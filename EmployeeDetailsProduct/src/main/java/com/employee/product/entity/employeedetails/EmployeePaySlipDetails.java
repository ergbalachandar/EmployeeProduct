package com.employee.product.entity.employeedetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
	

	@Column(name = "document_name")
	private String documentName;
	
	@Column(name = "document_type")
	private String documentType;

}
