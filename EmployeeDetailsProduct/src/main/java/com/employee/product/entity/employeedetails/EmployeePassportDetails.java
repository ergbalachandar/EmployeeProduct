package com.employee.product.entity.employeedetails;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "passport_details")
public class EmployeePassportDetails {

	@Id
	@Column(name = "passport_number")
	private String passportNumber;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "issue_place")
	private String issuePlace;

	@Column(name = "validity")
	private int validity;

}
