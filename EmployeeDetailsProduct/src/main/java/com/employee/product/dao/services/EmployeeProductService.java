package com.employee.product.dao.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.product.dao.interfaces.EmployeeDetailsInterface;
import com.employee.product.entity.companydetails.CompanyDetails;
import com.employee.product.entity.companydetails.Users;
import com.employee.product.entity.employeedetails.EmployeeDetails;

@Service
public class EmployeeProductService {

	@Autowired
	private EmployeeDetailsInterface employeeDetailsInterface;

	@Autowired
	private EntityManager entity;

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
			CompanyDetails companyDetails) {

		EmployeeDetails employeeDetailsValue = entity.merge(employeeDetails);

		Set<EmployeeDetails> employeeDetailsSet = new HashSet<EmployeeDetails>();
		employeeDetailsSet.add(employeeDetailsValue);

		if (employeeDetails.getId() == 0) {

			Users userValue = entity.merge(users);
			userValue.setEmployeeDetails(employeeDetailsSet);
			userValue.setCompanyDetails(companyDetails);
		}

		return employeeDetailsValue;

	}

}
