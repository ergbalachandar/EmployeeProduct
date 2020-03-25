package com.employee.product.employeedetails.request.dto;

import lombok.Data;

@Data
public class EmployeeFamilyDetailsRequestDto {
	
	private Integer id;
	private String firstName;
	private String lastName;
	private int relation;
	private String contactNumber;

	

}
