package com.employee.product.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.employee.product.entity.notification.NotificationDetailsEntity;
import com.employee.product.notification.dto.NotificationDetail;
import com.employee.product.notification.dto.NotificationDetails;
import com.employee.product.notification.dto.NotificationReq;

public class NotificationUtil {

	public static void push(String message, String empId, NotificationDetailsEntity nDe) {
		nDe.setUserName(empId);
		nDe.setId(empId.split("@")[0]+""+new java.util.Date().getTime());
		nDe.setMessage(message);
		nDe.setStatus(false);
	}

	public static void mapNotifications(NotificationDetails notifyDetails, List<NotificationDetailsEntity> pullNotification) {
		if(!pullNotification.isEmpty()) {
			Comparator<NotificationDetailsEntity> comp = new Comparator<NotificationDetailsEntity>() {
				@Override
				public int compare(NotificationDetailsEntity o1, NotificationDetailsEntity o2) {
					return o2.getCreatedDate().compareTo(o1.getCreatedDate());
				}
			};
			pullNotification.sort(comp);
			List<NotificationDetail> notificationDetailList = new ArrayList<NotificationDetail>();
			int unRead = 0;
			for(NotificationDetailsEntity notify:pullNotification) {
				NotificationDetail nd = new NotificationDetail();
				nd.setCreatedDate(notify.getCreatedDate());
				nd.setUsername(notify.getUserName()); 
				nd.setId(notify.getId());
				nd.setMessage(notify.getMessage());
				nd.setStatus(notify.isStatus());
				if(!notify.isStatus())
					unRead++;
				notificationDetailList.add(nd);
			}
			if(!pullNotification.isEmpty())
				notifyDetails.setCount(pullNotification.size());
			notifyDetails.setUnRead(unRead);
			notifyDetails.setNotificationDetail(notificationDetailList);
		}
	}
	
	public static void updateNotify(NotificationDetailsEntity ent, NotificationReq notify, String userName) {
		ent.setId(notify.getId());
		ent.setStatus(notify.isStatus());
		ent.setUserName(userName);
	}

}
