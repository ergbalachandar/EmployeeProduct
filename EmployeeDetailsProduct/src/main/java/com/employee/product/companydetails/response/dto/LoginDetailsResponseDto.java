package com.employee.product.companydetails.response.dto;

import com.employee.product.employeedetails.response.dto.EmployeeDetailsResponseDto;

import lombok.Data;

@Data
public class LoginDetailsResponseDto {

	private String userName;
	
	private String role;
	
	private String companyName;
	
	private String companyId;
	
	private String jwt;

	private int companyFlag;
	
	private String notifCount;
	
	private EmployeeDetailsResponseDto employeeDetails;
	

}
