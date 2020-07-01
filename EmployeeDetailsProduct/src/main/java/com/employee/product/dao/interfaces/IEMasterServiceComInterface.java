package com.employee.product.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.product.entity.masterdetails.CompanyDetailsMadm;

public interface IEMasterServiceComInterface extends JpaRepository<CompanyDetailsMadm, String>   {
 
}
