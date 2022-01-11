
package com.employee.product.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.employee.product.IConstants;
import com.employee.product.dao.services.CommonService;
import com.employee.product.dao.services.DocumentManagementService;
import com.employee.product.dao.services.EmployeeProductService;
import com.employee.product.dao.services.ExpenseService;
import com.employee.product.dao.services.NotificationService;
import com.employee.product.dao.services.UserDetailsImpl;
import com.employee.product.documentdetails.request.dto.UploadDocumentDetailsRequestDto;
import com.employee.product.documentdetails.response.dto.UploadDocumentDetailsResponseDto;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.notification.NotificationDetailsEntity;
import com.employee.product.entity.ops.AuditTrailFE;
import com.employee.product.expensedetails.req.dto.ExpenseReq;
import com.employee.product.expensedetails.response.dto.Expense;
import com.employee.product.expensedetails.response.dto.ExpenseResDto;
import com.employee.product.utils.ExpenseDetailsUtil;
import com.employee.product.utils.NotificationUtil;
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
	private NotificationService notifyService;
	
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
		List<EmployeeDetails> expenseDetails = expenseService.retrieveExpenseByCompanyId(userDetails.getUsers().getCompanyDetails());
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
		if(null == uploadDocumentDetailsRequestDto.getEmployeeId())
			throw new Exception("Employee Id is missing");
		if(null == uploadDocumentDetailsRequestDto.getDocumentType())
			throw new Exception("Document type is invalid");

		String extension = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
		try {
			UploadDocumentUtil.uploadDocument(userDetails.getUsers().getUserName(), uploadDocumentDetailsRequestDto,
					bytes, documentManagementService, uploadFile.getOriginalFilename(), extension);
			notifyDet("Your Expense has been Submitted Successfully ",userDetails.getUsername());
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
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/expenseUpdate")
	@ApiOperation(value = "Expense Document", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public boolean expenseUpdate(@RequestBody ExpenseReq expenseReq) throws Exception {
		UserDetailsImpl userDetails = generateUserDetailsFromJWT("EXPENSEUPDATE");
		if(null == expenseReq || null == expenseReq.getExpenseId()) {
			throw new Exception(IConstants.INVALID_REQ);
		}
		if("Employee".equals(userDetails.getUsers().getRole()) && !("CAN".equals(expenseReq.getTypeStatus().name()))){
			new Exception(IConstants.EXPENSE_STATUS_CHANGE);
		}
		if("Admin".equals(userDetails.getUsers().getRole()) && "CAN".equals(expenseReq.getTypeStatus().name())){
			new Exception(IConstants.EXPENSE_STATUS_CHANGE);
		}
		expenseService.updateExpense(expenseReq,userDetails);
		EmployeeDetails emp = employeeProductService.findByEmployeeId(expenseReq.getExpenseId().substring(0, 12));
		if("INP".equals(expenseReq.getTypeStatus().name())) {
			notifyDet(IConstants.INPROGRESS,userDetails.getUsername());
			if(!emp.getEmailId().equals(userDetails.getUsername()))
				notifyDet(IConstants.INPROGRESS_EMP,emp.getEmailId());
		}else if("APP".equals(expenseReq.getTypeStatus().name())) {
			notifyDet(IConstants.APPROVED,userDetails.getUsername());
			if(!emp.getEmailId().equals(userDetails.getUsername()))
				notifyDet(IConstants.APPROVED_EMP,emp.getEmailId());
		}else if("REJ".equals(expenseReq.getTypeStatus().name())) {
			notifyDet(IConstants.REJECTED,userDetails.getUsername());
			if(!emp.getEmailId().equals(userDetails.getUsername()))
				notifyDet(IConstants.REJECTED_EMP,emp.getEmailId());
		}
		commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
				userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
				"Successfully Expense added", 1, "EXPENSEUPDATE"));
		return true;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/expenseList")
	@ApiOperation(value = "View Expense", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Payslips"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public List<Expense> retrieveExpenseForEmployee()
			throws Exception {
		UserDetailsImpl userDetails = this.generateUserDetailsFromJWT("EXPENSEEMPLIST");
		List<EmployeeDetails> employeeDetailsList = employeeProductService
				.findbyCompanyDetails(userDetails.getUsers().getCompanyDetails().getId());
		List<Expense> expenseEmp = new ArrayList<Expense>();
		for(EmployeeDetails empDet:employeeDetailsList) {
			if(empDet.getEmailId().equals(userDetails.getUsers().getUserName())){
				if(null != empDet.getEmployeeExpenseDetails() && empDet.getEmployeeExpenseDetails().size() > 0) {
				ExpenseDetailsUtil.mapEmpExpenseDetails(empDet.getEmployeeExpenseDetails(), expenseEmp, empDet.getId());
				commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
						userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
						"Employee view of Expense retrieved Successfully", 1, "EXPENSEEMPLIST"));
				}
			}
			}
		return expenseEmp;
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
