package com.employee.product.controller;

import java.time.Month;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
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
import com.employee.product.dao.services.NotificationService;
import com.employee.product.dao.services.PaySlipService;
import com.employee.product.dao.services.UserDetailsImpl;
import com.employee.product.documentdetails.request.dto.UploadDocumentDetailsRequestDto;
import com.employee.product.documentdetails.response.dto.UploadDocumentDetailsResponseDto;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.notification.NotificationDetailsEntity;
import com.employee.product.entity.ops.AuditTrailFE;
import com.employee.product.payslipsdetails.response.dto.EPaySlipEmpRes;
import com.employee.product.payslipsdetails.response.dto.EPaySlipResDto;
import com.employee.product.utils.NotificationUtil;
import com.employee.product.utils.PaySlipsDetailsUtil;
import com.employee.product.utils.UploadDocumentUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	@Autowired
	private NotificationService notifyService;
	
	@Autowired
	private DocumentManagementService documentManagementService;

	
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
		EPaySlipEmpRes ePaySlipEmpRes = new EPaySlipEmpRes();
		List<EmployeeDetails> employeeDetailsList = employeeProductService
				.findbyCompanyDetails(userDetails.getUsers().getCompanyDetails().getId());
		for(EmployeeDetails empDet:employeeDetailsList) {
			if(empDet.getEmailId().equals(userDetails.getUsers().getUserName())){
				if(null != empDet.getEmployeePaySlipDetails() && empDet.getEmployeePaySlipDetails().size() > 0) {
				PaySlipsDetailsUtil.mapEmpPaySlipDetails(empDet.getEmployeePaySlipDetails(), ePaySlipEmpRes, empDet.getId());
				commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
						userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
						"Employee view of Payslips retrieved Successfully", 1, "PAYLIST"));
				}
			}
			}
		
		return ePaySlipEmpRes;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/payslipDocument", consumes = { "multipart/form-data",
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Payslip Document", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Uploaded"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public UploadDocumentDetailsResponseDto payslipWithFile(@RequestPart("value") String value,
			@RequestParam("file") MultipartFile uploadFile) throws Exception {
		UploadDocumentDetailsResponseDto uploadDocumentDetailsResponseDto = new UploadDocumentDetailsResponseDto();
		byte[] bytes = uploadFile.getBytes();
		ObjectMapper mapper = new ObjectMapper();
		UserDetailsImpl userDetails = generateUserDetailsFromJWT("PAYSLIPDOCUMENT");
		UploadDocumentDetailsRequestDto uplReq = mapper.readValue(value,
				UploadDocumentDetailsRequestDto.class);
		try {
			String extension = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
			UploadDocumentUtil.uploadDocument(userDetails.getUsers().getUserName(), uplReq,
					bytes, documentManagementService, uploadFile.getOriginalFilename(),extension);
			EmployeeDetails emp = employeeProductService.findByEmployeeId(uplReq.getEmployeeId());
			notifyDet("Your monthly of "+Month.of(Integer.valueOf(uplReq.getMonth()))+" Payslip was generated",emp.getEmailId());
		} catch (Exception e) {
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"Failed to Upload" + e.getMessage(), 0, "PAYSLIPDOCUMENT"));
			throw new Exception("Could not add payslip");
		}
		UploadDocumentUtil.mapResponseUploadDocumentResponseDto(uploadDocumentDetailsResponseDto);
		commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
				userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
				"Successfully Payslip added", 1, "PAYSLIPDOCUMENT"));
		return uploadDocumentDetailsResponseDto;

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
