package com.employee.product.controller;

import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.product.dao.services.CommonService;
import com.employee.product.dao.services.LoginDetailsService;
import com.employee.product.dao.services.NotificationService;
import com.employee.product.dao.services.UserDetailsImpl;
import com.employee.product.entity.companydetails.Users;
import com.employee.product.entity.notification.NotificationDetailsEntity;
import com.employee.product.employeedetails.request.dto.ResetPwdEmployeeRequestDto;
import com.employee.product.employeedetails.response.dto.ResetPwdEmployeeResponseDto;
import com.employee.product.entity.ops.AuditTrailFE;
import com.employee.product.security.jwt.JwtUtils;
import com.employee.product.utils.NotificationUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/EProduct/pwd")
public class PasswordController {
	

	@Autowired
	private LoginDetailsService loginDet;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	NotificationService notifyService;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	CommonService commonService;
		
	/**
	 * 
	 * @param ResetPwdEmployeeRequestDto
	 * @return ResetPwdEmployeeResponseDto
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/reset")
	@ApiOperation(value = "RESET PASSWORD", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully password reset"),
			@ApiResponse(code = 401, message = "You are not authorized to reset password"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public ResetPwdEmployeeResponseDto changePwdForUser(@RequestBody ResetPwdEmployeeRequestDto req) throws Exception {
		UserDetailsImpl userDetails = generateUserDetailsFromJWT("CHANGEPWD");
		if(!"Admin".equals(userDetails.getUsers().getRole()) || userDetails.getUsers().getCompanyDetails().getActive() == 0) {
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"You are not authorized to view", 0, "CHANGEPWD"));
			throw new Exception("You are not authorized to Change PWD");
		}
		Users user = loginDet.getUser(req.getUserName());
		if(null == user || userDetails.getEmail().equals(user.getUserName())) {
			throw new Exception("Invalid UserName !! Please check");
		}
		loginDet.updatePassword(encoder.encode(req.getUserName()), req.getUserName());
		notifyDet("Your password reset is completed",req.getUserName());
		notifyDet("Requested Password is changed for "+user.getFirstName(),userDetails.getEmail());
        ResetPwdEmployeeResponseDto res = new ResetPwdEmployeeResponseDto();
		res.setMessage("Password Reset done");
		return res;
	}
	
	private UserDetailsImpl generateUserDetailsFromJWT(String module) throws Exception {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (userDetails.getUsers().getActive() == 0) {
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"Your profile has been deleted", 0, module));
			throw new Exception("Your profile has been deleted");
		}
		return userDetails;
	}
	
	private void notifyDet(String message, String userName) {
		NotificationDetailsEntity nde = new NotificationDetailsEntity();
		NotificationUtil.push(message,userName,nde);
		notifyService.pushNotifaction(nde);
	}

}
