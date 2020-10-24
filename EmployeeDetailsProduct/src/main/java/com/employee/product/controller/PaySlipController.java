package com.employee.product.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.product.dao.services.CommonService;
import com.employee.product.dao.services.EmployeeProductService;
import com.employee.product.dao.services.PaySlipService;
import com.employee.product.dao.services.UserDetailsImpl;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDetails;
import com.employee.product.entity.ops.AuditTrailFE;
import com.employee.product.payslipsdetails.response.dto.EPaySlipEmpRes;
import com.employee.product.payslipsdetails.response.dto.EPaySlipResDto;
import com.employee.product.utils.PaySlipsDetailsUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/EProduct/payslips")
public class PaySlipController {
	
	@Autowired
	private PaySlipService paySlipService;
	
	@Autowired
	private EmployeeProductService employeeProductService;
	
	@Autowired
	CommonService commonService;

	
	@RequestMapping(method = RequestMethod.GET, value = "/payAdmlist")
	@ApiOperation(value = "View Payslips", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Payslips"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public EPaySlipResDto retrievePayslips(@RequestParam String m,@RequestParam String y)
			throws Exception {
		UserDetailsImpl userDetails = generateUserDetailsFromJWT("PAYADMLIST");
		if(!"Admin".equals(userDetails.getUsers().getRole()) || userDetails.getUsers().getCompanyDetails().getActive() == 0) {
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"You are not authorized to view", 0, "PAYADMLIST"));
			throw new Exception("You are not authorized to view");
		}
		List<EmployeeDetails> paySlipDetails = paySlipService.retrievePayslipsByCompanyId(userDetails.getUsers().getCompanyDetails());
		EPaySlipResDto ePaySlipRes = new EPaySlipResDto();
		PaySlipsDetailsUtil.mapPaySlipDetails(ePaySlipRes, paySlipDetails, m, y);
		commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
				userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
				"Admin view of Payslips retrieved Successfully", 1, "PAYADMLIST"));
		return ePaySlipRes;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/paylist")
	@ApiOperation(value = "View Payslips", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Payslips"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public EPaySlipEmpRes retrievePayslipsForEmployee()
			throws Exception {
		UserDetailsImpl userDetails = this.generateUserDetailsFromJWT("PAYLIST");
		Set<EmployeePaySlipDetails> paySlipDetails = null;

		List<EmployeeDetails> employeeDetailsList = employeeProductService
				.findbyCompanyDetails(userDetails.getUsers().getCompanyDetails().getId());
		for(EmployeeDetails empDet:employeeDetailsList) {
			if(empDet.getEmailId().equals(userDetails.getUsers().getUserName())){
				paySlipDetails = empDet.getEmployeePaySlipDetails();
				}
			}
		EPaySlipEmpRes ePaySlipEmpRes = new EPaySlipEmpRes();
		if(null != paySlipDetails && paySlipDetails.size() > 0) {
			PaySlipsDetailsUtil.mapEmpPaySlipDetails(paySlipDetails, ePaySlipEmpRes);
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"Employee view of Payslips retrieved Successfully", 1, "PAYLIST"));
		}
		return ePaySlipEmpRes;
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

}
