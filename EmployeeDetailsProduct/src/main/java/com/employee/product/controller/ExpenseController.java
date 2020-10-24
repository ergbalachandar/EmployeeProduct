
package com.employee.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.product.dao.services.CommonService;
import com.employee.product.dao.services.ExpenseService;
import com.employee.product.dao.services.UserDetailsImpl;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.ops.AuditTrailFE;
import com.employee.product.expensedetails.response.dto.ExpenseResDto;
import com.employee.product.utils.ExpenseDetailsUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/EProduct")
public class ExpenseController {
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	private ExpenseService expenseService;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/expenseAdm")
	@ApiOperation(value = "Expense Details", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Expense Details"),
	@ApiResponse(code = 401, message = "No Expense Details Exist"),
	@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public ExpenseResDto retrieveExpenseDetails() throws Exception {
		UserDetailsImpl userDetails = this.generateUserDetailsFromJWT("EXPENSEDETAILS");
		if(!"Admin".equals(userDetails.getUsers().getRole()) || userDetails.getUsers().getCompanyDetails().getActive() == 0) {
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"You are not authorized to view", 0, "EXPENSEDETAILS"));
			throw new Exception("You are not authorized to view");
		}	
		List<EmployeeDetails> expenseDetails = expenseService.retrievePayslipsByCompanyId(userDetails.getUsers().getCompanyDetails());
		ExpenseResDto expenseRes = new ExpenseResDto();
		ExpenseDetailsUtil.mapExpenseDetails(expenseRes, expenseDetails);
		commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
				userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
				"Admin view of Payslips retrieved Successfully", 1, "PAYADMLIST"));
		return expenseRes;
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
