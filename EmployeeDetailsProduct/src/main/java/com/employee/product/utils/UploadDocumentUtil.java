package com.employee.product.utils;

import com.employee.product.dao.services.DocumentManagementService;
import com.employee.product.documentdetails.request.dto.UploadDocumentDetailsRequestDto;
import com.employee.product.documentdetails.response.dto.UploadDocumentDetailsResponseDto;
import com.employee.product.entity.employeedetails.EmployeePassportDocumentDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDocumentDetails;
import com.employee.product.entity.employeedetails.EmployeeWorkPermitDocumentDetails;

public class UploadDocumentUtil {

	public static void uploadDocument(UploadDocumentDetailsRequestDto uploadDocumentDetailsRequestDto, byte[] bytes,
			DocumentManagementService documentManagementService, String fileName) throws Exception {

		switch (uploadDocumentDetailsRequestDto.getDocumentType()) {
		case "1":
			uploadWorkPermitDocumentDetails(uploadDocumentDetailsRequestDto, bytes, documentManagementService,
					fileName);
			break;
		case "2":
			uploadPaySlipDocumentDetails(uploadDocumentDetailsRequestDto, bytes, documentManagementService, fileName);
			break;
		case "3":
			uploadPassportDocumentDetails(uploadDocumentDetailsRequestDto, bytes, documentManagementService, fileName);
			break;
		default:
			throw new Exception("Document type doesnt valid");

		}

	}

	private static void uploadWorkPermitDocumentDetails(UploadDocumentDetailsRequestDto uploadDocumentDetailsRequestDto,
			byte[] bytes, DocumentManagementService documentManagementService, String fileName) {

		EmployeeWorkPermitDocumentDetails employeeWorkPermitDocumentDetails = new EmployeeWorkPermitDocumentDetails();
		employeeWorkPermitDocumentDetails.setDocumentData(bytes);
		employeeWorkPermitDocumentDetails.setWorkPermitNumber(uploadDocumentDetailsRequestDto.getDocumentNumber());
		employeeWorkPermitDocumentDetails.setDocumentName(fileName);
		documentManagementService.addWorkPermitDocument(employeeWorkPermitDocumentDetails);
	}

	private static void uploadPaySlipDocumentDetails(UploadDocumentDetailsRequestDto uploadDocumentDetailsRequestDto,
			byte[] bytes, DocumentManagementService documentManagementService, String fileName) {

		EmployeePaySlipDocumentDetails employeePaySlipDocumentDetails = new EmployeePaySlipDocumentDetails();
		employeePaySlipDocumentDetails.setDocumentData(bytes);
		employeePaySlipDocumentDetails.setPaySlipNumber(uploadDocumentDetailsRequestDto.getDocumentNumber());
		employeePaySlipDocumentDetails.setDocumentName(fileName);
		documentManagementService.addPaySlipDocument(employeePaySlipDocumentDetails);

	}

	private static void uploadPassportDocumentDetails(UploadDocumentDetailsRequestDto uploadDocumentDetailsRequestDto,
			byte[] bytes, DocumentManagementService documentManagementService, String fileName) {

		EmployeePassportDocumentDetails employeePassportDocumentDetails = new EmployeePassportDocumentDetails();
		employeePassportDocumentDetails.setDocumentData(bytes);
		employeePassportDocumentDetails.setPassportNumber(uploadDocumentDetailsRequestDto.getDocumentNumber());
		employeePassportDocumentDetails.setDocumentName(fileName);
		documentManagementService.addPassportDocument(employeePassportDocumentDetails);

	}
	
	public static void mapResponseUploadDocumentResponseDto(UploadDocumentDetailsResponseDto uploadDocumentDetailsResponseDto) {
		
		uploadDocumentDetailsResponseDto.setMessage("Upload is Successful");
	}

}
