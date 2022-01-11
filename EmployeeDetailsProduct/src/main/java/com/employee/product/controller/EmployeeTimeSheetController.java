package com.employee.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.product.dao.interfaces.ITimeSheetService;
import com.employee.product.dao.services.CommonService;
import com.employee.product.dao.services.EmployeeProductService;
import com.employee.product.dao.services.NotificationService;
import com.employee.product.dao.services.UserDetailsImpl;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.notification.NotificationDetailsEntity;
import com.employee.product.entity.ops.AuditTrailFE;
import com.employee.product.payslipsdetails.response.dto.EPaySlipEmpRes;
import com.employee.product.timesheet.res.dto.ELeavesRes;
import com.employee.product.utils.NotificationUtil;
import com.employee.product.utils.PaySlipsDetailsUtil;
import com.employee.product.utils.TimeSheetDetailsUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;


@RestController
@RequestMapping("/EProduct/timesheets")
public class EmployeeTimeSheetController {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private NotificationService notifyService;
	
	@Autowired
	private EmployeeProductService employeeProductService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/empleaves")
	@ApiOperation(value = "View Leaves for Employee", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved EmployeeLeaves"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public ELeavesRes retrieveLeavesForEmployee()
			throws Exception {
		UserDetailsImpl userDetails = this.generateUserDetailsFromJWT("LEAVES");
		List<EmployeeDetails> employeeDetailsList = employeeProductService
				.findbyCompanyDetails(userDetails.getUsers().getCompanyDetails().getId());
		ELeavesRes empLeaveRes = new ELeavesRes();
		for(EmployeeDetails empDet:employeeDetailsList) {
			if(empDet.getEmailId().equals(userDetails.getUsers().getUserName())){
				if(null != empDet.getEmployeeLeaveDetails()) {
				TimeSheetDetailsUtil.mapLeaveDetails(empLeaveRes,empDet.getEmployeeLeaveDetails());
				commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
						userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
						"Employee view of Payslips retrieved Successfully", 1, "PAYLIST"));
				}
			}
			}
			
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/admleaves")
	@ApiOperation(value = "View Leaves for all Employees as admin", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved EmployeeLeaves"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public EPaySlipEmpRes retrieveLeavesForAdmin()
			throws Exception {
		UserDetailsImpl userDetails = this.generateUserDetailsFromJWT("LEAVES_ADMIN");
		
			
		return null;
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
