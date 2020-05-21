package com.employee.product.dao.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.product.entity.companydetails.Users;
import com.employee.product.entity.employeedetails.EmployeePassportDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDetails;
import com.employee.product.entity.employeedetails.EmployeeWorkPermitDetails;

public interface LoginDetailsInterface extends JpaRepository <Users, String> {
	
	Optional<Users> findByUserName(String username);
	
	Users findByEmployeeDetailsEmployeeWorkPermitDetails(EmployeeWorkPermitDetails employeeWorkPermitDetails);
	
	Users findByEmployeeDetailsEmployeePaySlipDetails(EmployeePaySlipDetails employeePaySlipDetails);
	
	Users findByEmployeeDetailsEmployeePassportDetails(EmployeePassportDetails employeePassportDetails);
	
	
}
