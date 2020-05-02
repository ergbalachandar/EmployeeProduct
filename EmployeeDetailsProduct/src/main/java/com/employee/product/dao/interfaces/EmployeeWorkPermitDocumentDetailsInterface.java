package com.employee.product.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.product.entity.employeedetails.EmployeeWorkPermitDocumentDetails;

public interface EmployeeWorkPermitDocumentDetailsInterface
		extends JpaRepository<EmployeeWorkPermitDocumentDetails, String> {

}
