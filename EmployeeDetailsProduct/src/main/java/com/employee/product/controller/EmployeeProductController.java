package com.employee.product.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.product.companydetails.request.dto.CompanyDetailsRequestDto;
import com.employee.product.companydetails.request.dto.LoginDetailsRequestDto;
import com.employee.product.companydetails.response.dto.CompanyDetailsResponseDto;
import com.employee.product.companydetails.response.dto.LoginDetailsResponseDto;
import com.employee.product.dao.services.EmployeeProductService;
import com.employee.product.dao.services.LoginDetailsService;
import com.employee.product.entity.companydetails.CompanyDetails;
import com.employee.product.entity.companydetails.Users;
import com.employee.product.utils.CompanySignUpDetailsUtil;
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
	  private JavaMailSender javaMailSender;



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
	public CompanyDetailsResponseDto signUpCompanyDetails(@RequestBody CompanyDetailsRequestDto companyDetailsDto) {
		CompanyDetails companyDetails = new CompanyDetails();
		CompanyDetailsResponseDto companyDetailsResponseDto = new CompanyDetailsResponseDto();
		CompanySignUpDetailsUtil.companySignUpDetailsMapping(companyDetailsDto, companyDetails);
		companyDetails = employeeProductService.signUpCompanyDetails(companyDetails);
		CompanySignUpDetailsUtil.sendEmail(javaMailSender,companyDetails);
		CompanySignUpDetailsUtil.companyDetailsSignUpResponseMapping(companyDetailsResponseDto);
		return companyDetailsResponseDto;
	}
	

	/**
	 * Method to Login User
	 * 
	 * @param CompanyDetailsRequestDto
	 * @throws Exception 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/loginUser")
	@ApiOperation(value = "Sign Up Company")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Logged In"),
			@ApiResponse(code = 401, message = "You are not authorized to Log In"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public LoginDetailsResponseDto loginUser(@RequestBody LoginDetailsRequestDto loginDetailsRequestDto) throws Exception {
	
		LoginDetailsResponseDto loginDetailsResponseDto = new LoginDetailsResponseDto();
		Users users = new Users();
		users.setUserName(loginDetailsRequestDto.getUserName());
		
		Optional<Users> optionalUsers = loginDetailsService.loginUser(users);
		
		LoginUserUtil.validateLoginDetails(optionalUsers, loginDetailsRequestDto);
		
		users = optionalUsers.get();
		LoginUserUtil.mapLoginDetailsResponseDto(users,loginDetailsResponseDto);
		
		return loginDetailsResponseDto;
		
		
		
		
	}
}
