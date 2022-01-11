package com.employee.product.dao.services;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.product.employeedetails.request.dto.AddEmployeeRequestDto;
import com.employee.product.entity.companydetails.Users;
import com.employee.product.entity.employeedetails.EmployeeDetails;

@Service
public class LoginDetailsService {
	
	@Autowired
	private EntityManager entity;
	
	
	@Transactional
	public Users updatePassword(String newPassword,String userName) {
		Users users = entity.find(Users.class, userName);
		users.setPassword(newPassword);
		return users;
	}
	
	@Transactional
	public Users getUser(String userName) {
		Users users = entity.find(Users.class, userName);
		return users;
	}

	@Transactional
	public Users updateUsername(EmployeeDetails empActual, AddEmployeeRequestDto addEmployeeRequestDto) {
		Users users = entity.find(Users.class, empActual.getEmailId());
		users.setUserName(addEmployeeRequestDto.getEmployeeDetails().getEmailId());
		if(!empActual.getFirstName().equals(addEmployeeRequestDto.getEmployeeDetails().getFirstName()))
			users.setFirstName(addEmployeeRequestDto.getEmployeeDetails().getFirstName());
		if(!empActual.getLastName().equals(addEmployeeRequestDto.getEmployeeDetails().getLastName()))
			users.setLastName(addEmployeeRequestDto.getEmployeeDetails().getLastName());
		return users;
		
	}
	

	
}
