package com.employee.product.employeedetails.response.dto;

import java.util.Date;

import lombok.Data;

@Data
public class EmployeePassportDetailsResponseDto {
	
	private String passportNumber;
	private Date startDate;
	private Date endDate;
	private String issuePlace;
	private int validity;
	private String birthPlace;
	private String documentName;
	private String documentType;

}
