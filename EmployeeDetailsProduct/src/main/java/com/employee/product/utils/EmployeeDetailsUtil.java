package com.employee.product.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.employee.product.employeedetails.response.dto.EmployeeDataResponseDto;
import com.employee.product.employeedetails.response.dto.EmployeeDetailsResponseDto;
import com.employee.product.employeedetails.response.dto.EmployeeFamilyDetailsResponseDto;
import com.employee.product.employeedetails.response.dto.EmployeePassportDetailsResponseDto;
import com.employee.product.employeedetails.response.dto.EmployeePaySlipDocumentDetailsResponseDto;
import com.employee.product.employeedetails.response.dto.EmployeeWorkPermitDetailsResponseDto;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.employeedetails.EmployeeFamilyDetails;
import com.employee.product.entity.employeedetails.EmployeePassportDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDetails;
import com.employee.product.entity.employeedetails.EmployeeWorkPermitDetails;

public class EmployeeDetailsUtil {

	/*
	 * Method to map the response
	 */

	public static void mappingEmployeeDataResponse(List<EmployeeDetails> employeeDetailsList,
			EmployeeDataResponseDto employeeDataResponseDto) {

		List<EmployeeDetailsResponseDto> employeeDetailsResponseDtoList = new ArrayList<EmployeeDetailsResponseDto>();

		for (EmployeeDetails employeeDetails : employeeDetailsList) {
			if (employeeDetails.getActive() != 0) {
				EmployeeDetailsResponseDto employeeDetailsResponseDto = new EmployeeDetailsResponseDto();
				mapEmployeeDetails(employeeDetailsResponseDto, employeeDetails, true);
				employeeDetailsResponseDtoList.add(employeeDetailsResponseDto);
			}
		}

		employeeDataResponseDto.setEmployeeDetailsList(employeeDetailsResponseDtoList);

	}

	public static void mapEmployeeDetails(EmployeeDetailsResponseDto employeeDetailsResponseDto,
			EmployeeDetails employeeDetails, boolean retrieveEmployeeService) {

		employeeDetailsResponseDto.setAddressLine1(employeeDetails.getAddressLine1());
		employeeDetailsResponseDto.setAddressLine2(employeeDetails.getAddressLine2());
		employeeDetailsResponseDto.setCity(employeeDetails.getCity());
		employeeDetailsResponseDto.setContactNumber(employeeDetails.getContactNumber());
		employeeDetailsResponseDto.setCountry(employeeDetails.getCountry());
		employeeDetailsResponseDto.setDateOfBirth(employeeDetails.getDateOfBirth());
		employeeDetailsResponseDto.setEmailId(employeeDetails.getEmailId());
		employeeDetailsResponseDto.setFirstName(employeeDetails.getFirstName());
		employeeDetailsResponseDto.setLastName(employeeDetails.getLastName());
		employeeDetailsResponseDto.setSex(employeeDetails.getSex());
		employeeDetailsResponseDto.setState(employeeDetails.getState());
		employeeDetailsResponseDto.setId(employeeDetails.getId());
		employeeDetailsResponseDto.setPostalCode(employeeDetails.getPostalCode());
		employeeDetailsResponseDto.setJobRole(employeeDetails.getJobRole());
		employeeDetailsResponseDto.setDepartment(employeeDetails.getDepartment());
		employeeDetailsResponseDto.setWorkLocation(employeeDetails.getWorkLocation());
		employeeDetailsResponseDto.setReportingPerson(employeeDetails.getReportingPerson());
		if (!retrieveEmployeeService) {

			// Mapping PassportDetails
			employeeDetailsResponseDto.setPassportDetails(
					mapPassportDetails(employeeDetails.getEmployeePassportDetails()));

			// Mapping workPermit Details
			employeeDetailsResponseDto.setWorkPermitDetails(
					mapWorkPermitDetails(employeeDetails.getEmployeeWorkPermitDetails()));

			// Mapping Family Details
			employeeDetailsResponseDto
					.setFamilyDetails(mapFamilyDetails(employeeDetails.getEmployeeFamilyDetails()));

			// Mapping Payslip Details
			//Commenting the call to map Payslip details as it is not required in this module
			/*employeeDetailsResponseDto.setPayslipDetails(
					mapPaySlipDetails(employeeDetails.getEmployeePaySlipDetails())); */
		}
	}

	/*
	 * Method to Map passport details of an employee
	 */

	private static List<EmployeePassportDetailsResponseDto> mapPassportDetails(
			Set<EmployeePassportDetails> employeePassportDetailsList) {
		List<EmployeePassportDetailsResponseDto> employeePassportDetailsResponseDtoList = new ArrayList<EmployeePassportDetailsResponseDto>();
		for (EmployeePassportDetails employeePassportDetails : employeePassportDetailsList) {

			EmployeePassportDetailsResponseDto employeePassportDetailsResponseDto = new EmployeePassportDetailsResponseDto();

			employeePassportDetailsResponseDto.setEndDate(employeePassportDetails.getEndDate());
			employeePassportDetailsResponseDto.setIssuePlace(employeePassportDetails.getIssuePlace());
			employeePassportDetailsResponseDto.setPassportNumber(employeePassportDetails.getPassportNumber());
			employeePassportDetailsResponseDto.setStartDate(employeePassportDetails.getStartDate());
			employeePassportDetailsResponseDto.setValidity(employeePassportDetails.getValidity());
			employeePassportDetailsResponseDto.setBirthPlace(employeePassportDetails.getBirthPlace());
			employeePassportDetailsResponseDto.setDocumentName(employeePassportDetails.getDocumentName());
			employeePassportDetailsResponseDto.setDocumentType(employeePassportDetails.getDocumentType());
			employeePassportDetailsResponseDtoList.add(employeePassportDetailsResponseDto);
		}

		return employeePassportDetailsResponseDtoList;

	}

	/*
	 * Method to Map WorkPermit details of an employee
	 */

	private static List<EmployeeWorkPermitDetailsResponseDto> mapWorkPermitDetails(
			Set<EmployeeWorkPermitDetails> employeeWorkPermitDetailsList) {
		List<EmployeeWorkPermitDetailsResponseDto> employeeWorkPermitDetailsResponseDtoList = new ArrayList<EmployeeWorkPermitDetailsResponseDto>();

		for (EmployeeWorkPermitDetails employeeWorkPermitDetails : employeeWorkPermitDetailsList) {

			EmployeeWorkPermitDetailsResponseDto employeeWorkPermitDetailsResponseDto = new EmployeeWorkPermitDetailsResponseDto();

			employeeWorkPermitDetailsResponseDto.setWorkPermitNumber(employeeWorkPermitDetails.getWorkPermitNumber());
			employeeWorkPermitDetailsResponseDto.setEndDate(employeeWorkPermitDetails.getEndDate());
			employeeWorkPermitDetailsResponseDto.setStartDate(employeeWorkPermitDetails.getStartDate());
			employeeWorkPermitDetailsResponseDto.setValidity(employeeWorkPermitDetails.getValidity());
			employeeWorkPermitDetailsResponseDto.setDocumentName(employeeWorkPermitDetails.getDocumentName());
			employeeWorkPermitDetailsResponseDto.setDocumentType(employeeWorkPermitDetails.getDocumentType());

			employeeWorkPermitDetailsResponseDtoList.add(employeeWorkPermitDetailsResponseDto);
		}

		return employeeWorkPermitDetailsResponseDtoList;
	}

	/*
	 * Method to Map Family details of an employee
	 */

	private static List<EmployeeFamilyDetailsResponseDto> mapFamilyDetails(
			Set<EmployeeFamilyDetails> employeeFamilyDetailsResponseList) {

		List<EmployeeFamilyDetailsResponseDto> employeeFamilyDetailsResponseDtoList = new ArrayList<EmployeeFamilyDetailsResponseDto>();

		for (EmployeeFamilyDetails employeeFamilyDetails : employeeFamilyDetailsResponseList) {

			EmployeeFamilyDetailsResponseDto employeeFamilyDetailsResponseDto = new EmployeeFamilyDetailsResponseDto();

			employeeFamilyDetailsResponseDto.setContactNumber(employeeFamilyDetails.getContactNumber());
			employeeFamilyDetailsResponseDto.setFirstName(employeeFamilyDetails.getFirstName());
			employeeFamilyDetailsResponseDto.setLastName(employeeFamilyDetails.getLastName());
			employeeFamilyDetailsResponseDto.setRelation(employeeFamilyDetails.getRelation());
			employeeFamilyDetailsResponseDto.setId(employeeFamilyDetails.getId());

			employeeFamilyDetailsResponseDtoList.add(employeeFamilyDetailsResponseDto);

		}

		return employeeFamilyDetailsResponseDtoList;
	}

	/*
	 * Method to Map Payslip details of an employee
	 */

	private static List<EmployeePaySlipDocumentDetailsResponseDto> mapPaySlipDetails(
			Set<EmployeePaySlipDetails> employeePaySlipDetailsList) {

		List<EmployeePaySlipDocumentDetailsResponseDto> employeePaySlipDocumentDetailsResponseDtoList = new ArrayList<EmployeePaySlipDocumentDetailsResponseDto>();
		if (null != employeePaySlipDetailsList) {

			for (EmployeePaySlipDetails employeePaySlipDetails : employeePaySlipDetailsList) {

				EmployeePaySlipDocumentDetailsResponseDto employeePaySlipDocumentDetailsResponseDto = new EmployeePaySlipDocumentDetailsResponseDto();

				employeePaySlipDocumentDetailsResponseDto.setDocumentName(employeePaySlipDetails.getDocumentName());
				employeePaySlipDocumentDetailsResponseDto.setDocumentNumber(employeePaySlipDetails.getPaySlipNumber());
				employeePaySlipDocumentDetailsResponseDto.setDocumentType(employeePaySlipDetails.getDocumentType());
				employeePaySlipDocumentDetailsResponseDtoList.add(employeePaySlipDocumentDetailsResponseDto);

			}
		}
		return employeePaySlipDocumentDetailsResponseDtoList;
	}

}