package com.employee.product.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;


import com.employee.product.entity.companydetails.Users;

public interface LoginDetailsInterface extends JpaRepository <Users, String> {

}
