package com.employee.product.companydetails.request.dto;


import com.employee.product.employeedetails.dto.EmployeeDetailsDto;

import lombok.Data;

@Data
public class CompanyDetailsRequestDto {
	
	private String companyName;
	private String emailId;
	private String addressLineOne;
	private String addressLineTwo;
	private String city;
	private String state;
	private String country;
	private String contactNumber;
	private int sizeOfTheCompany;
	private int active;
	private String password;
	private EmployeeDetailsDto employeeDetails;
	
}
