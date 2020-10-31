
package com.employee.product.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.employee.product.dao.services.CommonService;
import com.employee.product.dao.services.DocumentManagementService;
import com.employee.product.dao.services.EmployeeProductService;
import com.employee.product.dao.services.ExpenseService;
import com.employee.product.dao.services.UserDetailsImpl;
import com.employee.product.documentdetails.request.dto.UploadDocumentDetailsRequestDto;
import com.employee.product.documentdetails.response.dto.UploadDocumentDetailsResponseDto;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.employeedetails.EmployeeExpenseDetails;
import com.employee.product.entity.ops.AuditTrailFE;
import com.employee.product.expensedetails.response.dto.ExpenseResDto;
import com.employee.product.expensedetails.response.dto.Expenses;
import com.employee.product.utils.ExpenseDetailsUtil;
import com.employee.product.utils.UploadDocumentUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	@Autowired
	private DocumentManagementService documentManagementService;

	@Autowired
	private EmployeeProductService employeeProductService;
	
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
		UserDetailsImpl userDetails = this.generateUserDetailsFromJWT("EXPENSEADMINDETAILS");
		if(!"Admin".equals(userDetails.getUsers().getRole()) || userDetails.getUsers().getCompanyDetails().getActive() == 0) {
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"You are not authorized to view", 0, "EXPENSEADMINDETAILS"));
			throw new Exception("You are not authorized to view");
		}	
		List<EmployeeDetails> expenseDetails = expenseService.retrievePayslipsByCompanyId(userDetails.getUsers().getCompanyDetails());
		ExpenseResDto expenseRes = new ExpenseResDto();
		ExpenseDetailsUtil.mapExpenseDetails(expenseRes, expenseDetails);
		commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
				userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
				"Admin view of Payslips retrieved Successfully", 1, "EXPENSEADMINDETAILS"));
		return expenseRes;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/expenseDocument", consumes = { "multipart/form-data",
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Expense Document", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Uploaded"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public UploadDocumentDetailsResponseDto expenseWithFile(@RequestPart("value") String value,
			@RequestParam("file") MultipartFile uploadFile) throws Exception {
		UploadDocumentDetailsResponseDto uploadDocumentDetailsResponseDto = new UploadDocumentDetailsResponseDto();
		byte[] bytes = uploadFile.getBytes();
		ObjectMapper mapper = new ObjectMapper();
		UserDetailsImpl userDetails = generateUserDetailsFromJWT("EXPENSEDOCUMENT");
		UploadDocumentDetailsRequestDto uploadDocumentDetailsRequestDto = mapper.readValue(value,
				UploadDocumentDetailsRequestDto.class);
		try {
			UploadDocumentUtil.uploadDocument(userDetails.getUsers().getUserName(), uploadDocumentDetailsRequestDto,
					bytes, documentManagementService, uploadFile.getOriginalFilename());
		} catch (Exception e) {
			e.printStackTrace();
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"Failed to Upload" + e.getMessage(), 0, "EXPENSEDOCUMENT"));
			throw new Exception("Could not add Expense");
		}
		UploadDocumentUtil.mapResponseUploadDocumentResponseDto(uploadDocumentDetailsResponseDto);
		commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
				userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
				"Successfully Expense added", 1, "EXPENSEDOCUMENT"));
		return uploadDocumentDetailsResponseDto;

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/expenseList")
	@ApiOperation(value = "View Payslips", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Payslips"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public Expenses retrieveExpenseForEmployee()
			throws Exception {
		UserDetailsImpl userDetails = this.generateUserDetailsFromJWT("EXPENSEEMPLIST");
		Set<EmployeeExpenseDetails> empExpDetails = null;

		List<EmployeeDetails> employeeDetailsList = employeeProductService
				.findbyCompanyDetails(userDetails.getUsers().getCompanyDetails().getId());
		for(EmployeeDetails empDet:employeeDetailsList) {
			if(empDet.getEmailId().equals(userDetails.getUsers().getUserName())){
				empExpDetails = empDet.getEmployeeExpenseDetails();
				}
			}
		Expenses expenseRes = new Expenses();
		if(null != empExpDetails && empExpDetails.size() > 0) {
			ExpenseDetailsUtil.mapEmpExpenseDetails(empExpDetails, expenseRes);
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"Employee view of Expense retrieved Successfully", 1, "EXPENSEEMPLIST"));
		}
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
