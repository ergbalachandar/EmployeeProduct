package com.employee.product.companydetails.request.dto;

import lombok.Data;

@Data
public class LoginDetailsRequestDto {
	
	private String userName;
	
	private String password;
	
	private int reset;
	
	private String newPassword;

}
