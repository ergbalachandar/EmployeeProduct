package com.employee.product.expensedetails.response.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ExpenseResDto {
	
	List<Expenses> expenses;

}
