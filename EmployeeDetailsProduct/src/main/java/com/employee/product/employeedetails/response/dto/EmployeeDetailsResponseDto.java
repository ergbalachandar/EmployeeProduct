package com.employee.product.employeedetails.response.dto;


import java.util.List;

import lombok.Data;

@Data
public class EmployeeDetailsResponseDto {

	private String id;

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

	private String dateOfBirth;

	private String postalCode;

	private String jobRole;

	private String department;

	private String reportingPerson;

	private String workLocation;

	private List<EmployeeWorkPermitDetailsResponseDto> workPermitDetails;

	private List<EmployeePassportDetailsResponseDto> passportDetails;

	private List<EmployeeFamilyDetailsResponseDto> familyDetails;

	// Commenting the call to map Payslip details as it is not required in this
	// module
	// private List<EmployeePaySlipDocumentDetailsResponseDto> payslipDetails;

}
