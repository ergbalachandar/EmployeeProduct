package com.employee.product.employeedetails.request.dto;

import lombok.Data;

@Data
public class AddEmployeeRequestDto {
	
	private String adminuserName;
	
	private int companyId;
	
	private EmployeeDetailsRequestDto employeeDetails;
	
	
	
	

}
