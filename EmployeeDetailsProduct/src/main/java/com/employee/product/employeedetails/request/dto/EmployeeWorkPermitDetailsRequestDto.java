package com.employee.product.employeedetails.request.dto;

import java.sql.Date;

import lombok.Data;

@Data

public class EmployeeWorkPermitDetailsRequestDto {

	private String workPermitNumber;

	private Date startDate;

	private Date endDate;

	private int validity;
	
	private String documentName;
	
	private String documentType;
}
