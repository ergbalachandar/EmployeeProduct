package com.employee.product.utils;

import java.util.Optional;

import com.employee.product.companydetails.response.dto.LoginDetailsResponseDto;
import com.employee.product.entity.companydetails.Users;

public class LoginUserUtil {

	public static void validateLoginDetails(Optional<Users> optionalUsers,
			String password) throws Exception {
		if (!password.equals(optionalUsers.get().getPassword())) {
			throw new Exception("You are not authorised to login. Invalid credentials");
		}
		else if(optionalUsers.get().getActive()==0){
			
			throw new Exception("Your profile has been deleted.");
		}
	}

	public static void mapLoginDetailsResponseDto(Users users, LoginDetailsResponseDto loginDetailsResponseDto) {

		loginDetailsResponseDto.setRole(users.getRole());
		loginDetailsResponseDto.setUserName(users.getUserName());
		loginDetailsResponseDto.setCompanyId(String.valueOf(users.getCompanyDetails().getId()));
		loginDetailsResponseDto.setCompanyName(users.getCompanyDetails().getCompanyName());

		/*Set<EmployeeDetails> employeeDetailsSet = users.getEmployeeDetails();
		EmployeeDetailsDto employeeDetailsDto = new EmployeeDetailsDto();
		for (Iterator<EmployeeDetails> it = employeeDetailsSet.iterator(); it.hasNext();) {
			EmployeeDetails employeeDetails = it.next();

			employeeDetailsDto.setAddressLine1(employeeDetails.getAddressLine1());
			employeeDetailsDto.setAddressLine2(employeeDetails.getAddressLine2());
			employeeDetailsDto.setCity(employeeDetails.getCity());
			employeeDetailsDto.setContactNumber(employeeDetails.getContactNumber());
			employeeDetailsDto.setCountry(employeeDetails.getCountry());
			employeeDetailsDto.setDateOfBirth(employeeDetails.getDateOfBirth());
			employeeDetailsDto.setEmailId(employeeDetails.getEmailId());
			employeeDetailsDto.setFirstName(employeeDetails.getFirstName());
			employeeDetailsDto.setLastName(employeeDetails.getLastName());
			employeeDetailsDto.setSex(employeeDetails.getSex());
			employeeDetailsDto.setState(employeeDetails.getState());

		}
		loginDetailsResponseDto.setEmployeeDetailsDto(employeeDetailsDto); */

	}
}
