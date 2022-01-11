package com.employee.product.payslipsdetails.response.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EPaySlip {
	
	private String fileName;
	private String fileType;
	private String documentNumber;
	private String month;
	private String year;
	private String uploadedDate;
	private PaySlipType paySlipType;
	private String id;
	private String documentType;
	
}
