package com.employee.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.product.dao.services.DeleteCompanyService;
import com.employee.product.dao.services.PaySlipService;
import com.employee.product.dao.services.UserDetailsImpl;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.payslipsdetails.request.dto.EPaySlipReqDto;
import com.employee.product.payslipsdetails.response.dto.EPaySlipResDto;
import com.employee.product.security.jwt.JwtUtils;
import com.employee.product.utils.PaySlipsDetailsUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/payslips")
public class PaySlipController {
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	private PaySlipService paySlipService;
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/payAdmlist")
	@ApiOperation(value = "View Payslips", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Payslips"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public EPaySlipResDto retrievePayslips(@RequestBody EPaySlipReqDto EPaySlipReqDto)
			throws Exception {
		UserDetailsImpl userDetailsImpl = generateUserDetailsFromJWT();
		List<EmployeeDetails> paySlipDetails = paySlipService.retrievePayslipsByCompanyId(userDetailsImpl.getUsers().getCompanyDetails());
		EPaySlipResDto ePaySlipRes = new EPaySlipResDto();
		PaySlipsDetailsUtil.mapPaySlipDetails(ePaySlipRes, paySlipDetails);
		return ePaySlipRes;
	}
	
	
	private static UserDetailsImpl generateUserDetailsFromJWT() throws Exception {
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (userDetailsImpl.getUsers().getActive() == 0) {
			throw new Exception("Your profile has been deleted");
		}
		return userDetailsImpl;
	}

}
