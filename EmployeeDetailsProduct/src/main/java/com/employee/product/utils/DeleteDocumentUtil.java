package com.employee.product.utils;

import java.util.Set;

import com.employee.product.dao.services.DocumentManagementService;
import com.employee.product.documentdetails.request.dto.DeleteDocumentRequestDto;
import com.employee.product.documentdetails.response.dto.DeleteDocumentResponseDto;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.employeedetails.EmployeePassportDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDetails;
import com.employee.product.entity.employeedetails.EmployeeWorkPermitDetails;

public class DeleteDocumentUtil {

	public static void validateRequestForOthers(String loggedInUserName, EmployeeDetails employeeDetails,
			String documentNumber, String documentType) throws Exception {

		boolean result = false;

		if (!employeeDetails.getEmailId().equalsIgnoreCase(loggedInUserName)) {
			throw new Exception("You dont have permission to delete the document");
		}

		if (documentType.equals("1")) {
			Set<EmployeeWorkPermitDetails> employeeWorkPermitDetailsSet = employeeDetails
					.getEmployeeWorkPermitDetails();
			for (EmployeeWorkPermitDetails employeeWorkPermitDetails : employeeWorkPermitDetailsSet) {

				if (employeeWorkPermitDetails.getWorkPermitNumber().equalsIgnoreCase(documentNumber)) {
					result = true;
					break;
				}
			}
		} else if (documentType.equals("2")) {
			Set<EmployeePaySlipDetails> employeePaySlipDetailsSet = employeeDetails.getEmployeePaySlipDetails();
			for (EmployeePaySlipDetails employeePaySlipDetails : employeePaySlipDetailsSet) {

				if (employeePaySlipDetails.getPaySlipNumber().equalsIgnoreCase(documentNumber)) {
					result = true;
					break;
				}
			}
		} else if (documentType.equals("3")) {
			Set<EmployeePassportDetails> employeePassportDetailsSet = employeeDetails.getEmployeePassportDetails();
			for (EmployeePassportDetails employeePassportDetails : employeePassportDetailsSet) {

				if (employeePassportDetails.getPassportNumber().equalsIgnoreCase(documentNumber)) {
					result = true;
				}
			}

		}

		if (!result) {
			throw new Exception("You dont have permission to delete the document");
		}

	}

	public static void deleteDocument(DeleteDocumentRequestDto deleteDocumentRequestDto,
			DocumentManagementService documentManagementService) {
		documentManagementService.deleteDocument(deleteDocumentRequestDto.getDocumentNumber(),
				deleteDocumentRequestDto.getDocumentType());

		// return
		// documentManagementService.deleteDocument(deleteDocumentRequestDto.getDocumentNumber(),
		// deleteDocumentRequestDto.getDocumentType());

	}

	public static void mapResponse(DeleteDocumentResponseDto deleteDocumentResponseDto) {
		deleteDocumentResponseDto.setMessage("Document Successfully Deleted");
	}
}
