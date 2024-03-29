package com.employee.product.dao.services;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.product.dao.interfaces.EmployeeDetailsInterface;
import com.employee.product.dao.interfaces.LoginDetailsInterface;
import com.employee.product.entity.companydetails.CompanyDetails;
import com.employee.product.entity.companydetails.Users;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.employeedetails.EmployeePassportDetails;
import com.employee.product.entity.employeedetails.EmployeeWorkPermitDetails;
import com.employee.product.utils.AddEmployeeDetailsUtil;

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
	public CompanyDetails updateCompany(CompanyDetails companyDetails) {
		return entity.merge(companyDetails);
	}

	@Transactional
	public CompanyDetails findCompanyDetails(String id) {

		return entity.find(CompanyDetails.class, id);
	}

	@Transactional
	public EmployeeDetails findByEmployeeId(String id) {
		return entity.find(EmployeeDetails.class, id);
	}

	public List<EmployeeDetails> findbyCompanyDetails(String id) {

		System.out.println("inside companyDetails");
		CompanyDetails companyDetails = new CompanyDetails();
		companyDetails.setId(id);
		List<EmployeeDetails> employeeDetailsList = employeeDetailsInterface.findByCompanyDetails(companyDetails);
		return employeeDetailsList;

	}

	@Transactional
	public String deleteEmployee(String userName, String companyId) throws Exception {
		Users users = entity.find(Users.class, userName);
		if(!users.getCompanyDetails().getId().equalsIgnoreCase(companyId)) {
			throw new Exception("You dont have authorise to delete employee of other company");
		}
		users.setActive(0);
		Set<EmployeeDetails> employeeDetailsSet = users.getEmployeeDetails();
		for (EmployeeDetails employeeDetails : employeeDetailsSet) {
			employeeDetails.setActive(0);
			employeeDetails.setResignDate(new Date(new java.util.Date().getTime()));
		}
		System.out.println(users.getEmployeeDetails());
		return users.getFirstName()+" "+ users.getLastName();

	}

	@Transactional
	public EmployeeDetails addOrUpdateEmployeeDetails(EmployeeDetails employeeDetails, Users users,
			CompanyDetails companyDetails, boolean newEmployee, String loggedInUserName) throws Exception {
		EmployeeDetails employeeDetailsValue = new EmployeeDetails();
		if (newEmployee) {
			/*
			 * if(employeeDetails.getEmployeePaySlipDetails().size() != 0) {
			 * Set<EmployeePaySlipDetails> employeePaySlipDetailsSet =
			 * employeeDetails.getEmployeePaySlipDetails(); Set<EmployeePaySlipDetails>
			 * employeePaySlipDetailsSetWithNumber = new HashSet<EmployeePaySlipDetails>();
			 * for(EmployeePaySlipDetails employeePaySlipDetails :employeePaySlipDetailsSet)
			 * {
			 * 
			 * EmployeePaySlipDetails employeePaySlipDetailsUpdated = new
			 * EmployeePaySlipDetails();
			 * employeePaySlipDetailsUpdated.setDocumentName(employeePaySlipDetails.
			 * getDocumentName());
			 * employeePaySlipDetailsUpdated.setDocumentType(employeePaySlipDetails.
			 * getDocumentType());
			 * employeePaySlipDetailsUpdated.setPaySlipMonth(employeePaySlipDetails.
			 * getPaySlipMonth());
			 * employeePaySlipDetailsUpdated.setPaySlipNumber(employeeDetails.getId()+
			 * employeePaySlipDetails.getPaySlipNumber());
			 * employeePaySlipDetailsSetWithNumber.add(employeePaySlipDetailsUpdated);
			 * 
			 * }
			 * employeeDetails.setEmployeePaySlipDetails(employeePaySlipDetailsSetWithNumber
			 * ); }
			 */
			checkForDocumentAlreadyPresentWithOtherEmployee(employeeDetails, loggedInUserName);
			
			employeeDetails.setId(AddEmployeeDetailsUtil.generateEmployeeId(companyDetails.getCompanyName(), employeeDetails.getFirstName(), employeeDetails.getLastName()));
			
		
			employeeDetailsValue = employeeDetailsInterface.save(employeeDetails);

		} else {
			/*
			 * if(employeeDetails.getEmployeePaySlipDetails().size() != 0) {
			 * Set<EmployeePaySlipDetails> employeePaySlipDetailsSet =
			 * employeeDetails.getEmployeePaySlipDetails(); Set<EmployeePaySlipDetails>
			 * employeePaySlipDetailsSetWithNumber = new HashSet<EmployeePaySlipDetails>();
			 * for(EmployeePaySlipDetails employeePaySlipDetails :employeePaySlipDetailsSet)
			 * {
			 * 
			 * EmployeePaySlipDetails employeePaySlipDetailsUpdated = new
			 * EmployeePaySlipDetails();
			 * employeePaySlipDetailsUpdated.setDocumentName(employeePaySlipDetails.
			 * getDocumentName());
			 * employeePaySlipDetailsUpdated.setDocumentType(employeePaySlipDetails.
			 * getDocumentType());
			 * employeePaySlipDetailsUpdated.setPaySlipMonth(employeePaySlipDetails.
			 * getPaySlipMonth());
			 * employeePaySlipDetailsUpdated.setPaySlipNumber(employeeDetails.getId()+
			 * employeePaySlipDetails.getPaySlipNumber());
			 * employeePaySlipDetailsSetWithNumber.add(employeePaySlipDetailsUpdated);
			 * 
			 * }
			 * employeeDetails.setEmployeePaySlipDetails(employeePaySlipDetailsSetWithNumber
			 * ); }
			 */
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

		if (StringUtils.isNotBlank(employeeDetails.getId())) {

			Set<EmployeeWorkPermitDetails> employeeWorkPermitDetailsList = employeeDetails
					.getEmployeeWorkPermitDetails();

			for (EmployeeWorkPermitDetails employeeWorkPermitDetails : employeeWorkPermitDetailsList) {

				Users userDetailsRetrieval = loginDetailsInterface
						.findByEmployeeDetailsEmployeeWorkPermitDetails(employeeWorkPermitDetails);

				DocumentManagementService.accessValidation(userRoleOfLoggedInEmployee, userDetailsRetrieval,
						employeeDetails.getId(),employeeDetails);

			}
			//Commented as Payslip is not required in this module.

			/*
			 * Set<EmployeePaySlipDetails> employeePaySlipDetailsList =
			 * employeeDetails.getEmployeePaySlipDetails();
			 * 
			 * for (EmployeePaySlipDetails employeePaySlipDetails :
			 * employeePaySlipDetailsList) {
			 * 
			 * Users userDetailsRetrieval = loginDetailsInterface
			 * .findByEmployeeDetailsEmployeePaySlipDetails(employeePaySlipDetails);
			 * 
			 * DocumentManagementService.accessValidation(userRoleOfLoggedInEmployee,
			 * userDetailsRetrieval, employeeDetails.getId(),employeeDetails);
			 * 
			 * }
			 */

			Set<EmployeePassportDetails> employeePassportDetailsList = employeeDetails.getEmployeePassportDetails();

			for (EmployeePassportDetails employeePassportDetails : employeePassportDetailsList) {

				Users userDetailsRetrieval = loginDetailsInterface
						.findByEmployeeDetailsEmployeePassportDetails(employeePassportDetails);

				DocumentManagementService.accessValidation(userRoleOfLoggedInEmployee, userDetailsRetrieval,
						employeeDetails.getId(),employeeDetails);

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
