package com.employee.product.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.employee.product.employeedetails.dto.EmployeeDetailsDto;
import com.employee.product.employeedetails.dto.MaritalStat;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.employeedetails.EmployeeExpenseDetails;
import com.employee.product.expensedetails.response.dto.Expense;
import com.employee.product.expensedetails.response.dto.ExpenseResDto;
import com.employee.product.expensedetails.response.dto.Expenses;
import com.employee.product.expensedetails.response.dto.TypeCurrency;
import com.employee.product.expensedetails.response.dto.TypeExpense;
import com.employee.product.expensedetails.response.dto.TypeStatus;

/**
 * 
 * A class that helps to retrieve Expense and employee details
 * 
 * @author raj33
 *
 */
public class ExpenseDetailsUtil {

	/**
	 * Mapping employee details & Payslips
	 * 
	 * @param ePaySlipResList
	 * @param paySlipDetails
	 */
	public static void mapExpenseDetails(ExpenseResDto expenseRes, List<EmployeeDetails> expenseDetails	) {
		if (null != expenseDetails) {
			List<Expenses> expensesList = new ArrayList<Expenses>();
			for (EmployeeDetails employeeDetails : expenseDetails) {
				Expenses expenses = new Expenses();
				if (null != employeeDetails && employeeDetails.getActive() == 1 && null != employeeDetails.getEmployeeExpenseDetails()) {
					mappingEmployeeDetailsForExpense(expenses, employeeDetails);
					List<Expense> expenseList = new ArrayList<Expense>();
					for (EmployeeExpenseDetails employeeExpenseDetails : employeeDetails.getEmployeeExpenseDetails()) {
							expenseList.add(mapExpense(employeeExpenseDetails));
						}
					if(null != expenseList) {
						Comparator<Expense> comp = new Comparator<Expense>() {
							@Override
							public int compare(Expense o1, Expense o2) {
								return o2.getCreatedDate().compareTo(o1.getCreatedDate());
							}
						};
						expenseList.sort(comp);
					}
						expenses.setExpense(expenseList);
						expensesList.add(expenses);
				}
			}
			expenseRes.setExpenses(expensesList);
		}

	}

	/**
	 * Mapping employee details
	 * 
	 * @param ePaySlips
	 * @param employeeDetails
	 */
	private static void mappingEmployeeDetailsForExpense(Expenses expenses, EmployeeDetails employeeDetails) {
		EmployeeDetailsDto empDetails = new EmployeeDetailsDto();
		empDetails.setAddressLine1(employeeDetails.getAddressLine1());
		empDetails.setAddressLine2(employeeDetails.getAddressLine2());
		empDetails.setCity(employeeDetails.getCity());
		empDetails.setContactNumber(employeeDetails.getContactNumber());
		empDetails.setCountry(employeeDetails.getCountry());
		empDetails.setDateOfBirth(employeeDetails.getDateOfBirth());
		empDetails.setEmailId(employeeDetails.getEmailId());
		if (null != empDetails.getDateOfJoin())
			empDetails.setDateOfJoin(employeeDetails.getDateOfJoin());
		empDetails.setFirstName(employeeDetails.getFirstName());
		empDetails.setLastName(employeeDetails.getLastName());
		empDetails.setSex(employeeDetails.getSex());
		empDetails.setState(employeeDetails.getState());
		if (null != employeeDetails.getMaritalStatus())
			empDetails.setMaritalStatus(MaritalStat.valueOf(employeeDetails.getMaritalStatus()));
		empDetails.setJobRole(employeeDetails.getJobRole());
		empDetails.setId(employeeDetails.getId());
		expenses.setEmpDetails(empDetails);
	}

	/**
	 * 
	 * @param paySlipDetails
	 * @param expenseEmp
	 */
	public static void mapEmpExpenseDetails(Set<EmployeeExpenseDetails> empExpDetails, List<Expense> expenseEmp, String id) {
		for(EmployeeExpenseDetails employeeExpenseDetails:empExpDetails) {
			expenseEmp.add(mapExpense(employeeExpenseDetails,id));
		}		
		if(null != expenseEmp) {
			Comparator<Expense> comp = new Comparator<Expense>() {
				@Override
				public int compare(Expense o1, Expense o2) {
					return o2.getCreatedDate().compareTo(o1.getCreatedDate());
				}
			};
			expenseEmp.sort(comp);
		}
	
	}
	
	private static Expense mapExpense(EmployeeExpenseDetails employeeExpenseDetails, String id){
		Expense expense = new Expense();
		expense.setAmount(employeeExpenseDetails.getAmount());
		if(null != employeeExpenseDetails.getUpdatedDate())
			expense.setUpdatedDate(employeeExpenseDetails.getUpdatedDate());
		expense.setApprovedId(employeeExpenseDetails.getApprovedId());
		expense.setApproverName(employeeExpenseDetails.getApproverName());
		expense.setCreatedDate(employeeExpenseDetails.getCreatedDate());
		if(null != employeeExpenseDetails.getCurrencyType())
			expense.setTypeCurrency(TypeCurrency.valueOf(employeeExpenseDetails.getCurrencyType()));
		expense.setFileName(employeeExpenseDetails.getDocumentName());
		expense.setFileType(employeeExpenseDetails.getDocumentType());
		expense.setExpenseNumber(employeeExpenseDetails.getId());
		expense.setReason(employeeExpenseDetails.getReason());
		expense.setProofNumber(employeeExpenseDetails.getProofNumber());
		expense.setProofDate(String.valueOf(employeeExpenseDetails.getProofDate()));
		if(null != employeeExpenseDetails.getStatus())
			expense.setTypeStatus(TypeStatus.valueOf(employeeExpenseDetails.getStatus()));
		if(null != employeeExpenseDetails.getTypeExpense())
			expense.setTypeExpense(TypeExpense.valueOf(employeeExpenseDetails.getTypeExpense()));
		expense.setArMessage(employeeExpenseDetails.getArMessage());
		expense.setDocumentType("4");
		expense.setId(id);
		return expense;
		
	}
	
	private static Expense mapExpense(EmployeeExpenseDetails employeeExpenseDetails){
		Expense expense = new Expense();
		expense.setAmount(employeeExpenseDetails.getAmount());
		expense.setDocumentType("4");
		if(null != employeeExpenseDetails.getUpdatedDate())
			expense.setUpdatedDate(employeeExpenseDetails.getUpdatedDate());
		expense.setApprovedId(employeeExpenseDetails.getApprovedId());
		expense.setApproverName(employeeExpenseDetails.getApproverName());
		expense.setCreatedDate(employeeExpenseDetails.getCreatedDate());
		if(null != employeeExpenseDetails.getCurrencyType())
			expense.setTypeCurrency(TypeCurrency.valueOf(employeeExpenseDetails.getCurrencyType()));
		expense.setFileName(employeeExpenseDetails.getDocumentName());
		expense.setFileType(employeeExpenseDetails.getDocumentType());
		expense.setExpenseNumber(employeeExpenseDetails.getId());
		expense.setReason(employeeExpenseDetails.getReason());
		expense.setProofNumber(employeeExpenseDetails.getProofNumber());
		expense.setProofDate(String.valueOf(employeeExpenseDetails.getProofDate()));
		if(null != employeeExpenseDetails.getStatus())
			expense.setTypeStatus(TypeStatus.valueOf(employeeExpenseDetails.getStatus()));
		if(null != employeeExpenseDetails.getTypeExpense())
			expense.setTypeExpense(TypeExpense.valueOf(employeeExpenseDetails.getTypeExpense()));
		expense.setArMessage(employeeExpenseDetails.getArMessage());
		return expense;
		
	}

}
