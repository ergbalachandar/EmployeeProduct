package com.employee.product.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.product.companydetails.request.dto.CompanyDetailsRequestDto;
import com.employee.product.companydetails.request.dto.LoginDetailsRequestDto;
import com.employee.product.companydetails.response.dto.CompanyDetailsResponseDto;
import com.employee.product.companydetails.response.dto.LoginDetailsResponseDto;
import com.employee.product.dao.interfaces.EmployeeDetailsInterface;
import com.employee.product.dao.services.EmployeeProductService;
import com.employee.product.dao.services.LoginDetailsService;
import com.employee.product.employeedetails.request.dto.AddEmployeeRequestDto;
import com.employee.product.employeedetails.request.dto.EmployeeDataRequestDto;
import com.employee.product.employeedetails.response.dto.EmployeeDataResponseDto;
import com.employee.product.entity.companydetails.CompanyDetails;
import com.employee.product.entity.companydetails.Users;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.utils.AddEmployeeDetailsUtil;
import com.employee.product.utils.CompanySignUpDetailsUtil;
import com.employee.product.utils.EmployeeDetailsUtil;
import com.employee.product.utils.GeneratePdfReportUtil;
import com.employee.product.utils.LoginUserUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController

@RequestMapping("/EProduct")
public class EmployeeProductController {

	@Autowired
	private EmployeeProductService employeeProductService;

	@Autowired
	private LoginDetailsService loginDetailsService;

	@Autowired
	private EmployeeDetailsInterface employeeDetailsInterface;

	
	  @Autowired 
	  private MailSender mailSender;
	 

	/**
	 * Method to SignUp Company
	 * 
	 * @param CompanyDetailsRequestDto
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/companysignup")
	@ApiOperation(value = "Sign Up Company")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Signed Up"),
			@ApiResponse(code = 401, message = "You are not authorized to sign Up"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public CompanyDetailsResponseDto signUpCompanyDetails(@RequestBody CompanyDetailsRequestDto companyDetailsDto,
			HttpSession httpSession) {
		System.out.println("Session Value------>" + httpSession.toString());
		Users users = new Users();
		CompanyDetailsResponseDto companyDetailsResponseDto = new CompanyDetailsResponseDto();
		CompanySignUpDetailsUtil.companySignUpDetailsMapping(companyDetailsDto, users);
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
	@RequestMapping(method = RequestMethod.POST, value = "/loginUser")
	@ApiOperation(value = "LoginUser")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Logged In"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public LoginDetailsResponseDto loginUser(@RequestBody LoginDetailsRequestDto loginDetailsRequestDto,
			HttpSession httpSession) throws Exception {

		LoginDetailsResponseDto loginDetailsResponseDto = new LoginDetailsResponseDto();
		Users users = new Users();
		Optional<Users> optionalUsers = loginValidation(loginDetailsRequestDto.getUserName(),
				loginDetailsRequestDto.getPassword());
		if (loginDetailsRequestDto.getReset() == 0) {
			users = optionalUsers.get();
			LoginUserUtil.mapLoginDetailsResponseDto(users, loginDetailsResponseDto);
		} else {
			Users usersWithNewPassword = loginDetailsService.updatePassword(loginDetailsRequestDto.getNewPassword(),
					loginDetailsRequestDto.getUserName());
			LoginUserUtil.mapLoginDetailsResponseDto(usersWithNewPassword, loginDetailsResponseDto);
		}
		httpSession.setAttribute("userName", users.getUserName());

		return loginDetailsResponseDto;

	}

	/**
	 * Method to retrieve Employee List
	 * 
	 * @param EmployeeDetailsRequestDto
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/retrieveEmployeeList")
	@ApiOperation(value = "RetrieveEmployee")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved EmployeeList"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody

	public EmployeeDataResponseDto retrieveEmployeeList(@RequestBody EmployeeDataRequestDto employeeDataRequestDto,
			HttpSession httpSession) throws Exception {
		EmployeeDataResponseDto employeeDataResponseDto = new EmployeeDataResponseDto();

		Optional<Users> users = loginValidation(employeeDataRequestDto.getUserName(),
				employeeDataRequestDto.getPassword());

		if (!users.get().getRole().equalsIgnoreCase("Admin")) {
			throw new Exception("You are not authorised to retrieve the list of Employees");
		}
		List<EmployeeDetails> employeeDetailsList = employeeProductService
				.findbyCompanyDetails(employeeDataRequestDto.getCompanyId());

		EmployeeDetailsUtil.mappingEmployeeDataResponse(employeeDetailsList, employeeDataResponseDto);

		return employeeDataResponseDto;

	}

	/**
	 * a Method to Add or Modify Employee
	 * 
	 * @param EmployeeDetailsRequestDto
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/addModifyEmployee")
	@ApiOperation(value = "Add or Update Employee")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added or updated EmployeeDetails"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody

	public EmployeeDataResponseDto addOrUpdateEmployeeList(@RequestBody AddEmployeeRequestDto addEmployeeRequestDto) {

		Users users = new Users();
		EmployeeDetails employeeDetails = new EmployeeDetails();
		CompanyDetails companyDetails = employeeProductService.findCompanyDetails(addEmployeeRequestDto.getCompanyId());
		System.out.println(companyDetails);
		AddEmployeeDetailsUtil.mapAddEmployeeRequest(addEmployeeRequestDto, users, employeeDetails, companyDetails);

		employeeDetails = employeeProductService.addOrUpdateEmployeeDetails(employeeDetails, users, companyDetails);
		List<EmployeeDetails> employeeDetailsList = new ArrayList<EmployeeDetails>();

		employeeDetailsList.add(employeeDetails);

		EmployeeDataResponseDto employeeDataResponseDto = new EmployeeDataResponseDto();
		EmployeeDetailsUtil.mappingEmployeeDataResponse(employeeDetailsList, employeeDataResponseDto);

		return employeeDataResponseDto;

	}

	@RequestMapping(value = "/pdfreport/{companyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	@ApiOperation(value = "Generate EmployeeReport PDF")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully GeneratedPDF"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseEntity<InputStreamResource> employeePdfReport(@PathVariable String companyId) throws Exception {

		/*
		 * Optional<Users> users = loginValidation(employeeDataRequestDto.getUserName(),
		 * employeeDataRequestDto.getPassword());
		 * 
		 * if (!users.get().getRole().equalsIgnoreCase("Admin")) { throw new
		 * Exception("You are not authorised to retrieve the list of Employees"); }
		 */
		List<EmployeeDetails> employeeDetailsList = employeeProductService.findbyCompanyDetails(companyId);

		ByteArrayInputStream bis = GeneratePdfReportUtil.employeeReport(employeeDetailsList);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=EmployeeReport.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));

	}

	private Optional<Users> loginValidation(String userName, String password) throws Exception {

		Optional<Users> optionalUsers = loginDetailsService.loginUser(userName);

		LoginUserUtil.validateLoginDetails(optionalUsers, password);

		return optionalUsers;
	}
}
