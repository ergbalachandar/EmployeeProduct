package com.employee.product.dao.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.product.entity.notification.NotificationDetailsEntity;

public interface INotificationService extends JpaRepository <NotificationDetailsEntity, String> {
	
	public List<NotificationDetailsEntity> getByUserName(String userName);
	public void deleteByUserName(String userName);

}
