package com.employee.product.documentdetails.request.dto;

import java.sql.Date;

import com.employee.product.expensedetails.response.dto.TypeCurrency;
import com.employee.product.expensedetails.response.dto.TypeExpense;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UploadDocumentDetailsRequestDto {

	@ApiParam(required = true,value="1 - WP, 2- PaySlip, 3 - Passport, 4 - Expense")
	private String documentType; // 1 - WP, 2- PaySlip, 3 - Passport, 4 - Expense
	
	private String documentNumber;
	
	//private String loggedInUserName;
	
	@ApiParam(required=true,value="Employee Id")
	private String employeeId; // EmployeeId for which document needs to be uploaded
	
	private String month;  // Payslip Module
	
	private String year;   //  Payslip Module 
	
	private String payslipType; // Payslip Module
	
	private String reason; // Expense Module
	
	private long amount;  // Expense Module
	
	private TypeCurrency typeCurrency; // Expense Module
	 
	private Date proofDate; // Expense Module
	
	private TypeExpense typeExpense; // Expense Module
	
	private String proofNumber; // Expense Module
	
	
}
