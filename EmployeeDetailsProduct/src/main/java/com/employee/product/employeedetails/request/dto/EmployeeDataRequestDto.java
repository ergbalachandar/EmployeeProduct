package com.employee.product.employeedetails.request.dto;

import lombok.Data;

@Data
public class EmployeeDataRequestDto {
	
//	private String userName;
//	private String password;
	private FILETYPE fileType;  // 1- PDF, 2 - XLSX, 3 - CSV

}
