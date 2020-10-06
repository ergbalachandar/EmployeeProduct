package com.employee.product.entity.ops;

import lombok.Data;

@Data
public class AuditTrailFE {
	
	private String user;
	private String company;
	private String role;
	private String message;
	private int status;
	private String module;
	
	public AuditTrailFE(String user, String company,String role,String message,int status,String module){
		this.user=user;
		this.company=company;
		this.role=role;
		this.message=message;
		this.status=status;
		this.module=module;
	}

}
