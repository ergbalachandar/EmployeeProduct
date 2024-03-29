package com.employee.product.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.employee.product.employeedetails.dto.MaritalStat;
import com.employee.product.employeedetails.response.dto.EmployeeDataResponseDto;
import com.employee.product.employeedetails.response.dto.EmployeeDetailsResponseDto;
import com.employee.product.employeedetails.response.dto.EmployeeFamilyDetailsResponseDto;
import com.employee.product.employeedetails.response.dto.EmployeePassportDetailsResponseDto;
import com.employee.product.employeedetails.response.dto.EmployeeWorkPermitDetailsResponseDto;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.employeedetails.EmployeeFamilyDetails;
import com.employee.product.entity.employeedetails.EmployeePassportDetails;
import com.employee.product.entity.employeedetails.EmployeeWorkPermitDetails;

public class EmployeeDetailsUtil {

	/*
	 * Method to map the response
	 */

	public static void mappingEmployeeDataResponse(List<EmployeeDetails> employeeDetailsList,
			EmployeeDataResponseDto employeeDataResponseDto) {

		List<EmployeeDetailsResponseDto> employeeDetailsResponseDtoList = new ArrayList<EmployeeDetailsResponseDto>();

		for (EmployeeDetails employeeDetails : employeeDetailsList) {
			//if (employeeDetails.getActive() != 0) {
				EmployeeDetailsResponseDto employeeDetailsResponseDto = new EmployeeDetailsResponseDto();
				mapEmployeeDetails(employeeDetailsResponseDto, employeeDetails, true);
				employeeDetailsResponseDtoList.add(employeeDetailsResponseDto);
		//	}
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
		if (null != employeeDetails.getDateOfBirth()) {
			employeeDetailsResponseDto.setDateOfBirth(String.valueOf(employeeDetails.getDateOfBirth()));
		}
		if (null != employeeDetails.getResignDate()) {
			employeeDetailsResponseDto.setDateOfResign(String.valueOf(employeeDetails.getResignDate()));
		}
		employeeDetailsResponseDto.setActive(employeeDetails.getActive());
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
		employeeDetailsResponseDto.setIban(employeeDetails.getIban());
		employeeDetailsResponseDto.setBic(employeeDetails.getBic());
		employeeDetailsResponseDto.setReportingPerson(employeeDetails.getReportingPerson());
		if (null != employeeDetails.getDateOfJoin()) {
			employeeDetailsResponseDto.setDateOfJoin(String.valueOf(employeeDetails.getDateOfJoin()));
		}
		if(null != employeeDetails.getMaritalStatus()) {
		employeeDetailsResponseDto.setMaritalStatus(MaritalStat.valueOf(employeeDetails.getMaritalStatus()));
		}
		
		
		if (!retrieveEmployeeService) {

			// Mapping PassportDetails
			employeeDetailsResponseDto
					.setPassportDetails(mapPassportDetails(employeeDetails.getEmployeePassportDetails()));

			// Mapping workPermit Details
			employeeDetailsResponseDto
					.setWorkPermitDetails(mapWorkPermitDetails(employeeDetails.getEmployeeWorkPermitDetails()));

			// Mapping Family Details
			employeeDetailsResponseDto.setFamilyDetails(mapFamilyDetails(employeeDetails.getEmployeeFamilyDetails()));

			// Mapping Payslip Details
			// Commenting the call to map Payslip details as it is not required in this
			// module
			/*
			 * employeeDetailsResponseDto.setPayslipDetails(
			 * mapPaySlipDetails(employeeDetails.getEmployeePaySlipDetails()));
			 */
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
			if(null!=employeePassportDetails.getEndDate()) {
			employeePassportDetailsResponseDto.setEndDate(String.valueOf(employeePassportDetails.getEndDate()));
			}
			employeePassportDetailsResponseDto.setIssuePlace(employeePassportDetails.getIssuePlace());
			employeePassportDetailsResponseDto.setPassportNumber(employeePassportDetails.getPassportNumber());
			if(null!=employeePassportDetails.getStartDate()) {
			employeePassportDetailsResponseDto.setStartDate(String.valueOf(employeePassportDetails.getStartDate()));
			}
			employeePassportDetailsResponseDto.setValidity(employeePassportDetails.getValidity());
			employeePassportDetailsResponseDto.setBirthPlace(employeePassportDetails.getBirthPlace());
			employeePassportDetailsResponseDto.setFileName(employeePassportDetails.getDocumentName());
			employeePassportDetailsResponseDto.setFileType(employeePassportDetails.getDocumentType());
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
			if(null!= employeeWorkPermitDetails.getEndDate()) {
			employeeWorkPermitDetailsResponseDto.setEndDate(String.valueOf(employeeWorkPermitDetails.getEndDate()));
			}
			if(null!= employeeWorkPermitDetails.getStartDate()) {
			employeeWorkPermitDetailsResponseDto.setStartDate(String.valueOf(employeeWorkPermitDetails.getStartDate()));
			}
			employeeWorkPermitDetailsResponseDto.setValidity(employeeWorkPermitDetails.getValidity());
			employeeWorkPermitDetailsResponseDto.setFileName(employeeWorkPermitDetails.getDocumentName());
			employeeWorkPermitDetailsResponseDto.setFileType(employeeWorkPermitDetails.getDocumentType());

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

	/*
	 * private static List<EmployeePaySlipDocumentDetailsResponseDto>
	 * mapPaySlipDetails( Set<EmployeePaySlipDetails> employeePaySlipDetailsList) {
	 * 
	 * List<EmployeePaySlipDocumentDetailsResponseDto>
	 * employeePaySlipDocumentDetailsResponseDtoList = new
	 * ArrayList<EmployeePaySlipDocumentDetailsResponseDto>(); if (null !=
	 * employeePaySlipDetailsList) {
	 * 
	 * for (EmployeePaySlipDetails employeePaySlipDetails :
	 * employeePaySlipDetailsList) {
	 * 
	 * EmployeePaySlipDocumentDetailsResponseDto
	 * employeePaySlipDocumentDetailsResponseDto = new
	 * EmployeePaySlipDocumentDetailsResponseDto();
	 * 
	 * employeePaySlipDocumentDetailsResponseDto.setDocumentName(
	 * employeePaySlipDetails.getDocumentName());
	 * employeePaySlipDocumentDetailsResponseDto.setDocumentNumber(
	 * employeePaySlipDetails.getPaySlipNumber());
	 * employeePaySlipDocumentDetailsResponseDto.setDocumentType(
	 * employeePaySlipDetails.getDocumentType());
	 * employeePaySlipDocumentDetailsResponseDtoList.add(
	 * employeePaySlipDocumentDetailsResponseDto);
	 * 
	 * } } return employeePaySlipDocumentDetailsResponseDtoList; }
	 */

}