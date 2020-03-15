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

	public static void mappingEmployeeDataResponse(List<EmployeeDetails> employeeDetailsList,
			EmployeeDataResponseDto employeeDataResponseDto) {

		List<EmployeeDetailsResponseDto> employeeDetailsResponseDtoList = new ArrayList<EmployeeDetailsResponseDto>();

		for (EmployeeDetails employeeDetails : employeeDetailsList) {

			EmployeeDetailsResponseDto employeeDetailsResponseDto = new EmployeeDetailsResponseDto();

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

			// Mapping PassportDetails
			Set<EmployeePassportDetails> employeePassportDetailsList = employeeDetails.getEmployeePassportDetails();
			List<EmployeePassportDetailsResponseDto> employeePassportDetailsResponseDtoList = new ArrayList<EmployeePassportDetailsResponseDto>();
			for (EmployeePassportDetails employeePassportDetails : employeePassportDetailsList) {

				EmployeePassportDetailsResponseDto employeePassportDetailsResponseDto = new EmployeePassportDetailsResponseDto();

				employeePassportDetailsResponseDto.setEndDate(employeePassportDetails.getEndDate());
				employeePassportDetailsResponseDto.setIssuePlace(employeePassportDetails.getIssuePlace());
				employeePassportDetailsResponseDto.setPassportNumber(employeePassportDetails.getPassportNumber());
				employeePassportDetailsResponseDto.setStartDate(employeePassportDetails.getStartDate());
				employeePassportDetailsResponseDto.setValidity(employeePassportDetails.getValidity());
				employeePassportDetailsResponseDtoList.add(employeePassportDetailsResponseDto);
			}
			employeeDetailsResponseDto.setEmployeePassportDetailResponseDto(employeePassportDetailsResponseDtoList);

			// Mapping workPermit Details

			Set<EmployeeWorkPermitDetails> employeeWorkPermitDetailsList = employeeDetails
					.getEmployeeWorkPermitDetails();
			List<EmployeeWorkPermitDetailsResponseDto> employeeWorkPermitDetailsResponseDtoList = new ArrayList<EmployeeWorkPermitDetailsResponseDto>();

			for (EmployeeWorkPermitDetails employeeWorkPermitDetails : employeeWorkPermitDetailsList) {

				EmployeeWorkPermitDetailsResponseDto employeeWorkPermitDetailsResponseDto = new EmployeeWorkPermitDetailsResponseDto();

				employeeWorkPermitDetailsResponseDto
						.setWorkPermitNumber(employeeWorkPermitDetails.getWorkPermitNumber());
				employeeWorkPermitDetailsResponseDto.setEndDate(employeeWorkPermitDetails.getEndDate());
				employeeWorkPermitDetailsResponseDto.setStartDate(employeeWorkPermitDetails.getStartDate());
				employeeWorkPermitDetailsResponseDto.setValidity(employeeWorkPermitDetails.getValidity());

				employeeWorkPermitDetailsResponseDtoList.add(employeeWorkPermitDetailsResponseDto);
			}
			employeeDetailsResponseDto
					.setEmployeeWorkPermitDetailsResponseDto(employeeWorkPermitDetailsResponseDtoList);

			// Mapping Family Details
			Set<EmployeeFamilyDetails> employeeFamilyDetailsResponseList = employeeDetails.getEmployeeFamilyDetails();

			List<EmployeeFamilyDetailsResponseDto> employeeFamilyDetailsResponseDtoList = new ArrayList<EmployeeFamilyDetailsResponseDto>();

			for (EmployeeFamilyDetails employeeFamilyDetails : employeeFamilyDetailsResponseList) {

				EmployeeFamilyDetailsResponseDto employeeFamilyDetailsResponseDto = new EmployeeFamilyDetailsResponseDto();

				employeeFamilyDetailsResponseDto.setContactNumber(employeeFamilyDetails.getContactNumber());
				employeeFamilyDetailsResponseDto.setFirstName(employeeFamilyDetails.getFirstName());
				employeeFamilyDetailsResponseDto.setLastName(employeeFamilyDetails.getLastName());
				employeeFamilyDetailsResponseDto.setRelation(employeeFamilyDetails.getRelation());

				employeeFamilyDetailsResponseDtoList.add(employeeFamilyDetailsResponseDto);

			}
			employeeDetailsResponseDto.setEmployeeFamilyDetailsResponseDto(employeeFamilyDetailsResponseDtoList);

			// Mapping Payslip Details

			Set<EmployeePaySlipDetails> employeePaySlipDetailsList = employeeDetails.getEmployeePaySlipDetails();

			List<EmployeePaySlipDocumentDetailsResponseDto> employeePaySlipDocumentDetailsResponseDtoList = new ArrayList<EmployeePaySlipDocumentDetailsResponseDto>();

			for (EmployeePaySlipDetails employeePaySlipDetails : employeePaySlipDetailsList) {

				EmployeePaySlipDocumentDetailsResponseDto employeePaySlipDocumentDetailsResponseDto = new EmployeePaySlipDocumentDetailsResponseDto();

				employeePaySlipDocumentDetailsResponseDto.setDocumentName(employeePaySlipDetails.getPaySlipMonth());
				employeePaySlipDocumentDetailsResponseDto.setDocumentNumber(employeePaySlipDetails.getPaySlipNumber());
				employeePaySlipDocumentDetailsResponseDtoList.add(employeePaySlipDocumentDetailsResponseDto);

			}
			employeeDetailsResponseDto
					.setEmployeePaySlipDocumentDetailsResponseDto(employeePaySlipDocumentDetailsResponseDtoList);

			employeeDetailsResponseDtoList.add(employeeDetailsResponseDto);
		}

		employeeDataResponseDto.setEmployeeDetailsResponseDto(employeeDetailsResponseDtoList);

	}

}