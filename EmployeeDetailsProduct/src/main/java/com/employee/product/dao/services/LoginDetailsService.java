package com.employee.product.dao.services;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.product.dao.interfaces.LoginDetailsInterface;
import com.employee.product.entity.companydetails.Users;

@Service
public class LoginDetailsService {
	
	@Autowired
	public LoginDetailsInterface loginDetailsInterface;
	
	@Autowired
	private EntityManager entity;
	
	
	@Transactional
	public Users updatePassword(String newPassword,String userName) {
		
		Users users = entity.find(Users.class, userName);
		users.setPassword(newPassword);
		return users;
	}
	

	
}
