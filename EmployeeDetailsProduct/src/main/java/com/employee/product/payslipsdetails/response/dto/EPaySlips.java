package com.employee.product.payslipsdetails.response.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EPaySlips {
	
	private String firstName;
	private String lastName;
	List<EPaySlip> ePaySlip;

}
