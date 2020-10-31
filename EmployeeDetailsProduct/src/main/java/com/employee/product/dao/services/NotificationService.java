package com.employee.product.dao.services;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee.product.dao.interfaces.INotificationService;
import com.employee.product.entity.notification.NotificationDetailsEntity;

@Service
public class NotificationService  {
	
	@Autowired
	private EntityManager entity;

    @Autowired
    INotificationService notificationService;
    
    @Transactional
    public void pushNotifaction(NotificationDetailsEntity nDe) {
    	notificationService.save(nDe);
    }
    
    @Transactional
    public List<NotificationDetailsEntity> pullNotification(String employeeId) {
		return notificationService.getByEmployeeId(employeeId);
    }
    
    @Transactional
    public void updateNotification(NotificationDetailsEntity notify) {
		entity.merge(notify);
    }
    
    	
    
    
}
