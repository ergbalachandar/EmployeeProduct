package com.employee.product.entity.companydetails;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.employee.product.entity.employeedetails.EmployeeDetails;

import lombok.Data;

@Entity
@Data
@Table(name = "COMPANY")
public class CompanyDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
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
	@Column(name = "size")
	private int sizeOfTheCompany;
	@Column(name = "active")
	private int active;
	
   
	
   /*@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "company_id")
	private Set<Users> users; */
    
    

}
