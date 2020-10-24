package com.employee.product.expensedetails.response.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Expense {
	
	private String expenseNumber;
	private String reason;
	private TypeExpense typeExpense;
	private String documentName;
	private String documentType;
	private long amount;
	private String approverName;
	private String approvedId;
	private TypeStatus typeStatus;
	private TypeCurrency typeCurrency;
	private String proofDate;
	private String createdDate;
	private String approvedDate;

}
