package com.employee.product.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
		CompanySignUpDetailsUtil.sendMessage(mailSender,companyDetailsDto.getEmailId(), companyDetailsDto.getCompanyName(), users);
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
		users = optionalUsers.get();
		LoginUserUtil.mapLoginDetailsResponseDto(users, loginDetailsResponseDto);
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


			loginValidation(employeeDataRequestDto.getUserName(), employeeDataRequestDto.getPassword());

			List<EmployeeDetails> employeeDetailsList = employeeProductService
					.findbyCompanyDetails(employeeDataRequestDto.getCompanyId());

			EmployeeDetailsUtil.mappingEmployeeDataResponse(employeeDetailsList, employeeDataResponseDto);
		
		return employeeDataResponseDto;

	}

	/**
	 * Method to Add or Modify Employee
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
		// users = employeeProductService.signUpCompanyDetails(users);
		List<EmployeeDetails> employeeDetailsList = new ArrayList<EmployeeDetails>();

		employeeDetailsList.add(employeeDetails);

		EmployeeDataResponseDto employeeDataResponseDto = new EmployeeDataResponseDto();
		EmployeeDetailsUtil.mappingEmployeeDataResponse(employeeDetailsList, employeeDataResponseDto);

		return employeeDataResponseDto;

	}

	private Optional<Users> loginValidation(String userName, String password) throws Exception {

		Optional<Users> optionalUsers = loginDetailsService.loginUser(userName);

		LoginUserUtil.validateLoginDetails(optionalUsers, password);

		return optionalUsers;
	}
}
