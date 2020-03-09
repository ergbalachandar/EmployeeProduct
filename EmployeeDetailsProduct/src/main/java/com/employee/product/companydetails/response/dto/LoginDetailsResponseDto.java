package com.employee.product.companydetails.response.dto;

import com.employee.product.employeedetails.dto.EmployeeDetailsDto;

import lombok.Data;

@Data
public class LoginDetailsResponseDto {
	
	private String userName;
	
	private String role;
	
	private EmployeeDetailsDto employeeDetailsDto;

}
