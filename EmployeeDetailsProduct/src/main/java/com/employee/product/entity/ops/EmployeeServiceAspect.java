package com.employee.product.entity.ops;


import java.sql.Date;

import javax.transaction.Transactional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.employee.product.dao.interfaces.IAuditServiceInterface;

@Aspect
@Component
public class EmployeeServiceAspect {
	
	@Autowired
	IAuditServiceInterface auditService;

	@Before(value = "execution(* com.employee.product.dao.services.CommonService.*(..)) and args(auditFE)")
	public void beforeAdvice(JoinPoint joinPoint, AuditTrailFE auditFE) {
		//System.out.println("Before method:" + joinPoint.getSignature());
		setAuditTrail(auditFE);
		
	}

	@Transactional
	private void setAuditTrail(AuditTrailFE auditFE) {
		AuditTrail audit = new AuditTrail();
		audit.setUpdatedAt(new Date(new java.util.Date().getTime()));
		audit.setUpdatedBy(auditFE.getUser());
		audit.setUpdateCom(auditFE.getCompany());
		audit.setUpdatedUser(auditFE.getRole());
		audit.setMessage(auditFE.getMessage());
		audit.setStatus(auditFE.getStatus());
		audit.setModule(auditFE.getModule());
		auditService.save(audit);
	}

	@After(value = "execution(* com.employee.product.dao.services.CommonService.*(..)) and args(user,company)")
	public void afterAdvice(JoinPoint joinPoint, String user,String company) {
		//System.out.println("After method:" + joinPoint.getSignature());

		//System.out.println("Creating Employee with aid - " + id);
	}
}
