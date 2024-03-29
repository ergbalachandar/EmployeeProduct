package com.employee.product.entity.employeedetails;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.employee.product.entity.companydetails.CompanyDetails;

import lombok.Data;

@Data
@Entity
@Table(name = "employee")
public class EmployeeDetails {

	@Id
	private String id;
	
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
	private java.sql.Date dateOfBirth;
	
	@Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date updated_at;
	
	@Column(name = "job_role")
	private String jobRole;
	
	@Column(name = "resignation_date")
	private java.sql.Date resignDate;
	
	@Column(name = "work_location")
	private String workLocation;

	@Column(name = "marital_status")
	private String maritalStatus;
	
	@Column(name = "date_of_joining")
	private java.sql.Date dateOfJoin;
	
	@Column(name = "department")
	private String department;
	
	@Column(name = "reporting_person")
	private String reportingPerson;
	
	@Column(name="active")
	private int active;
	
	@Column (name ="postal_code")
	private String postalCode;
	
	@Column (name ="iban", length = 25)
	private String iban;
	
	@Column (name ="bic", length = 20)
	private String bic;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "company_id")
	private CompanyDetails companyDetails;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id")
	private Set<EmployeeWorkPermitDetails> employeeWorkPermitDetails;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id")
	private Set<EmployeePaySlipDetails> employeePaySlipDetails;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id")
	private Set<EmployeePassportDetails> employeePassportDetails;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id")
	private Set<EmployeeFamilyDetails> employeeFamilyDetails;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id")
	private Set<EmployeeExpenseDetails> employeeExpenseDetails;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id")
	private Set<EmployeeTimeSheetDetails> employeeTimeSheetDetails;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id")
	private Set<EmployeeLeaveDetails> employeeLeaveDetails;

	/*
	 * @OneToMany(cascade = CascadeType.ALL)
	 * 
	 * @JoinColumn(name = "employee_id") private Set<EmployeeExpenseDetails>
	 * employeeExpenseDetails;
	 */
}
