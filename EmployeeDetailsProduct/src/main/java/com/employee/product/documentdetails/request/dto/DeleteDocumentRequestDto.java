package com.employee.product.documentdetails.request.dto;

import lombok.Data;

@Data
public class DeleteDocumentRequestDto {
	
//	private String userName;
//	private String password;
	private String documentNumber;
	private String documentType;
	private String employeeId; // for which document to be deleted

}
