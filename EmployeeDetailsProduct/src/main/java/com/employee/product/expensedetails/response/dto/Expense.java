package com.employee.product.expensedetails.response.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Expense {
	
	private String expenseNumber;
	private String reason;
	private TypeExpense typeExpense;
	private String fileName;
	private String fileType;
	private long amount;
	private String approverName;
	private String approvedId;
	private TypeStatus typeStatus;
	private TypeCurrency typeCurrency;
	private String proofDate;
	private String proofNumber;
	private Date createdDate;
	private String arMessage;
	private Date updatedDate;
	private String id;
	private String documentType;

}
