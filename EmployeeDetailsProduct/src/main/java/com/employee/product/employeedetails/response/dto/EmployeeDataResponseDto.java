package com.employee.product.employeedetails.response.dto;

import java.util.List;


import com.employee.product.entity.employeedetails.EmployeeDetails;

import lombok.Data;

@Data
public class EmployeeDataResponseDto {
	
	private List<EmployeeDetailsResponseDto> employeeDetailsList;

}
