package com.employee.product.dao.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.product.dao.interfaces.EmployeeDetailsInterface;
import com.employee.product.dao.interfaces.LoginDetailsInterface;
import com.employee.product.entity.companydetails.CompanyDetails;
import com.employee.product.entity.companydetails.Users;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.employeedetails.EmployeePassportDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDetails;
import com.employee.product.entity.employeedetails.EmployeeWorkPermitDetails;

@Service
public class EmployeeProductService {

	@Autowired
	private EmployeeDetailsInterface employeeDetailsInterface;

	@Autowired
	private EntityManager entity;

	@Autowired
	private LoginDetailsInterface loginDetailsInterface;

	/**
	 * method to save the CompanyDetails in DB
	 * 
	 * @param companyDetails
	 */
	@Transactional
	public Users signUpCompanyDetails(Users users) {

		return entity.merge(users);
		// return companySignupDetailsInterface.save(users);
	}

	@Transactional
	public CompanyDetails findCompanyDetails(int id) {

		return entity.find(CompanyDetails.class, id);
	}

	@Transactional
	public EmployeeDetails findByEmployeeId(Integer id) {
		return entity.find(EmployeeDetails.class, id);
	}

	public List<EmployeeDetails> findbyCompanyDetails(String id) {

		System.out.println("inside companyDetails");
		CompanyDetails companyDetails = new CompanyDetails();
		companyDetails.setId(Integer.valueOf(id));
		List<EmployeeDetails> employeeDetailsList = employeeDetailsInterface.findByCompanyDetails(companyDetails);
		return employeeDetailsList;

	}

	@Transactional
	public void deleteEmployee(String userName) {

		Users users = entity.find(Users.class, userName);
		users.setActive(0);
		Set<EmployeeDetails> employeeDetailsSet = users.getEmployeeDetails();
		for (EmployeeDetails employeeDetails : employeeDetailsSet) {
			employeeDetails.setActive(0);
		}
		System.out.println(users.getEmployeeDetails());

	}

	@Transactional
	public EmployeeDetails addOrUpdateEmployeeDetails(EmployeeDetails employeeDetails, Users users,
			CompanyDetails companyDetails, boolean newEmployee, String loggedInUserName) throws Exception {
		EmployeeDetails employeeDetailsValue = new EmployeeDetails();
		if (newEmployee) {
			checkForDocumentAlreadyPresentWithOtherEmployee(employeeDetails, loggedInUserName);
			employeeDetailsValue = employeeDetailsInterface.save(employeeDetails);

		} else {
			checkForDocumentAlreadyPresentWithOtherEmployee(employeeDetails, loggedInUserName);
			employeeDetailsValue = entity.merge(employeeDetails);
		}

		Set<EmployeeDetails> employeeDetailsSet = new HashSet<EmployeeDetails>();
		employeeDetailsSet.add(employeeDetailsValue);

		if (newEmployee) {

			Users userValue = entity.merge(users);
			userValue.setEmployeeDetails(employeeDetailsSet);
			userValue.setCompanyDetails(companyDetails);
		}

		return employeeDetailsValue;

	}

	public void checkForDocumentAlreadyPresentWithOtherEmployee(EmployeeDetails employeeDetails, String userName)
			throws Exception {

		Optional<Users> userRoleOfLoggedInEmployee = loginDetailsInterface.findById(userName);

		if (employeeDetails.getId() != 0) {

			Set<EmployeeWorkPermitDetails> employeeWorkPermitDetailsList = employeeDetails
					.getEmployeeWorkPermitDetails();

			for (EmployeeWorkPermitDetails employeeWorkPermitDetails : employeeWorkPermitDetailsList) {

				Users userDetailsRetrieval = loginDetailsInterface
						.findByEmployeeDetailsEmployeeWorkPermitDetails(employeeWorkPermitDetails);

				DocumentManagementService.accessValidation(userRoleOfLoggedInEmployee, userDetailsRetrieval,
						employeeDetails.getId());

			}

			Set<EmployeePaySlipDetails> employeePaySlipDetailsList = employeeDetails.getEmployeePaySlipDetails();

			for (EmployeePaySlipDetails employeePaySlipDetails : employeePaySlipDetailsList) {

				Users userDetailsRetrieval = loginDetailsInterface
						.findByEmployeeDetailsEmployeePaySlipDetails(employeePaySlipDetails);

				DocumentManagementService.accessValidation(userRoleOfLoggedInEmployee, userDetailsRetrieval,
						employeeDetails.getId());

			}

			Set<EmployeePassportDetails> employeePassportDetailsList = employeeDetails.getEmployeePassportDetails();

			for (EmployeePassportDetails employeePassportDetails : employeePassportDetailsList) {

				Users userDetailsRetrieval = loginDetailsInterface
						.findByEmployeeDetailsEmployeePassportDetails(employeePassportDetails);

				DocumentManagementService.accessValidation(userRoleOfLoggedInEmployee, userDetailsRetrieval,
						employeeDetails.getId());

			}
		} else {

			if (!userRoleOfLoggedInEmployee.get().getRole().equalsIgnoreCase("Admin")) {
				throw new Exception("You dont have access to add employee");
			}

			if (userRoleOfLoggedInEmployee.get().getEmployeeDetails().stream().findFirst().get().getCompanyDetails()
					.getId() != employeeDetails.getCompanyDetails().getId()) {

				throw new Exception("You are not authorised to add employee to other company");
			}
		}

	}

}
