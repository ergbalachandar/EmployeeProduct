package com.employee.product.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.product.entity.employeedetails.EmployeePaySlipDetails;

public interface EmployeePayslipDetailsInterface extends JpaRepository<EmployeePaySlipDetails, String> {

}
