
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
@Table(name = "leave_details")
public class EmployeeLeaveDetails {

	@Id
	@Column(name = "id")
	private String leaveIdNumber;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "no_of_days")
	private int noOfDays;

	@Column(name = "leave_type")
	private String leaveType;

}
