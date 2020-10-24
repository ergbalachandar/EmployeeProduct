package com.employee.product.dao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.product.dao.interfaces.EmployeeDetailsInterface;
import com.employee.product.entity.companydetails.CompanyDetails;
import com.employee.product.entity.employeedetails.EmployeeDetails;

@Service
public class ExpenseService {
	
	@Autowired
	public EmployeeDetailsInterface empDetInt;
	
	public List<EmployeeDetails> retrievePayslipsByCompanyId(CompanyDetails companyDetails) {
		return empDetInt.findByCompanyDetails(companyDetails);
	}

}
