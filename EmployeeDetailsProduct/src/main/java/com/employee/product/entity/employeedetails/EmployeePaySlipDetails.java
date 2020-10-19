package com.employee.product.entity.employeedetails;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "payslip_details")
public class EmployeePaySlipDetails {

	@Id
	@Column(name = "payslip_number")
	private String paySlipNumber;

	@Column(name = "payslip_month")
	private String paySlipMonth;
	
	@Column(name = "payslip_year")
	private String paySlipYear;

	@Column(name = "document_name")
	private String documentName;
	
	@Column(name = "uploaded_date")
	private Date uploadedDate;
	
	@Column(name = "document_type")
	private String documentType;
	
	@Column(name = "payslip_type")
	private String payslipType;
	
	@Lob
	@Column(name = "document_data")
	private byte[] documentData;
	
	
}
