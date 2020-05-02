package com.employee.product.employeedetails.request.dto;

import lombok.Data;

@Data
public class RetrieveEmployeeDataRequestDto {
	
	private Integer employeeId;
	private String userName;
	private String password;

}
