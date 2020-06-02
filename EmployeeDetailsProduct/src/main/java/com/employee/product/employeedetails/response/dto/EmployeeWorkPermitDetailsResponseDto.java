package com.employee.product.employeedetails.response.dto;

import lombok.Data;

@Data

public class EmployeeWorkPermitDetailsResponseDto {

	private String workPermitNumber;

	private String startDate;

	private String endDate;

	private int validity;

	private String documentName;

	private String documentType;

}
