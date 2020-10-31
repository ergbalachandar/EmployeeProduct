package com.employee.product.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.product.entity.employeedetails.EmployeeExpenseDetails;

public interface EmployeeExpenseDetailsInterface extends JpaRepository<EmployeeExpenseDetails, String> {

}
