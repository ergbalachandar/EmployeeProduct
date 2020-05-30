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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mail.MailSender;

import com.employee.product.companydetails.request.dto.CompanyDetailsRequestDto;
import com.employee.product.companydetails.request.dto.LoginDetailsRequestDto;
import com.employee.product.companydetails.response.dto.CompanyDetailsResponseDto;
import com.employee.product.companydetails.response.dto.LoginDetailsResponseDto;
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
import com.employee.product.security.jwt.JwtUtils;
import com.employee.product.utils.AddEmployeeDetailsUtil;
import com.employee.product.utils.CompanySignUpDetailsUtil;
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
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private MailSender mailSender;

	/**
	 * Method to SignUp Company
	 * 
	 * @param CompanyDetailsRequestDto
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

	CompanySignUpDetailsUtil.sendMessage(mailSender, companyDetailsDto.getEmailId(),
				companyDetailsDto.getCompanyName(), users);

		CompanySignUpDetailsUtil.companyDetailsSignUpResponseMapping(companyDetailsResponseDto);
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

		if (loginDetailsRequestDto.getReset() == 0) {
			LoginUserUtil.mapLoginDetailsResponseDto(userDetails.getUsers(), loginDetailsResponseDto);
			loginDetailsResponseDto.setJwt(jwt);
		} else {
			Users usersWithNewPassword = loginDetailsService.updatePassword(
					encoder.encode(loginDetailsRequestDto.getNewPassword()), loginDetailsRequestDto.getUserName());
			LoginUserUtil.mapLoginDetailsResponseDto(usersWithNewPassword, loginDetailsResponseDto);
			loginDetailsResponseDto.setJwt(null);
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
		UserDetailsImpl userDetailsImpl = generateUserDetailsFromJWT();

		if (!userDetailsImpl.getUsers().getRole().equalsIgnoreCase("Admin")) {
			throw new Exception("You are not authorised to retrieve the list of Employees");
		}
		List<EmployeeDetails> employeeDetailsList = employeeProductService
				.findbyCompanyDetails(userDetailsImpl.getUsers().getCompanyDetails().getId());

		EmployeeDetailsUtil.mappingEmployeeDataResponse(employeeDetailsList, employeeDataResponseDto);

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
		UserDetailsImpl userDetailsImpl = generateUserDetailsFromJWT();
		EmployeeDetails employeeDetails = employeeProductService
				.findByEmployeeId(retrieveEmployeeDataRequestDto.getEmployeeId());
		if (userDetailsImpl.getUsers().getCompanyDetails().getId().equalsIgnoreCase(employeeDetails.getCompanyDetails().getId())) {
			EmployeeDetailsUtil.mapEmployeeDetails(employeeDetailsResponseDto, employeeDetails, false);
		} else {
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
		UserDetailsImpl userDetailsImpl = generateUserDetailsFromJWT();
		CompanyDetails companyDetails = employeeProductService.findCompanyDetails(addEmployeeRequestDto.getCompanyId());
		newEmployee = AddEmployeeDetailsUtil.checkForNewOrUpdateEmployee(newEmployee, addEmployeeRequestDto);
		AddEmployeeDetailsUtil.mapAddEmployeeRequest(addEmployeeRequestDto, users, employeeDetails, companyDetails,
				newEmployee,encoder);
		employeeDetails = employeeProductService.addOrUpdateEmployeeDetails(employeeDetails, users, companyDetails,
				newEmployee, userDetailsImpl.getUsers().getUserName());
		EmployeeDetailsResponseDto employeeDetailsResponseDto = new EmployeeDetailsResponseDto();
		EmployeeDetailsUtil.mapEmployeeDetails(employeeDetailsResponseDto, employeeDetails, false);

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

		UserDetailsImpl userDetailsImpl = generateUserDetailsFromJWT();

		if (!userDetailsImpl.getUsers().getRole().equalsIgnoreCase("Admin")) {
			throw new Exception("You are not authorised to retrieve the list of Employees");
		}

		List<EmployeeDetails> employeeDetailsList = employeeProductService
				.findbyCompanyDetails(userDetailsImpl.getUsers().getCompanyDetails().getId());
		ByteArrayInputStream bis = null;
		HttpHeaders headers = new HttpHeaders();

		if (employeeDataRequestDto.getDocumentType() == 1) {

			bis = GeneratePdfReportUtil.employeeReport(employeeDetailsList,
					userDetailsImpl.getUsers().getCompanyDetails().getCompanyName());

			headers.add("Content-Disposition", "inline; filename=EmployeeReport.pdf");
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
					.body(new InputStreamResource(bis));
		}

		else if (employeeDataRequestDto.getDocumentType() == 2) {
			bis = GenerateExcelReportUtil.employeeReport(employeeDetailsList,
					userDetailsImpl.getUsers().getCompanyDetails().getCompanyName());
			headers.add("Content-Disposition", "inline; filename=EmployeeReport.xlsx");
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
		UserDetailsImpl userDetailsImpl = generateUserDetailsFromJWT();
		if (employeeDataRequestDto.getDocumentType() == 3) {

			if (!userDetailsImpl.getUsers().getRole().equalsIgnoreCase("Admin")) {
				throw new Exception("You are not authorised to retrieve the list of Employees");
			}

			List<EmployeeDetails> employeeDetailsList = employeeProductService
					.findbyCompanyDetails(userDetailsImpl.getUsers().getCompanyDetails().getId());

			GenerateCsvReportUtil.generateEmployeeDetails(response.getWriter(), employeeDetailsList,
					userDetailsImpl.getUsers().getCompanyDetails().getCompanyName());
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
		UserDetailsImpl userDetailsImpl = generateUserDetailsFromJWT();
		DeleteEmployeeResponseDto deleteEmployeeResponseDto = new DeleteEmployeeResponseDto();

		if (!userDetailsImpl.getUsers().getRole().equalsIgnoreCase("Admin")) {
			throw new Exception("You are not authorised to Delete the employee");
		}

		employeeProductService.deleteEmployee(deleteEmployeeRequestDto.getUserName(),
				userDetailsImpl.getUsers().getCompanyDetails().getId());
		DeleteEmployeeResponseUtil.mapResponseDeleteEmployeeResponse(deleteEmployeeResponseDto);

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
		UserDetailsImpl userDetailsImpl = generateUserDetailsFromJWT();
		UploadDocumentDetailsRequestDto uploadDocumentDetailsRequestDto = mapper.readValue(value,
				UploadDocumentDetailsRequestDto.class);
		try {
			UploadDocumentUtil.uploadDocument(userDetailsImpl.getUsers().getUserName(), uploadDocumentDetailsRequestDto,
					bytes, documentManagementService, uploadFile.getOriginalFilename());
		} catch (Exception e) {
			throw new Exception("Could not upload Document");
		}
		UploadDocumentUtil.mapResponseUploadDocumentResponseDto(uploadDocumentDetailsResponseDto);
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
		generateUserDetailsFromJWT();
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

	@PostMapping("/deleteDocument")
	@ApiOperation(value = "Delete Document", authorizations = { @Authorization(value = "jwtToken") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to Delete Document"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 204, message = "No Content for the document") })
	public DeleteDocumentResponseDto deleteDocument(@RequestBody DeleteDocumentRequestDto deleteDocumentRequestDto)
			throws Exception {
		DeleteDocumentResponseDto deleteDocumentResponseDto = new DeleteDocumentResponseDto();
		UserDetailsImpl userDetailsImpl = generateUserDetailsFromJWT();
		EmployeeDetails employeeDetails = employeeProductService
				.findByEmployeeId(deleteDocumentRequestDto.getEmployeeId());
		if (!userDetailsImpl.getUsers().getRole().equalsIgnoreCase("Admin")) {
			DeleteDocumentUtil.validateRequestForOthers(userDetailsImpl.getUsers().getUserName(), employeeDetails,
					deleteDocumentRequestDto.getDocumentNumber(), deleteDocumentRequestDto.getDocumentType());
		} else if (userDetailsImpl.getUsers().getRole().equalsIgnoreCase("Admin")) {
			if (!userDetailsImpl.getUsers().getCompanyDetails().getId().equalsIgnoreCase(employeeDetails.getCompanyDetails().getId())) {
				throw new Exception("You are not authorised to delete the document of the employee");
			}
		}
		DeleteDocumentUtil.deleteDocument(deleteDocumentRequestDto, documentManagementService);
		DeleteDocumentUtil.mapResponse(deleteDocumentResponseDto);

		return deleteDocumentResponseDto;

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

	private static UserDetailsImpl generateUserDetailsFromJWT() throws Exception {

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		if (userDetailsImpl.getUsers().getActive() == 0) {
			throw new Exception("Your profile has been deleted");
		}

		return userDetailsImpl;
	}
}
