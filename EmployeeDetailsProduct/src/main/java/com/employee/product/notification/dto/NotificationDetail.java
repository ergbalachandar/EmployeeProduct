package com.employee.product.notification.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationDetail {
	
	private String id;
	private String message;
	private String employeeId;
	private boolean status;
	private String createdDate;

}
