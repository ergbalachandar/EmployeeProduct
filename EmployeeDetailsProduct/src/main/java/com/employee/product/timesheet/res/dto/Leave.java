package com.employee.product.timesheet.res.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Leave {
	
	private String leaveIdNumber;
	private Date createdDate;
	private int noOfDays;
	private String leaveType;
}
