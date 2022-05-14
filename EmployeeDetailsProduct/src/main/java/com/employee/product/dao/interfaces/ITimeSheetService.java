package com.employee.product.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.product.entity.employeedetails.EmployeeLeaveDetails;

public interface ITimeSheetService extends JpaRepository <EmployeeLeaveDetails, String> {

}
