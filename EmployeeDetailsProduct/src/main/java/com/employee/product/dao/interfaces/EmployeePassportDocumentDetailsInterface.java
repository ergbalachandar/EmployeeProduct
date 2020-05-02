package com.employee.product.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.product.entity.employeedetails.EmployeePassportDocumentDetails;

public interface EmployeePassportDocumentDetailsInterface extends JpaRepository<EmployeePassportDocumentDetails, String> {

}
