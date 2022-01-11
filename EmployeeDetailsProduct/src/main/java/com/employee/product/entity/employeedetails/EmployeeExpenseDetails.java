package com.employee.product.entity.employeedetails;

import java.util.Date;

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

	@Column(name = "ar_message")
	private String arMessage;

	@Column(name = "reason")
	private String reason;

	@Column(name = "type_expense")
	private String typeExpense;

	@Column(name = "document_name")
	private String documentName;

	@Column(name = "document_type")
	private String documentType;
	
	@Column(name = "currency_type")
	private String currencyType;

	@Column(name = "amount")
	private long amount;

	@Column(name = "approver_name")
	private String approverName;

	@Column(name = "approver_id")
	private String approvedId;

	@Column(name = "status")
	private String status;

	@Column(name = "proof_date")
	private Date proofDate;
	
	@Column(name = "proof_number")
	private String proofNumber;

	@Column(name = "created_date", nullable = false, insertable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
	private Date createdDate;

	@Column(name = "approved_date", nullable = false, insertable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date updatedDate;
	
	@Lob
	@Column(name = "document_data")
	private byte[] documentData;

}
