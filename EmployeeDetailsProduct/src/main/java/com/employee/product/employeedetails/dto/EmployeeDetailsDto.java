package com.employee.product.employeedetails.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class EmployeeDetailsDto {
	
	private String firstName;
	private String lastName;
	private String emailId;
	private String sex;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;
	private String contactNumber;
	private Date dateOfBirth;
	
	

}
