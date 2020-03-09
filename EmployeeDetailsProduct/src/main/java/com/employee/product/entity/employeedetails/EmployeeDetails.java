package com.employee.product.entity.employeedetails;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.employee.product.entity.companydetails.Users;

import lombok.Data;

@Data
@Entity
@Table(name = "employee")
public class EmployeeDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "email_id")
	private String emailId;
	@Column(name = "sex")
	private String sex;
	@Column(name = "address1")
	private String addressLine1;
	@Column(name = "address2")
	private String addressLine2;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "country")
	private String country;
	@Column(name = "contact_number")
	private String contactNumber;
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	@Column(name= "updated_at" ,nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date updated_at;

}
