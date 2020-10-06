package com.employee.product.entity.ops;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "audit_trial")
public class AuditTrail {
	@Id
	@Column(name = "id")
	private int id;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "updated_user")
	private String updatedUser;
	@Column(name = "updated_company")
	private String UpdateCom;
	@Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date updatedAt;
	@Column(name = "module")
	private String module;
	@Column(name = "message")
	private String message;
	@Column(name = "status")
	private int status;
	 
}

