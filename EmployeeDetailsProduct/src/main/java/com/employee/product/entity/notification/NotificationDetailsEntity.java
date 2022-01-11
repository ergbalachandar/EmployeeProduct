package com.employee.product.entity.notification;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "notifications_details")
public class NotificationDetailsEntity {

	@Id
	private String id;
	@Column(name = "message")
	private String message;
	@Column(name = "username")
	private String userName;
	@Column(name = "status	")
	private boolean status;
	@Column(name = "created_date", nullable = false, insertable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date createdDate;

}
