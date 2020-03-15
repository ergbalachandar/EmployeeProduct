package com.employee.product.employeedetails.request.dto;

import java.util.Date;

import lombok.Data;

@Data
public class EmployeePassportDetailsRequestDto {
	
	private String passportNumber;
	private Date startDate;
	private Date endDate;
	private String issuePlace;
	private int validity;

}
