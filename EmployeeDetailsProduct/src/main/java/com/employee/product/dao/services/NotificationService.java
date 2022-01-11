package com.employee.product.dao.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
    public List<NotificationDetailsEntity> pullNotification(String userName) {
		return notificationService.getByUserName(userName);
    }
    

    @Transactional
    public void removeAllByUserName(String userName) {
    	notificationService.deleteByUserName(userName);
    }
    
    @Transactional
    public long notifCount(String userName) {
    	return notificationService.getByUserName(userName).stream().filter(a -> !a.isStatus()).count();
    }
    
    
    @Transactional
    public Optional<NotificationDetailsEntity> getById(String id) {
    	return notificationService.findById(id);
    }
    
    @Transactional
    public void updateNotification(NotificationDetailsEntity notify) {
		entity.merge(notify);
    }
    
    	
    
    
}
