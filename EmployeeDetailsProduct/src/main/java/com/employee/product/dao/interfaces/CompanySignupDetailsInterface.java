package com.employee.product.dao.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.employee.product.entity.companydetails.CompanyDetails;
import com.employee.product.entity.companydetails.Users;



public interface CompanySignupDetailsInterface  extends JpaRepository <Users, String>{
	

    

}
