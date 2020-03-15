package com.employee.product.dao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.product.dao.interfaces.CompanySignupDetailsInterface;
import com.employee.product.dao.interfaces.EmployeeDetailsInterface;
import com.employee.product.entity.companydetails.CompanyDetails;
import com.employee.product.entity.companydetails.Users;
import com.employee.product.entity.employeedetails.EmployeeDetails;

@Service
public class EmployeeProductService {

	@Autowired
	private CompanySignupDetailsInterface companySignupDetailsInterface;
	
	@Autowired
	private EmployeeDetailsInterface employeeDetailsInterface;
	
	/**
	 * method to save the CompanyDetails in DB
	 * @param companyDetails
	 */
	public Users signUpCompanyDetails(Users users){		
				
		return companySignupDetailsInterface.save(users);
	}
	
	
	public List<EmployeeDetails> findbyCompanyDetails(String id){
		
		System.out.println("inside companyDetails");
		CompanyDetails companyDetails = new CompanyDetails();
		companyDetails.setId(Integer.valueOf(id));
		List<EmployeeDetails> employeeDetailsList =  employeeDetailsInterface.findByCompanyDetails(companyDetails);
		System.out.println("outside companyDetails");
		System.out.println(employeeDetailsList.toString());
			return employeeDetailsList;
		
	}
	
}
