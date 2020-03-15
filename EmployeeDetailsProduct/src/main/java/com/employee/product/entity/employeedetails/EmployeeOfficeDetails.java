//package com.employee.product.entity.employeedetails;
//
//import java.sql.Date;
//import java.util.Set;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.MapsId;
//import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//import com.employee.product.entity.companydetails.CompanyDetails;
//import com.employee.product.entity.companydetails.Users;
//
//import lombok.Data;
//
//@Data
//@Entity
//@Table(name = "employee_office_details")
//public class EmployeeOfficeDetails {
//
//	@Id
//	@Column(name = "id")
//	private int employeeId;
//
//	@OneToOne
//	@MapsId // This is used to map the column employee id as both PK and FK
//	//@JoinColumn(name = "employee_id")
//	private EmployeeDetails employee;
//
//
//	
//  /*  @ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "reporting_person_id")
//	private EmployeeDetails reportingPersonId; */
//
//}
