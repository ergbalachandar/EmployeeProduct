package com.employee.product.expensedetails.req.dto;

import com.employee.product.expensedetails.response.dto.TypeStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseReq {

	TypeStatus typeStatus;
	String expenseId;
	String arMesssage;
	
}
