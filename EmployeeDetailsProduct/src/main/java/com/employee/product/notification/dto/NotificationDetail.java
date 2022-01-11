package com.employee.product.notification.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationDetail {
	
	private String id;
	private String message;
	private String username;
	private boolean status;
	private Date createdDate;

}
