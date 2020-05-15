package com.employee.product.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.product.entity.companydetails.Users;
import com.employee.product.entity.employeedetails.EmployeePassportDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDetails;
import com.employee.product.entity.employeedetails.EmployeeWorkPermitDetails;

public interface LoginDetailsInterface extends JpaRepository <Users, String> {
	
	Users findByEmployeeDetailsEmployeeWorkPermitDetails(EmployeeWorkPermitDetails employeeWorkPermitDetails);
	
	Users findByEmployeeDetailsEmployeePaySlipDetails(EmployeePaySlipDetails employeePaySlipDetails);
	
	Users findByEmployeeDetailsEmployeePassportDetails(EmployeePassportDetails employeePassportDetails);
	
	
}
