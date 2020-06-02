package com.employee.product.employeedetails.response.dto;

import lombok.Data;

@Data
public class EmployeePassportDetailsResponseDto {

	private String passportNumber;
	private String startDate;
	private String endDate;
	private String issuePlace;
	private int validity;
	private String birthPlace;
	private String documentName;
	private String documentType;

}
