package com.employee.product.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.product.entity.companydetails.CompanyDetails;

public interface CompanyDeleteInterface extends JpaRepository<CompanyDetails, String>  {

}
