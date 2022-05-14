
package com.employee.product.entity.employeedetails;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "timesheet_details")
public class EmployeeTimeSheetDetails {

	@Id
	@Column(name = "id")
	private String timeSheetNumber;

	@Column(name = "type_timesheet")
	private String typeTimesheet;

	@Column(name = "document_name")
	private String documentName;

	@Column(name = "documentType")
	private String documentType;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "validity")
	private int validity;

	@Column(name = "document_data")
	private String documentData;

	@Column(name = "status")
	private String status;

	@Column(name = "approved_id")
	private String approvedId;

	@Column(name = "timesheet_date")
	private Date timesheetDate;

	@Column(name = "approver_name")
	private String approverName;

	@Column(name = "approved_date")
	private Date approvedDate;

	@Column(name = "clientName")
	private String clientName;

	@Column(name = "timePeriod")
	private int timePeriod;

	@Column(name = "task")
	private String task;

}
