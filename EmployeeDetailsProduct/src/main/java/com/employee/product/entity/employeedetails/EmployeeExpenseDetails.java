package com.employee.product.entity.employeedetails;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "expense_details")
public class EmployeeExpenseDetails {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "issue_place")
	private String issuePlace;

	@Column(name = "reason")
	private String reason;

	@Column(name = "type_expense")
	private String typeExpense;

	@Column(name = "document_name")
	private String documentName;

	@Column(name = "document_type")
	private String documentType;

	@Column(name = "amount")
	private long amount;

	@Column(name = "approver_name")
	private String approverName;

	@Column(name = "approver_id")
	private String approvedId;

	@Column(name = "status")
	private String status;

	@Column(name = "proof_date")
	private Date startDate;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "approved_date")
	private Date approvedDate;

	@Lob
	@Column(name = "document_data")
	private byte[] documentData;

}
