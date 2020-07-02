package com.employee.product.entity.masterdetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "COMPANY_DETAILS_MADM")
public class CompanyDetailsMadm {

	@Id
	private String id;
	@Column(name = "name")
	private String companyName;
	@Column(name = "email_id")
	private String emailId;
	@Column(name = "address1")
	private String addressLineOne;
	@Column(name = "address2")
	private String addressLineTwo;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "country")
	private String country;
	@Column(name = "contact_number")
	private String contactNumber;
	@Column(name = "active")
	private int active;
	@Column(name = "vat_number")
	private String vatNumber;
	@Column(name = "total")
	private int countOfEmp;
	@Column(name = "postal_code")
	private String postalCode;
	@Column(name = "company_type")
	private String companyType;

}
