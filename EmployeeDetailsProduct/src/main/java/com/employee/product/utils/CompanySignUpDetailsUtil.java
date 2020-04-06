package com.employee.product.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.employee.product.companydetails.request.dto.CompanyDetailsRequestDto;
import com.employee.product.companydetails.response.dto.CompanyDetailsResponseDto;
import com.employee.product.employeedetails.dto.EmployeeDetailsDto;
import com.employee.product.entity.companydetails.CompanyDetails;
import com.employee.product.entity.companydetails.Users;
import com.employee.product.entity.employeedetails.EmployeeDetails;

public class CompanySignUpDetailsUtil {

	public static void companySignUpDetailsMapping(CompanyDetailsRequestDto companyDetailsDto, Users users) {

		CompanyDetails companyDetails = new CompanyDetails();

		users.setActive(companyDetailsDto.getActive());
		users.setUserName(companyDetailsDto.getEmailId());
		if (null != companyDetailsDto.getEmployeeDetails()) {
			users.setFirstName(companyDetailsDto.getEmployeeDetails().getFirstName());
			users.setLastName(companyDetailsDto.getEmployeeDetails().getLastName());
			users.setCountry(companyDetailsDto.getEmployeeDetails().getCountry());
		}
		users.setPassword(companyDetailsDto.getPassword());
		users.setRole("Admin");
		users.setActive(1);
		users.setCreatedAt(new Date());
		companyDetailsMapping(companyDetailsDto, companyDetails);

		 users.setCompanyDetails(companyDetails);

		employeeDetailsMapping(users, companyDetailsDto, companyDetails);

	}

	private static void companyDetailsMapping(CompanyDetailsRequestDto companyDetailsDto,
			CompanyDetails companyDetails) {

		companyDetails.setCompanyName(companyDetailsDto.getCompanyName());
		companyDetails.setEmailId(companyDetailsDto.getEmailId());
		companyDetails.setAddressLineOne(companyDetailsDto.getAddressLineOne());
		companyDetails.setAddressLineTwo(companyDetailsDto.getAddressLineTwo());
		companyDetails.setCity(companyDetailsDto.getCity());
		companyDetails.setState(companyDetailsDto.getState());
		companyDetails.setCountry(companyDetailsDto.getCountry());
		companyDetails.setContactNumber(companyDetailsDto.getContactNumber());
		companyDetails.setSizeOfTheCompany(companyDetailsDto.getSizeOfTheCompany());
		companyDetails.setActive(companyDetailsDto.getActive());

	}

	private static void employeeDetailsMapping(Users users, CompanyDetailsRequestDto companyDetailsDto,
			CompanyDetails companyDetails) {

		Set<EmployeeDetails> employeeDetailsSet = new HashSet<EmployeeDetails>();
		EmployeeDetails employeeDetails = new EmployeeDetails();

		EmployeeDetailsDto employeeDetailsDto = companyDetailsDto.getEmployeeDetails();

		employeeDetails.setFirstName(employeeDetailsDto.getFirstName());
		employeeDetails.setLastName(employeeDetailsDto.getLastName());
		employeeDetails.setEmailId(companyDetailsDto.getEmailId());
		employeeDetails.setState(companyDetailsDto.getState());
		employeeDetails.setCountry(companyDetailsDto.getCountry());
		employeeDetails.setContactNumber(companyDetailsDto.getContactNumber());

		employeeDetails.setCompanyDetails(companyDetails);
		employeeDetailsSet.add(employeeDetails);

		users.setEmployeeDetails(employeeDetailsSet);

	}

	/*
	 * public static void sendEmail(JavaMailSender javaMailSender, CompanyDetails
	 * companyDetails) {
	 * 
	 * SimpleMailMessage msg = new SimpleMailMessage();
	 * msg.setTo(companyDetails.getEmailId());
	 * 
	 * msg.setSubject("SignUp Is Successfull");
	 * 
	 * Set<Users> usersSet = companyDetails.getUsers();
	 * 
	 * StringBuilder result = new StringBuilder();
	 * 
	 * for (Iterator<Users> it = usersSet.iterator(); it.hasNext();) { Users users =
	 * it.next(); result.append("Dear " + users.getFirstName() + " " +
	 * users.getLastName()); result.append(System.lineSeparator());
	 * result.append("UserName : " + users.getUserName());
	 * result.append(System.lineSeparator()); result.append("Password : " +
	 * users.getPassword()); result.append(System.lineSeparator()); }
	 * 
	 * result.append("Company Name : " + companyDetails.getCompanyName());
	 * result.append(System.lineSeparator()); result.append("Company Mail Id : " +
	 * companyDetails.getEmailId()); msg.setText(result.toString());
	 * javaMailSender.send(msg);
	 * 
	 * }
	 */

     public static void sendMessage(MailSender mailSender,String emailId, String companyName, Users users) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		StringBuilder result = new StringBuilder();

		result.append("Dear " + users.getFirstName() + " " + users.getLastName());
		result.append(System.lineSeparator());
		result.append("UserName : " + users.getUserName());
		result.append(System.lineSeparator());
		result.append("Password : " + users.getPassword());
		result.append(System.lineSeparator());

		result.append("Company Name : " + companyName);
		result.append(System.lineSeparator());
		result.append("Company Mail Id : " + emailId);
		simpleMailMessage.setText(result.toString());
		simpleMailMessage.setFrom("mproduct113@gmail.com");
		simpleMailMessage.setTo(emailId);
		simpleMailMessage.setSubject("SignUp Is Successfull");

		mailSender.send(simpleMailMessage);

	} 
	
	
	
	public static CompanyDetailsResponseDto companyDetailsSignUpResponseMapping(
			CompanyDetailsResponseDto companyDetailsResponseDto) {

		companyDetailsResponseDto.setMessage("SignUp Successfull");
		return companyDetailsResponseDto;
	}
}
