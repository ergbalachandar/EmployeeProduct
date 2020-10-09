package com.employee.product.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.product.entity.ops.AuditTrail;

public interface IAuditServiceInterface  extends JpaRepository<AuditTrail, String>   {
 
}
