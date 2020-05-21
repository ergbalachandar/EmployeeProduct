package com.employee.product.utils;

import com.employee.product.dao.services.DocumentManagementService;
import com.employee.product.documentdetails.request.dto.UploadDocumentDetailsRequestDto;
import com.employee.product.documentdetails.response.dto.UploadDocumentDetailsResponseDto;
import com.employee.product.entity.employeedetails.EmployeePassportDocumentDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDocumentDetails;
import com.employee.product.entity.employeedetails.EmployeeWorkPermitDocumentDetails;

public class UploadDocumentUtil {

	public static void uploadDocument(String loggedInUserName,UploadDocumentDetailsRequestDto uploadDocumentDetailsRequestDto, byte[] bytes,
			DocumentManagementService documentManagementService, String fileName) throws Exception {

		switch (uploadDocumentDetailsRequestDto.getDocumentType()) {
		case "1":
			uploadWorkPermitDocumentDetails(loggedInUserName,uploadDocumentDetailsRequestDto, bytes, documentManagementService,
					fileName);
			break;
		case "2":
			uploadPaySlipDocumentDetails(loggedInUserName,uploadDocumentDetailsRequestDto, bytes, documentManagementService, fileName);
			break;
		case "3":
			uploadPassportDocumentDetails(loggedInUserName,uploadDocumentDetailsRequestDto, bytes, documentManagementService, fileName);
			break;
		default:
			throw new Exception("Document type doesnt valid");

		}

	}

	private static void uploadWorkPermitDocumentDetails(String loggedInUserName, UploadDocumentDetailsRequestDto uploadDocumentDetailsRequestDto,
			byte[] bytes, DocumentManagementService documentManagementService, String fileName) throws Exception {

		EmployeeWorkPermitDocumentDetails employeeWorkPermitDocumentDetails = new EmployeeWorkPermitDocumentDetails();
		employeeWorkPermitDocumentDetails.setDocumentData(bytes);
		employeeWorkPermitDocumentDetails.setWorkPermitNumber(uploadDocumentDetailsRequestDto.getDocumentNumber());
		employeeWorkPermitDocumentDetails.setDocumentName(fileName);
		documentManagementService.addWorkPermitDocument(employeeWorkPermitDocumentDetails,
				 loggedInUserName,uploadDocumentDetailsRequestDto.getEmployeeId());
	}

	private static void uploadPaySlipDocumentDetails(String loggedInUserName, UploadDocumentDetailsRequestDto uploadDocumentDetailsRequestDto,
			byte[] bytes, DocumentManagementService documentManagementService, String fileName) throws Exception {

		EmployeePaySlipDocumentDetails employeePaySlipDocumentDetails = new EmployeePaySlipDocumentDetails();
		employeePaySlipDocumentDetails.setDocumentData(bytes);
		employeePaySlipDocumentDetails.setPaySlipNumber(uploadDocumentDetailsRequestDto.getDocumentNumber());
		employeePaySlipDocumentDetails.setDocumentName(fileName);
		documentManagementService.addPaySlipDocument(employeePaySlipDocumentDetails,
				 loggedInUserName,uploadDocumentDetailsRequestDto.getEmployeeId());

	}

	private static void uploadPassportDocumentDetails(String loggedInUserName,UploadDocumentDetailsRequestDto uploadDocumentDetailsRequestDto,
			byte[] bytes, DocumentManagementService documentManagementService, String fileName) throws Exception {

		EmployeePassportDocumentDetails employeePassportDocumentDetails = new EmployeePassportDocumentDetails();
		employeePassportDocumentDetails.setDocumentData(bytes);
		employeePassportDocumentDetails.setPassportNumber(uploadDocumentDetailsRequestDto.getDocumentNumber());
		employeePassportDocumentDetails.setDocumentName(fileName);
		documentManagementService.addPassportDocument(employeePassportDocumentDetails,
				 loggedInUserName,uploadDocumentDetailsRequestDto.getEmployeeId());

	}

	public static void mapResponseUploadDocumentResponseDto(
			UploadDocumentDetailsResponseDto uploadDocumentDetailsResponseDto) {

		uploadDocumentDetailsResponseDto.setMessage("Upload is Successful");
	}

}
