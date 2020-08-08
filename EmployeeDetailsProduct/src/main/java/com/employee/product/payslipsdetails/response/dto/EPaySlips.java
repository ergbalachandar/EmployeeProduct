package com.employee.product.payslipsdetails.response.dto;

import java.util.List;

import com.employee.product.employeedetails.dto.EmployeeDetailsDto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EPaySlips {
	EmployeeDetailsDto empDetails;
	List<EPaySlip> ePaySlip;
}
