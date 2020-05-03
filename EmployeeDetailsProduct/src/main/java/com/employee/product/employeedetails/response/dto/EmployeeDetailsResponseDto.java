package com.employee.product.employeedetails.response.dto;

import java.sql.Date;
import java.util.List;

import lombok.Data;

@Data
public class EmployeeDetailsResponseDto {

	private int id;
	
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

	private List<EmployeeWorkPermitDetailsResponseDto> workPermitDetails;

	private List<EmployeePassportDetailsResponseDto> passportDetails;

	private List<EmployeeFamilyDetailsResponseDto> familyDetails;

	private List<EmployeePaySlipDocumentDetailsResponseDto> payslipDetails;

}
