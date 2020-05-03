package com.employee.product.employeedetails.request.dto;

import java.sql.Date;
import java.util.List;


import lombok.Data;


@Data
public class EmployeeDetailsRequestDto {
	
	
	private Integer id;
	
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
	
	private String department;
	
	private String jobRole;
	
	private String reportingPerson;
	
	private String workLocation;
	
    private List<EmployeeWorkPermitDetailsRequestDto> workPermitDetails;
    
    private List<EmployeePassportDetailsRequestDto> passportDetails;
    
    private List<EmployeeFamilyDetailsRequestDto> familyDetails;
    
    private List<EmployeePaySlipDetailsRequestDto> paySlipDetails;

}
