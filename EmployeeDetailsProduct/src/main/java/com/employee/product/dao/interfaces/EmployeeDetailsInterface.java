package com.employee.product.dao.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.product.entity.companydetails.CompanyDetails;
import com.employee.product.entity.employeedetails.EmployeeDetails;

public interface EmployeeDetailsInterface extends JpaRepository <EmployeeDetails, String> {

	List<EmployeeDetails> findByCompanyDetails(CompanyDetails companyDetails);
	
}
