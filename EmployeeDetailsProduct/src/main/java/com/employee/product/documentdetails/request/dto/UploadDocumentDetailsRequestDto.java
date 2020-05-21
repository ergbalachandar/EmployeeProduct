package com.employee.product.documentdetails.request.dto;

import lombok.Data;

@Data
public class UploadDocumentDetailsRequestDto {

	private String documentType; // 1 - WP, 2- PaySlip, 3 - Passport
	
	private String documentNumber;
	
	//private String loggedInUserName;
	
	private String employeeId; // EmployeeId for which document needs to be uploaded
	
	
}
