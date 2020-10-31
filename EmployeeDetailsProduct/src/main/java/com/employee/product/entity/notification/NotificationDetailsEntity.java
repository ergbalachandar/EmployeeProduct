package com.employee.product.entity.notification;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "notifications_details")
public class NotificationDetailsEntity {

	@Id
	private String id;
	@Column(name = "message")
	private String message;
	@Column(name = "employee_id")
	private String employeeId;
	@Column(name = "status	")
	private boolean status;
	@Column(name = "created_date")
	private Date createdDate;

}
