package com.employee.product.payslipsdetails.response.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EPaySlip {

	private String documentName;
	private String documentNumber;
	private DocumentType documentType;
	private String month;
	private String year;
}
