package com.employee.product.dao.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.employee.product.entity.companydetails.Users;

public interface LoginDetailsInterface extends JpaRepository <Users, String> {
	
	
}
