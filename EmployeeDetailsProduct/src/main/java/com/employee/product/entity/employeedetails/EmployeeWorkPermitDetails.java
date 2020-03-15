package com.employee.product.entity.employeedetails;



import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "workpermit_details")
public class EmployeeWorkPermitDetails {
	
	@Id
	@Column(name = "workpermit_number")
	private String workPermitNumber;
	
	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "validity")
	private int validity;
	
/*	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "workpermit_number")
	private EmployeeWorkPermitDocumentDetails employeeWorkPermitDocumentDetails; */
}
