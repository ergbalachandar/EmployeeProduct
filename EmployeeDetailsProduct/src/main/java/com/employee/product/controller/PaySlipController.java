package com.employee.product.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.product.dao.services.EmployeeProductService;
import com.employee.product.dao.services.PaySlipService;
import com.employee.product.dao.services.UserDetailsImpl;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDetails;
import com.employee.product.payslipsdetails.request.dto.EPaySlipReqDto;
import com.employee.product.payslipsdetails.response.dto.EPaySlipEmpRes;
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
	

	@Autowired
	private EmployeeProductService employeeProductService;
	
	
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
		if(!"Admin".equals(userDetailsImpl.getUsers().getRole()) || userDetailsImpl.getUsers().getCompanyDetails().getActive() == 0) {
			throw new Exception("You are not authorized to view");
		}
		List<EmployeeDetails> paySlipDetails = paySlipService.retrievePayslipsByCompanyId(userDetailsImpl.getUsers().getCompanyDetails());
		EPaySlipResDto ePaySlipRes = new EPaySlipResDto();
		PaySlipsDetailsUtil.mapPaySlipDetails(ePaySlipRes, paySlipDetails);
		return ePaySlipRes;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/paylist")
	@ApiOperation(value = "View Payslips", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Payslips"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public EPaySlipEmpRes retrievePayslipsForEmployee(@RequestBody EPaySlipReqDto EPaySlipReqDto)
			throws Exception {
		UserDetailsImpl userDetailsImpl = generateUserDetailsFromJWT();
		Set<EmployeePaySlipDetails> paySlipDetails = null;

		List<EmployeeDetails> employeeDetailsList = employeeProductService
				.findbyCompanyDetails(userDetailsImpl.getUsers().getCompanyDetails().getId());
		for(EmployeeDetails empDet:employeeDetailsList) {
			System.out.println("---empDet---"+empDet.getEmailId());
			System.out.println("---UserName---"+userDetailsImpl.getUsers().getUserName());
			
			if(empDet.getEmailId().equals(userDetailsImpl.getUsers().getUserName())){
				paySlipDetails = empDet.getEmployeePaySlipDetails();
				}
			}
		EPaySlipEmpRes ePaySlipEmpRes = new EPaySlipEmpRes();
		if(null != paySlipDetails && paySlipDetails.size() > 0) {
			PaySlipsDetailsUtil.mapEmpPaySlipDetails(paySlipDetails, ePaySlipEmpRes);
		}
		return ePaySlipEmpRes;
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
