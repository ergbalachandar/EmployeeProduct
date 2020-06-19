package com.employee.product.entity.masterdetails;

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
@Table(name = "master_users")
public class MasterUsers {

	@Id
	@Column(name = "musername")
	private String mUserName;
	@Column(name = "mfirst_name")
	private String mFirstName;
	@Column(name = "mlast_name")
	private String mLastName;
	@Column(name = "mpassword")
	private String mPassword;
	@Column(name = "mrole")
	private String mRole;
	@Column(name = "created_At", nullable = false, insertable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Date createdAt;

	public MasterUsers(String mUsername, String mPassword) {
		this.mUserName = mUsername;
		this.mPassword = mPassword;
	}

	public MasterUsers() {
		
	}

}
