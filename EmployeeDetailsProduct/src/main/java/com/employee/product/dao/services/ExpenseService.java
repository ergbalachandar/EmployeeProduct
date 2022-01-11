package com.employee.product.dao.services;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.product.dao.interfaces.EmployeeDetailsInterface;
import com.employee.product.dao.interfaces.EmployeeExpenseDetailsInterface;
import com.employee.product.entity.companydetails.CompanyDetails;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.employeedetails.EmployeeExpenseDetails;
import com.employee.product.expensedetails.req.dto.ExpenseReq;

@Service
public class ExpenseService {
	
	@Autowired
	public EmployeeDetailsInterface empDetInt;
	

	@Autowired
	private EntityManager entity;
	
	@Autowired
	public EmployeeExpenseDetailsInterface empExp;
	
	
	public List<EmployeeDetails> retrieveExpenseByCompanyId(CompanyDetails companyDetails) {
		return empDetInt.findByCompanyDetails(companyDetails);
	}
	
	@Transactional
	public void updateExpense(ExpenseReq expenseReq, UserDetailsImpl userDetails) {
		EmployeeExpenseDetails exp = empExp.getOne(expenseReq.getExpenseId());
		exp.setApproverName(userDetails.getUsers().getFirstName());
		if(null != expenseReq.getArMesssage())
			exp.setArMessage(expenseReq.getArMesssage());
		exp.setStatus(expenseReq.getTypeStatus().name());
		exp.setUpdatedDate(new Date());
		entity.merge(exp);
	}

}
