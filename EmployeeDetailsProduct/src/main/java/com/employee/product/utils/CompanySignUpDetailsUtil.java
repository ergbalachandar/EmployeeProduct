package com.employee.product.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
/*
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;*/

import com.employee.product.companydetails.dto.CompanyDetailsDto;
import com.employee.product.companydetails.request.dto.CompanyDetailsRequestDto;
import com.employee.product.companydetails.response.dto.CompanyDetailsResponseDto;
import com.employee.product.employeedetails.dto.EmployeeDetailsDto;
import com.employee.product.entity.companydetails.CompanyDetails;
import com.employee.product.entity.companydetails.Users;
import com.employee.product.entity.employeedetails.EmployeeDetails;

public class CompanySignUpDetailsUtil {

	public static void companySignUpDetailsMapping(CompanyDetailsRequestDto companyDetailsDto, Users users) {

		CompanyDetails companyDetails = new CompanyDetails();
		users.setActive(1);
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
		companyDetails.setActive(1);
		companyDetails.setVatNumber(companyDetailsDto.getVatNumber());
		companyDetails.setPostalCode(companyDetailsDto.getPostalCode());
		companyDetails.setCompanyType(companyDetailsDto.getCompanyType());
		int num = AddEmployeeDetailsUtil.generateRandomNumber();
		String cName = removeSpaces(companyDetailsDto.getCompanyName()).substring(0,4);
		companyDetails.setId(cName.toUpperCase() + String.valueOf(num));
	}


	// Remove all space characters
	private static String removeSpaces(String input) {
		return input.replaceAll(" ", "");
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
		employeeDetails.setCity(companyDetailsDto.getCity());

		employeeDetails.setCompanyDetails(companyDetails);
		employeeDetails.setActive(1);
		employeeDetails.setId(AddEmployeeDetailsUtil.generateEmployeeId(companyDetailsDto.getCompanyName(),
				employeeDetailsDto.getFirstName(), employeeDetailsDto.getLastName()));

		employeeDetailsSet.add(employeeDetails);

		users.setEmployeeDetails(employeeDetailsSet);

	}
    
	public static void viewCompanyDetailsMapping(CompanyDetails companyDetails,
			CompanyDetailsDto companyDetailsDto) {
		companyDetailsDto.setCompanyName(companyDetails.getCompanyName());
		companyDetailsDto.setEmailId(companyDetails.getEmailId());
		companyDetailsDto.setAddressLineOne(companyDetails.getAddressLineOne());
		companyDetailsDto.setAddressLineTwo(companyDetails.getAddressLineTwo());
		companyDetailsDto.setCity(companyDetails.getCity());
		companyDetailsDto.setState(companyDetails.getState());
		companyDetailsDto.setCountry(companyDetails.getCountry());
		companyDetailsDto.setContactNumber(companyDetails.getContactNumber());
		companyDetailsDto.setSizeOfTheCompany(companyDetails.getSizeOfTheCompany());
		companyDetailsDto.setVatNumber(companyDetails.getVatNumber());
		companyDetailsDto.setCompanyId(companyDetails.getId());
		companyDetailsDto.setPostalCode(companyDetails.getPostalCode());
		companyDetailsDto.setCompanyType(companyDetails.getCompanyType());
		companyDetailsDto.setCompanyFlag(companyDetails.getCompanyFlag());
	}
	
	public static void modifyCompanyDetailsMapping(CompanyDetails companyDetails,
			CompanyDetailsDto companyDetailsDto) {
		companyDetails.setCompanyName(companyDetailsDto.getCompanyName());
		companyDetails.setActive(1);
		companyDetails.setEmailId(companyDetailsDto.getEmailId());
		companyDetails.setAddressLineOne(companyDetailsDto.getAddressLineOne());
		companyDetails.setAddressLineTwo(companyDetailsDto.getAddressLineTwo());
		companyDetails.setCity(companyDetailsDto.getCity());
		companyDetails.setState(companyDetailsDto.getState());
		companyDetails.setCountry(companyDetailsDto.getCountry());
		companyDetails.setContactNumber(companyDetailsDto.getContactNumber());
		companyDetails.setSizeOfTheCompany(companyDetailsDto.getSizeOfTheCompany());
		companyDetails.setVatNumber(companyDetailsDto.getVatNumber());
		companyDetails.setId(companyDetailsDto.getCompanyId());
		companyDetails.setPostalCode(companyDetailsDto.getPostalCode());
		companyDetails.setCompanyType(companyDetailsDto.getCompanyType());
		companyDetails.setCompanyFlag(1);
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
	/*
	 * public static void sendMessageAfterLogin(MailSender mailSender, String
	 * emailId) { SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
	 * StringBuilder result = new StringBuilder(); result.
	 * append("Welcome to MProduct !! One stop services for all your HR needs");
	 * result.append(System.lineSeparator()); result.append(System.lineSeparator());
	 * result.append(System.lineSeparator()); result.append(System.lineSeparator());
	 * result.append(System.lineSeparator()); result.append(System.lineSeparator());
	 * result.append(System.lineSeparator()); result.append("Logged In Time : " +
	 * new Date()); result.append(System.lineSeparator());
	 * result.append(System.lineSeparator()); result.append(System.lineSeparator());
	 * result.append(System.lineSeparator()); result.append(System.lineSeparator());
	 * result.
	 * append("Welcome to MProduct !! One stop services for all your HR needs");
	 * simpleMailMessage.setText(result.toString());
	 * simpleMailMessage.setFrom("mproduct113@gmail.com");
	 * simpleMailMessage.setTo(emailId);
	 * simpleMailMessage.setSubject("Login Is Successfull");
	 * mailSender.send(simpleMailMessage); }
	 */
	
	/*
	 * public static void sendMessage(MailSender mailSender, String emailId, String
	 * companyName, Users users, String pwd) { SimpleMailMessage simpleMailMessage =
	 * new SimpleMailMessage(); StringBuilder result = new StringBuilder(); result.
	 * append("Welcome to MProduct !! One stop services for all your HR needs");
	 * result.append(System.lineSeparator()); result.append("Dear " +
	 * users.getFirstName() + " " + users.getLastName());
	 * result.append(System.lineSeparator()); result.append("UserName : " +
	 * users.getUserName()); result.append(System.lineSeparator());
	 * result.append("Password : " + pwd); result.append(System.lineSeparator());
	 * result.append("Company Name : " + companyName);
	 * result.append(System.lineSeparator()); result.append("Company Mail Id : " +
	 * emailId); result.append(System.lineSeparator());
	 * result.append(System.lineSeparator()); result.append(System.lineSeparator());
	 * result.append(System.lineSeparator()); result.append(System.lineSeparator());
	 * result.
	 * append("Welcome to MProduct !! One stop services for all your HR needs");
	 * simpleMailMessage.setText(result.toString());
	 * simpleMailMessage.setFrom("mproduct113@gmail.com");
	 * simpleMailMessage.setTo(emailId);
	 * simpleMailMessage.setSubject("SignUp Is Successfull");
	 * mailSender.send(simpleMailMessage); }
	 */
	public static CompanyDetailsResponseDto companyDetailsSignUpResponseMapping(
			CompanyDetailsResponseDto companyDetailsResponseDto) {
		companyDetailsResponseDto.setMessage("SignUp Successfull");
		return companyDetailsResponseDto;
	}
}
