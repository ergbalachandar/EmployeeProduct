package com.employee.product.dao.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.product.dao.interfaces.LoginDetailsInterface;

import com.employee.product.entity.companydetails.Users;

@Service
public class LoginDetailsService {
	
	@Autowired
	public LoginDetailsInterface loginDetailsInterface;
	
	
	/**
	 * method to retrieve UserDetails
	 * @param companyDetails
	 */
	public Optional<Users> loginUser(Users users){		
				
		 Optional<Users> optionalUsers =  loginDetailsInterface.findById(users.getUserName());
		return optionalUsers;
	}
	
}
