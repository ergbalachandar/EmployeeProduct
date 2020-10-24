package com.employee.product.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.employee.product.employeedetails.dto.EmployeeDetailsDto;
import com.employee.product.employeedetails.dto.MaritalStat;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.employeedetails.EmployeeExpenseDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDetails;
import com.employee.product.expensedetails.response.dto.Expense;
import com.employee.product.expensedetails.response.dto.ExpenseResDto;
import com.employee.product.expensedetails.response.dto.Expenses;
import com.employee.product.expensedetails.response.dto.TypeCurrency;
import com.employee.product.expensedetails.response.dto.TypeExpense;
import com.employee.product.expensedetails.response.dto.TypeStatus;
import com.employee.product.payslipsdetails.response.dto.EPaySlip;
import com.employee.product.payslipsdetails.response.dto.EPaySlipEmpRes;
import com.employee.product.payslipsdetails.response.dto.PaySlipType;

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
							Expense expense = new Expense();
							expense.setAmount(employeeExpenseDetails.getAmount());
							expense.setApprovedDate(String.valueOf(employeeExpenseDetails.getApprovedDate()));
							expense.setApprovedId(employeeExpenseDetails.getApprovedId());
							expense.setApproverName(employeeExpenseDetails.getApproverName());
							expense.setCreatedDate(String.valueOf(employeeExpenseDetails.getCreatedDate()));
							if(null != employeeExpenseDetails.getCurrencyType())
								expense.setTypeCurrency(TypeCurrency.valueOf(employeeExpenseDetails.getCurrencyType()));
							expense.setDocumentName(employeeExpenseDetails.getDocumentName());
							expense.setDocumentType(employeeExpenseDetails.getDocumentType());
							expense.setExpenseNumber(employeeExpenseDetails.getId());
							expense.setReason(employeeExpenseDetails.getReason());
							expense.setProofDate(String.valueOf(employeeExpenseDetails.getProofDate()));
							if(null != employeeExpenseDetails.getStatus())
								expense.setTypeStatus(TypeStatus.valueOf(employeeExpenseDetails.getStatus()));
							if(null != employeeExpenseDetails.getTypeExpense())
								expense.setTypeExpense(TypeExpense.valueOf(employeeExpenseDetails.getTypeExpense()));
							expenseList.add(expense);
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
	 * @param ePaySlipEmpRes
	 */
	public static void mapEmpPaySlipDetails(Set<EmployeePaySlipDetails> paySlipDetails, EPaySlipEmpRes ePaySlipEmpRes) {
		List<EPaySlip> ePaySlipList = new ArrayList<EPaySlip>();
		for (EmployeePaySlipDetails paySlip : paySlipDetails) {
				EPaySlip paySlipEmp = new EPaySlip();
				paySlipEmp.setDocumentName(paySlip.getDocumentName());
				if (null != paySlip.getPayslipType())
					paySlipEmp.setPaySlipType(PaySlipType.valueOf(paySlip.getPayslipType()));
				paySlipEmp.setMonth(paySlip.getPaySlipMonth());
				paySlipEmp.setDocumentType(paySlip.getDocumentType());
				paySlipEmp.setDocumentNumber(paySlip.getPaySlipNumber());
				paySlipEmp.setYear(paySlip.getPaySlipYear());
				paySlipEmp.setUploadedDate(String.valueOf(paySlip.getUploadedDate()));
				ePaySlipList.add(paySlipEmp);
		}

		ePaySlipEmpRes.setEPaySlip(ePaySlipList);
	}

}
