package com.employee.product.masterdetails.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Employee Company details admin response
 * @author raj33
 *
 */
@Setter
@Getter
public class ECDA {

	private String id;
	private String companyName;
	private String emailId;
	private String addressLineOne;
	private String addressLineTwo;
	private String city;
	private String state;
	private String country;
	private String contactNumber;
	private int active;
	private String vatNumber;
	private int empCount;
	private String postalCode;
}
