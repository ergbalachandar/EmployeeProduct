package com.employee.product.employeedetails.response.dto;

import java.util.Date;

import lombok.Data;

@Data

public class EmployeeWorkPermitDetailsResponseDto {
	
	private String workPermitNumber;
	
	private Date startDate;
	
	private Date endDate;
	
	private int validity;
	
	

}
