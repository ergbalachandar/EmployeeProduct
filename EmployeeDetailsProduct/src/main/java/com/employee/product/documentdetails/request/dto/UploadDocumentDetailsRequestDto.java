package com.employee.product.documentdetails.request.dto;

import lombok.Data;

@Data
public class UploadDocumentDetailsRequestDto {

	private String documentType; // 1 - WP, 2- Passport, 3 - Payslip
	
	private String documentNumber;
	
	
}
