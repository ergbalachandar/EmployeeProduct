package com.employee.product.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
/*import org.springframework.mail.MailSender;*/
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.employee.product.companydetails.dto.CompanyDetailsDto;
import com.employee.product.companydetails.request.dto.CompanyDetailsRequestDto;
import com.employee.product.companydetails.request.dto.LoginDetailsRequestDto;
import com.employee.product.companydetails.response.dto.CompanyDetailsResponseDto;
import com.employee.product.companydetails.response.dto.DeleteCompanyResponseDto;
import com.employee.product.companydetails.response.dto.LoginDetailsResponseDto;
import com.employee.product.dao.services.CommonService;
import com.employee.product.dao.services.DeleteCompanyService;
import com.employee.product.dao.services.DocumentManagementService;
import com.employee.product.dao.services.EmployeeProductService;
import com.employee.product.dao.services.LoginDetailsService;
import com.employee.product.dao.services.UserDetailsImpl;
import com.employee.product.documentdetails.request.dto.DeleteDocumentRequestDto;
import com.employee.product.documentdetails.request.dto.UploadDocumentDetailsRequestDto;
import com.employee.product.documentdetails.response.dto.DeleteDocumentResponseDto;
import com.employee.product.documentdetails.response.dto.UploadDocumentDetailsResponseDto;
import com.employee.product.employeedetails.request.dto.AddEmployeeRequestDto;
import com.employee.product.employeedetails.request.dto.DeleteEmployeeRequestDto;
import com.employee.product.employeedetails.request.dto.EmployeeDataRequestDto;
import com.employee.product.employeedetails.request.dto.RetrieveEmployeeDataRequestDto;
import com.employee.product.employeedetails.response.dto.DeleteEmployeeResponseDto;
import com.employee.product.employeedetails.response.dto.EmployeeDataResponseDto;
import com.employee.product.employeedetails.response.dto.EmployeeDetailsResponseDto;
import com.employee.product.entity.companydetails.CompanyDetails;
import com.employee.product.entity.companydetails.Users;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.employeedetails.EmployeePassportDocumentDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDocumentDetails;
import com.employee.product.entity.employeedetails.EmployeeWorkPermitDocumentDetails;
import com.employee.product.entity.ops.AuditTrailFE;
import com.employee.product.security.jwt.JwtUtils;
import com.employee.product.utils.AddEmployeeDetailsUtil;
import com.employee.product.utils.CompanySignUpDetailsUtil;
import com.employee.product.utils.DeleteCompanyUtil;
import com.employee.product.utils.DeleteDocumentUtil;
import com.employee.product.utils.DeleteEmployeeResponseUtil;
import com.employee.product.utils.DownloadDocumentUtil;
import com.employee.product.utils.EmployeeDetailsUtil;
import com.employee.product.utils.GenerateCsvReportUtil;
import com.employee.product.utils.GenerateExcelReportUtil;
import com.employee.product.utils.GeneratePdfReportUtil;
import com.employee.product.utils.LoginUserUtil;
import com.employee.product.utils.UploadDocumentUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController

@RequestMapping("/EProduct")
public class EmployeeProductController {

	@Autowired
	private EmployeeProductService employeeProductService;

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private DocumentManagementService documentManagementService;

	@Autowired
	private CommonService commonService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	/*
	 * @Autowired private MailSender mailSender;
	 */

	@Autowired
	private DeleteCompanyService deleteCompanyService;

	/**
	 * Method to SignUp Company
	 * 
	 * @param CompanyDetailsDto
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/user/companysignup")
	@ApiOperation(value = "Sign Up Company")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Signed Up"),
			@ApiResponse(code = 401, message = "You are not authorized to sign Up"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public CompanyDetailsResponseDto signUpCompanyDetails(@RequestBody CompanyDetailsRequestDto companyDetailsDto,
			HttpSession httpSession) {
		Users users = new Users();
		CompanyDetailsResponseDto companyDetailsResponseDto = new CompanyDetailsResponseDto();
		CompanySignUpDetailsUtil.companySignUpDetailsMapping(companyDetailsDto, users);
		users.setPassword(encoder.encode(companyDetailsDto.getPassword()));
		users = employeeProductService.signUpCompanyDetails(users);
		// CompanySignUpDetailsUtil.sendMessage(mailSender,companyDetailsDto.getEmailId(),
		// companyDetailsDto.getCompanyName(), users);
		CompanySignUpDetailsUtil.companyDetailsSignUpResponseMapping(companyDetailsResponseDto);
		commonService.setAudit(new AuditTrailFE(companyDetailsDto.getEmployeeDetails().getFirstName(),companyDetailsDto.getCompanyName(),"Admin" ,
				"SignUp Successfully", 1, "SIGNUP"));
		return companyDetailsResponseDto;
	}

	/**
	 * Method to Login User
	 * 
	 * @param LoginDetailsrequestDto
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/user/loginUser")
	@ApiOperation(value = "LoginUser")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Logged In"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public ResponseEntity<?> loginUser(@RequestBody LoginDetailsRequestDto loginDetailsRequestDto,
			HttpSession httpSession) throws Exception {

		LoginDetailsResponseDto loginDetailsResponseDto = new LoginDetailsResponseDto();
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDetailsRequestDto.getUserName(), loginDetailsRequestDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		if (userDetails.getUsers().getCompanyDetails().getActive() == 0 || userDetails.getUsers().getActive() == 0) {
			// Need to trigger mail with support email id
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"User has been removed/resigned", 0, "LOGIN"));
			throw new Exception("You are not authorized to Log In Please contact Us");
		}
		if (loginDetailsRequestDto.getReset() == 0) {
			LoginUserUtil.mapLoginDetailsResponseDto(userDetails.getUsers(), loginDetailsResponseDto);
			loginDetailsResponseDto.setJwt(jwt);
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"User Login Successful", 1, "LOGIN"));
		} else {
			Users usersWithNewPassword = loginDetailsService.updatePassword(
					encoder.encode(loginDetailsRequestDto.getNewPassword()), loginDetailsRequestDto.getUserName());
			LoginUserUtil.mapLoginDetailsResponseDto(usersWithNewPassword, loginDetailsResponseDto);
			loginDetailsResponseDto.setJwt(null);
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"Password changed Successfully", 1, "LOGIN"));
		}
		return ResponseEntity.ok(loginDetailsResponseDto);

	}

	/**
	 * Method to retrieve Employee List
	 * 
	 * @param EmployeeDataResponseDto
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/retrieveEmployeeList")
	@ApiOperation(value = "RetrieveEmployeeList", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved EmployeeList"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public EmployeeDataResponseDto retrieveEmployeeList() throws Exception {
		EmployeeDataResponseDto employeeDataResponseDto = new EmployeeDataResponseDto();
		UserDetailsImpl userDetails = generateUserDetailsFromJWT("RETRIEVEEMPLOYEELIST");

		if (!userDetails.getUsers().getRole().equalsIgnoreCase("Admin")) {
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"ONLY ADMIN ACCESS", 0, "RETRIEVEEMPLOYEELIST"));
			throw new Exception("You are not authorised to retrieve the list of Employees");
		}
		List<EmployeeDetails> employeeDetailsList = employeeProductService
				.findbyCompanyDetails(userDetails.getUsers().getCompanyDetails().getId());

		EmployeeDetailsUtil.mappingEmployeeDataResponse(employeeDetailsList, employeeDataResponseDto);
		commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
				userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
				"Successfully retrieved EmployeeList", 1, "RETRIEVEEMPLOYEELIST"));
		return employeeDataResponseDto;

	}

	/**
	 * Method to retrieve Employee Data
	 * 
	 * @param EmployeeDetailsRequestDto
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/retrieveEmployeeData")
	@ApiOperation(value = "RetrieveEmployeeData", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Employee Data"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody

	public EmployeeDetailsResponseDto retrieveEmployeeList(
			@RequestBody RetrieveEmployeeDataRequestDto retrieveEmployeeDataRequestDto) throws Exception {
		EmployeeDetailsResponseDto employeeDetailsResponseDto = new EmployeeDetailsResponseDto();
		UserDetailsImpl userDetails = generateUserDetailsFromJWT("RETRIEVEEMPLOYEEDATA");
		EmployeeDetails employeeDetails = employeeProductService
				.findByEmployeeId(retrieveEmployeeDataRequestDto.getEmployeeId());
		if (userDetails.getUsers().getCompanyDetails().getId()
				.equalsIgnoreCase(employeeDetails.getCompanyDetails().getId())) {
			EmployeeDetailsUtil.mapEmployeeDetails(employeeDetailsResponseDto, employeeDetails, false);
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"Successfully retrieved EmployeeData", 1, "RETRIEVEEMPLOYEEDATA"));
		} else {
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"Not Authorised to access this module", 1, "RETRIEVEEMPLOYEEDATA"));
			throw new Exception("You are not authorised to retrieve the employee Data of other Company");
		}
		return employeeDetailsResponseDto;

	}

	/**
	 * a Method to Add or Modify Employee
	 * 
	 * @param EmployeeDetailsRequestDto
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/addModifyEmployee")
	@ApiOperation(value = "Add or Update Employee", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added or updated EmployeeDetails"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody

	public EmployeeDetailsResponseDto addOrUpdateEmployeeList(@RequestBody AddEmployeeRequestDto addEmployeeRequestDto)
			throws Exception {

		Users users = new Users();
		boolean newEmployee = false;
		EmployeeDetails employeeDetails = new EmployeeDetails();
		UserDetailsImpl userDetails = generateUserDetailsFromJWT("ADDMODIFYEMPLOYEE");
		CompanyDetails companyDetails = employeeProductService.findCompanyDetails(addEmployeeRequestDto.getCompanyId());
		newEmployee = AddEmployeeDetailsUtil.checkForNewOrUpdateEmployee(newEmployee, addEmployeeRequestDto);
		AddEmployeeDetailsUtil.mapAddEmployeeRequest(addEmployeeRequestDto, users, employeeDetails, companyDetails,
				newEmployee, encoder);
		employeeDetails = employeeProductService.addOrUpdateEmployeeDetails(employeeDetails, users, companyDetails,
				newEmployee, userDetails.getUsers().getUserName());
		EmployeeDetailsResponseDto employeeDetailsResponseDto = new EmployeeDetailsResponseDto();
		EmployeeDetailsUtil.mapEmployeeDetails(employeeDetailsResponseDto, employeeDetails, false);
		if(newEmployee) {
		commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
				userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
				"Successfully added an Employee", 1, "ADDMODIFYEMPLOYEE"));
		}else {
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"Successfully Modified an Employee", 1, "ADDMODIFYEMPLOYEE"));
		}
		return employeeDetailsResponseDto;

	}

	/**
	 * a Method to Generate PDF
	 * 
	 * @param EmployeeDetailsRequestDto
	 * @throws Exception
	 */
	@RequestMapping(value = "/employeeListReport", method = RequestMethod.POST, produces = MediaType.APPLICATION_PDF_VALUE)
	@ApiOperation(value = "Generate EmployeeReport PDF or XLSX", authorizations = {
			@Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully GeneratedPDF"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<InputStreamResource> employeePdfReport(
			@RequestBody EmployeeDataRequestDto employeeDataRequestDto, HttpServletResponse response) throws Exception {

		UserDetailsImpl userDetails = generateUserDetailsFromJWT("EMPLOYEELISTREPORT");

		if (!userDetails.getUsers().getRole().equalsIgnoreCase("Admin")) {
			throw new Exception("You are not authorised to retrieve the list of Employees");
		}

		List<EmployeeDetails> employeeDetailsList = employeeProductService
				.findbyCompanyDetails(userDetails.getUsers().getCompanyDetails().getId());
		ByteArrayInputStream bis = null;
		HttpHeaders headers = new HttpHeaders();

		if (employeeDataRequestDto.getDocumentType() == 1) {

			bis = GeneratePdfReportUtil.employeeReport(employeeDetailsList,
					userDetails.getUsers().getCompanyDetails().getCompanyName());

			headers.add("Content-Disposition", "inline; filename=EmployeeReport.pdf");
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"Successfully PDF GENERATED FOR DOWNLOAD", 1, "EMPLOYEELISTREPORT"));
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
					.body(new InputStreamResource(bis));
		}

		else if (employeeDataRequestDto.getDocumentType() == 2) {
			bis = GenerateExcelReportUtil.employeeReport(employeeDetailsList,
					userDetails.getUsers().getCompanyDetails().getCompanyName());
			headers.add("Content-Disposition", "inline; filename=EmployeeReport.xlsx");
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"Successfully EXCEL GENERATED FOR DOWNLOAD", 1, "EMPLOYEELISTREPORT"));
			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bis));
		} else {
			throw new Exception("Invalid Document Type");
		}

	}

	@ApiOperation(value = "Generate EmployeeReport CSV", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully GeneratedCSV"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@RequestMapping(value = "/employeeListCsvReport", method = RequestMethod.POST)
	public void generateCsvEmployeeReport(@RequestBody EmployeeDataRequestDto employeeDataRequestDto,
			HttpServletResponse response) throws Exception {
		UserDetailsImpl userDetails = generateUserDetailsFromJWT("EMPLOYEELISTREPORT");
		if (employeeDataRequestDto.getDocumentType() == 3) {

			if (!userDetails.getUsers().getRole().equalsIgnoreCase("Admin")) {
				throw new Exception("You are not authorised to retrieve the list of Employees");
			}

			List<EmployeeDetails> employeeDetailsList = employeeProductService
					.findbyCompanyDetails(userDetails.getUsers().getCompanyDetails().getId());

			GenerateCsvReportUtil.generateEmployeeDetails(response.getWriter(), employeeDetailsList,
					userDetails.getUsers().getCompanyDetails().getCompanyName());
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"Successfully CSV GENERATED FOR DOWNLOAD", 1, "EMPLOYEELISTREPORT"));
			response.setHeader("Content-Disposition", "attachment; filename=AllUsersCSVReport.csv");
		} else {
			throw new Exception("Invalid Document Type");
		}

	}

	/**
	 * a Method to Delete Employee
	 * 
	 * @param DeleteEmployeeRequestDto
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/deleteEmployee")
	@ApiOperation(value = "Delete Employee", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public DeleteEmployeeResponseDto deleteEmployee(@RequestBody DeleteEmployeeRequestDto deleteEmployeeRequestDto)
			throws Exception {
		UserDetailsImpl userDetails = generateUserDetailsFromJWT("DELETEEMPLOYEE");
		DeleteEmployeeResponseDto deleteEmployeeResponseDto = new DeleteEmployeeResponseDto();

		if (!userDetails.getUsers().getRole().equalsIgnoreCase("Admin")) {
			throw new Exception("You are not authorised to Delete the employee");
		}

		employeeProductService.deleteEmployee(deleteEmployeeRequestDto.getUserName(),
				userDetails.getUsers().getCompanyDetails().getId());
		DeleteEmployeeResponseUtil.mapResponseDeleteEmployeeResponse(deleteEmployeeResponseDto);
		commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
				userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
				"Successfully Deleted Employee", 1, "DELETEEMPLOYEE"));
		return deleteEmployeeResponseDto;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/uploadDocument", consumes = { "multipart/form-data",
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Upload Document", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Uploaded"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public UploadDocumentDetailsResponseDto uploadFile(@RequestPart("value") String value,
			@RequestParam("file") MultipartFile uploadFile) throws Exception {
		UploadDocumentDetailsResponseDto uploadDocumentDetailsResponseDto = new UploadDocumentDetailsResponseDto();
		byte[] bytes = uploadFile.getBytes();
		ObjectMapper mapper = new ObjectMapper();
		UserDetailsImpl userDetails = generateUserDetailsFromJWT("UPLOADDOCUMENT");
		UploadDocumentDetailsRequestDto uploadDocumentDetailsRequestDto = mapper.readValue(value,
				UploadDocumentDetailsRequestDto.class);
		try {
			UploadDocumentUtil.uploadDocument(userDetails.getUsers().getUserName(), uploadDocumentDetailsRequestDto,
					bytes, documentManagementService, uploadFile.getOriginalFilename());
		} catch (Exception e) {
			e.printStackTrace();
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"Failed to Upload"+e.getMessage(), 0, "UPLOADDOCUMENT"));
			throw new Exception("Could not upload Document");
		}
		UploadDocumentUtil.mapResponseUploadDocumentResponseDto(uploadDocumentDetailsResponseDto);
		commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
				userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
				"Successfully Uploaded Document", 1, "UPLOADDOCUMENT"));
		return uploadDocumentDetailsResponseDto;

	}

	@PostMapping("/downloadDocument")
	@ApiOperation(value = "Download Document", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Downloaded"),
			@ApiResponse(code = 401, message = "You are not authorized to Download"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 204, message = "No Content for the document") })
	public ResponseEntity<?> downloadFromDB(
			@RequestBody UploadDocumentDetailsRequestDto uploadDocumentDetailsRequestDto) throws Exception {
		generateUserDetailsFromJWT("DOWNLOADDOCUMENT");
		Object obj = DownloadDocumentUtil.downloadDocument(uploadDocumentDetailsRequestDto, documentManagementService);
		if (obj instanceof EmployeeWorkPermitDocumentDetails) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=" + ((EmployeeWorkPermitDocumentDetails) obj).getDocumentName())
					.body(((EmployeeWorkPermitDocumentDetails) obj).getDocumentData());
		} else if (obj instanceof EmployeePassportDocumentDetails) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=" + ((EmployeePassportDocumentDetails) obj).getDocumentName())
					.body(((EmployeePassportDocumentDetails) obj).getDocumentData());
		} else if (obj instanceof EmployeePaySlipDocumentDetails) {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=" + ((EmployeePaySlipDocumentDetails) obj).getDocumentName())
					.body(((EmployeePaySlipDocumentDetails) obj).getDocumentData());
		} else {
			return ResponseEntity.noContent().build();
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/deleteDocument")
	@ApiOperation(value = "Delete Document", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to Delete Document"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 204, message = "No Content for the document") })
	public DeleteDocumentResponseDto deleteDocument(@RequestBody DeleteDocumentRequestDto deleteDocumentRequestDto)
			throws Exception {
		DeleteDocumentResponseDto deleteDocumentResponseDto = new DeleteDocumentResponseDto();
		UserDetailsImpl userDetailsImpl = generateUserDetailsFromJWT("DELETEDOCUMENT");
		EmployeeDetails employeeDetails = employeeProductService
				.findByEmployeeId(deleteDocumentRequestDto.getEmployeeId());
		if (!userDetailsImpl.getUsers().getRole().equalsIgnoreCase("Admin")) {
			DeleteDocumentUtil.validateRequestForOthers(userDetailsImpl.getUsers().getUserName(), employeeDetails,
					deleteDocumentRequestDto.getDocumentNumber(), deleteDocumentRequestDto.getDocumentType());
		} else if (userDetailsImpl.getUsers().getRole().equalsIgnoreCase("Admin")) {
			if (!userDetailsImpl.getUsers().getCompanyDetails().getId()
					.equalsIgnoreCase(employeeDetails.getCompanyDetails().getId())) {
				throw new Exception("You are not authorised to delete the document of the employee");
			}
		}
		DeleteDocumentUtil.deleteDocument(deleteDocumentRequestDto, documentManagementService);
		DeleteDocumentUtil.mapResponse(deleteDocumentResponseDto);

		return deleteDocumentResponseDto;

	}

	@DeleteMapping("/deleteCompany")
	@ApiOperation(value = "Delete Company", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to Delete Company"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 204, message = "No Content for the document") })
	public DeleteCompanyResponseDto deleteCompany() throws Exception {
		DeleteCompanyResponseDto deleteCompanyResponseDto = new DeleteCompanyResponseDto();
		UserDetailsImpl userDetails = generateUserDetailsFromJWT("DELETECOMPANY");

		if (userDetails.getUsers().getRole().equalsIgnoreCase("Admin")) {

			deleteCompanyService.deleteCompany(userDetails.getUsers().getCompanyDetails().getId());
			List<EmployeeDetails> employeeDetailsList = employeeProductService
					.findbyCompanyDetails(userDetails.getUsers().getCompanyDetails().getId());
			for (EmployeeDetails employeeDetails : employeeDetailsList) {
				employeeProductService.deleteEmployee(employeeDetails.getEmailId(),
						employeeDetails.getCompanyDetails().getId());
			}
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"Successfully Deleted Company", 1, "DELETECOMPANY"));

		} else {
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"You are not authorised to delete the company", 0, "DELETECOMPANY"));
			throw new Exception("You are not authorised to delete the company");
		}
		DeleteCompanyUtil.deleteCompanyResponse(deleteCompanyResponseDto);
		return deleteCompanyResponseDto;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/vmCompany/{id}")
	@ApiOperation(value = "View or Modify Company", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully View Company Details"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public CompanyDetailsDto viewCompany(@PathVariable String id) throws Exception {
		UserDetailsImpl userDetails = generateUserDetailsFromJWT("VIEWCOMPANY");
		CompanyDetailsDto companyDetailsDto = new CompanyDetailsDto();
		if (!userDetails.getUsers().getRole().equalsIgnoreCase("Admin")) {
			throw new Exception("You are not authorised to use this service");
		}
		CompanyDetails companyDetails = employeeProductService.findCompanyDetails(id);
		CompanySignUpDetailsUtil.viewCompanyDetailsMapping(companyDetails, companyDetailsDto);
		commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
				userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
				"Successfully Retrieved company information", 1, "VIEWCOMPANY"));
		return companyDetailsDto;

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/vmCompany")
	@ApiOperation(value = "View or Modify Company", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Modified Company Details"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public CompanyDetailsDto UpdateCompany(@RequestBody CompanyDetailsDto companyDetailsDto) throws Exception {
		UserDetailsImpl userDetails = generateUserDetailsFromJWT("UPDATECOMPANY");
		CompanyDetails companyDetails = new CompanyDetails();
		CompanyDetailsDto compRes = new CompanyDetailsDto();
		if (!userDetails.getUsers().getRole().equalsIgnoreCase("Admin")) {
			commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
					userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
					"You are not authorised to use this service", 0, "UPDATECOMPANY"));
			throw new Exception("You are not authorised to use this service");
		}
		CompanySignUpDetailsUtil.modifyCompanyDetailsMapping(companyDetails, companyDetailsDto);
		employeeProductService.updateCompany(companyDetails);
		compRes = companyDetailsDto;
		commonService.setAudit(new AuditTrailFE(userDetails.getUsers().getFirstName(),
				userDetails.getUsers().getCompanyDetails().getId(), userDetails.getUsers().getRole(),
				"Successfully Updated company information", 1, "UPDATECOMPANY"));
		return compRes;

	}

	/*
	 * private Optional<Users> loginValidation(String userName, String password)
	 * throws Exception {
	 * 
	 * Optional<Users> optionalUsers = loginDetailsService.loginUser(userName);
	 * 
	 * LoginUserUtil.validateLoginDetails(optionalUsers, password);
	 * 
	 * return optionalUsers; }
	 */

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
