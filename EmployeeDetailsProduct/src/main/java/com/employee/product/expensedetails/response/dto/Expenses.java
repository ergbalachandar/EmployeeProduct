package com.employee.product.expensedetails.response.dto;

import java.util.Set;

import com.employee.product.employeedetails.dto.EmployeeDetailsDto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Expenses {
	
	EmployeeDetailsDto empDetails;
	Set<Expense> expense;


}
