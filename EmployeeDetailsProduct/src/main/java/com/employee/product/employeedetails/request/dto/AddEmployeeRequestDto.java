package com.employee.product.employeedetails.request.dto;

import lombok.Data;

@Data
public class AddEmployeeRequestDto {
	
	private String loggedInUserName; // Send this email Id for validation purpose, email id of logged in user.
	
	private int companyId;
	
	private EmployeeDetailsRequestDto employeeDetails;
	
	
	
	

}
