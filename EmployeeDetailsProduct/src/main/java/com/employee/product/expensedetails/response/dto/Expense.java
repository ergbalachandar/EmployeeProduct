package com.employee.product.expensedetails.response.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Expense {
	
	private String expenseNumber;
	private String issuePlace;
	private String reason;
	private String typeExpense;
	private String documentName;
	private String documentType;
	private long amount;
	private String approverName;
	private String approvedId;
	private String status;
	private Date startDate;
	private Date createdDate;
	private Date approvedDate;

}
