package com.employee.product.notification.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationDetails {
	
	int count;
	List<NotificationDetail> notificationDetail;

}
