package com.employee.product.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.product.entity.masterdetails.MasterUsers;

public interface IEMasterServiceInterface  extends JpaRepository<MasterUsers, String>   {
 
}
