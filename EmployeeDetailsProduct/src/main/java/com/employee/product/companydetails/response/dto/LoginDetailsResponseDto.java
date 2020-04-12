package com.employee.product.companydetails.response.dto;




import com.employee.product.employeedetails.response.dto.EmployeeDataResponseDto;

import lombok.Data;

@Data
public class LoginDetailsResponseDto {
	
	private String userName;
	
	private String role;
	
	private String companyName;
	
	private String companyId;
	
	private EmployeeDataResponseDto employeeDataResponseDto;

}
