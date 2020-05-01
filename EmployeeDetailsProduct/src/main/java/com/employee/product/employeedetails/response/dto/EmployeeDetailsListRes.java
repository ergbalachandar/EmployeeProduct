package com.employee.product.employeedetails.response.dto;

import java.sql.Date;

import com.employee.product.employeedetails.common.Sex;

import lombok.Data;

@Data
public class EmployeeDetailsListRes {
	
	private int id;
	private String firstName;
	private String lastName;
	private String emailId;
	private Sex sex;
	private String contactNumber;
	private Date dateOfBirth;
	
}
