package com.employee.product.utils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.employee.product.entity.notification.NotificationDetailsEntity;
import com.employee.product.notification.dto.NotificationDetail;
import com.employee.product.notification.dto.NotificationDetails;
import com.employee.product.notification.dto.NotificationReq;

public class NotificationUtil {

	public static void push(String message, String empId, NotificationDetailsEntity nDe) {
		nDe.setCreatedDate(new Date(new java.util.Date().getTime()));
		nDe.setEmployeeId(empId);
		nDe.setId(empId+""+new java.util.Date().getTime());
		nDe.setMessage(message);
		nDe.setStatus(true);
	}

	public static void mapNotifications(NotificationDetails notifyDetails, List<NotificationDetailsEntity> pullNotification) {
		if(!pullNotification.isEmpty()) {
			List<NotificationDetail> notificationDetailList = new ArrayList<NotificationDetail>();
			for(NotificationDetailsEntity notify:pullNotification) {
				NotificationDetail nd = new NotificationDetail();
				nd.setCreatedDate(String.valueOf(notify.getCreatedDate()));
				nd.setEmployeeId(notify.getEmployeeId());
				nd.setId(notify.getId());
				nd.setMessage(notify.getMessage());
				nd.setStatus(notify.isStatus());
				notificationDetailList.add(nd);
			}
			if(!pullNotification.isEmpty())
				notifyDetails.setCount(pullNotification.size());
			notifyDetails.setNotificationDetail(notificationDetailList);
		}
	}
	
	public static void updateNotify(NotificationDetailsEntity ent, NotificationReq notify) {
		ent.setId(notify.getId());
		ent.setStatus(notify.isStatus());
	}

}
