package com.employee.product.documentdetails.request.dto;

import java.sql.Date;

import com.employee.product.expensedetails.response.dto.TypeCurrency;
import com.employee.product.expensedetails.response.dto.TypeExpense;

import lombok.Data;

@Data
public class UploadDocumentDetailsRequestDto {

	private String documentType; // 1 - WP, 2- PaySlip, 3 - Passport
	
	private String documentNumber;
	
	//private String loggedInUserName;
	
	private String employeeId; // EmployeeId for which document needs to be uploaded
	
	private String month;  // Payslip Module
	
	private String year;   //  Payslip Module 
	
	private String payslipType; // Payslip Module
	
	private String reason; // Expense Module
	
	private long amount;  // Expense Module
	
	private TypeCurrency currency; // Expense Module
	 
	private Date proofDate; // Expense Module
	
	private TypeExpense expense; // Expense Module
	
	
}
