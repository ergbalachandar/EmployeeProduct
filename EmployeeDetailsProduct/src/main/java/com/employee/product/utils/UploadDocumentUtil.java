package com.employee.product.utils;

import java.util.Date;

import com.employee.product.dao.services.DocumentManagementService;
import com.employee.product.documentdetails.request.dto.UploadDocumentDetailsRequestDto;
import com.employee.product.documentdetails.response.dto.UploadDocumentDetailsResponseDto;
import com.employee.product.entity.employeedetails.EmployeeExpenseDetails;
import com.employee.product.entity.employeedetails.EmployeePassportDocumentDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDetails;
import com.employee.product.entity.employeedetails.EmployeeWorkPermitDocumentDetails;
import com.employee.product.expensedetails.response.dto.TypeStatus;

public class UploadDocumentUtil {

	/**
	 * A method for Upload document for several modules
	 * 
	 * @param loggedInUserName
	 * @param uploadDocumentDetailsRequestDto
	 * @param bytes
	 * @param documentManagementService
	 * @param fileName
	 * @throws Exception
	 */
	public static void uploadDocument(String loggedInUserName,
			UploadDocumentDetailsRequestDto uploadDocumentDetailsRequestDto, byte[] bytes,
			DocumentManagementService documentManagementService, String fileName, String ext) throws Exception {

		switch (uploadDocumentDetailsRequestDto.getDocumentType()) {
		case "1":
			uploadWorkPermitDocumentDetails(loggedInUserName, uploadDocumentDetailsRequestDto, bytes,
					documentManagementService, fileName);
			break;
		case "2":
			uploadPaySlipDocumentDetails(loggedInUserName, uploadDocumentDetailsRequestDto, bytes,
					documentManagementService, fileName, ext);
			break;
		case "3":
			uploadPassportDocumentDetails(loggedInUserName, uploadDocumentDetailsRequestDto, bytes,
					documentManagementService, fileName);
			break;
		case "4":
			uploadExpenseDocumentDetails(loggedInUserName, uploadDocumentDetailsRequestDto, bytes,
					documentManagementService, fileName, ext);
			break;
		default:
			throw new Exception("Document type doesnt valid");

		}

	}

	/**
	 * Upload Expense details
	 * 
	 * @param loggedInUserName
	 * @param uploadDocumentDetailsRequestDto
	 * @param bytes
	 * @param documentManagementService
	 * @param fileName
	 * @param ext 
	 */
	private static void uploadExpenseDocumentDetails(String loggedInUserName, UploadDocumentDetailsRequestDto uddReq,
			byte[] bytes, DocumentManagementService documentManagementService, String fileName, String ext) {
		EmployeeExpenseDetails expDet = new EmployeeExpenseDetails();
		expDet.setAmount(uddReq.getAmount());
		expDet.setDocumentData(bytes);
		expDet.setCurrencyType(uddReq.getTypeCurrency().name());
		expDet.setId(uddReq.getEmployeeId() + "" + new Date().getTime());
		expDet.setDocumentName(fileName);
		expDet.setDocumentType(ext);
		expDet.setReason(uddReq.getReason());
		expDet.setProofDate(uddReq.getProofDate());
		expDet.setStatus(TypeStatus.PEN.name());
		expDet.setProofNumber(uddReq.getProofNumber());
		expDet.setTypeExpense(uddReq.getTypeExpense().name());
		documentManagementService.addExpenseDocument(expDet, loggedInUserName, uddReq);
	}

	private static void uploadWorkPermitDocumentDetails(String loggedInUserName,
			UploadDocumentDetailsRequestDto uploadDocumentDetailsRequestDto, byte[] bytes,
			DocumentManagementService documentManagementService, String fileName) throws Exception {

		EmployeeWorkPermitDocumentDetails employeeWorkPermitDocumentDetails = new EmployeeWorkPermitDocumentDetails();
		employeeWorkPermitDocumentDetails.setDocumentData(bytes);
		employeeWorkPermitDocumentDetails.setWorkPermitNumber(uploadDocumentDetailsRequestDto.getDocumentNumber());
		employeeWorkPermitDocumentDetails.setDocumentName(fileName);
		documentManagementService.addWorkPermitDocument(employeeWorkPermitDocumentDetails, loggedInUserName,
				uploadDocumentDetailsRequestDto.getEmployeeId());
	}

	private static void uploadPaySlipDocumentDetails(String loggedInUserName, UploadDocumentDetailsRequestDto uddReq,
			byte[] bytes, DocumentManagementService documentManagementService, String fileName, String ext) throws Exception {

		EmployeePaySlipDetails ePDD = new EmployeePaySlipDetails();
		ePDD.setDocumentData(bytes);
		ePDD.setPaySlipNumber(uddReq.getEmployeeId() + "" + new Date().getTime());
		ePDD.setDocumentName(fileName);
		ePDD.setDocumentType(ext);
		ePDD.setPayslipType(uddReq.getPayslipType());
		ePDD.setPaySlipMonth(uddReq.getMonth());
		ePDD.setPaySlipYear(uddReq.getYear());
		ePDD.setUploadedDate(new java.sql.Date(new Date().getTime()));
		documentManagementService.addPaySlipDocument(ePDD, loggedInUserName, uddReq);

	}

	private static void uploadPassportDocumentDetails(String loggedInUserName,
			UploadDocumentDetailsRequestDto uploadDocumentDetailsRequestDto, byte[] bytes,
			DocumentManagementService documentManagementService, String fileName) throws Exception {

		EmployeePassportDocumentDetails employeePassportDocumentDetails = new EmployeePassportDocumentDetails();
		employeePassportDocumentDetails.setDocumentData(bytes);
		employeePassportDocumentDetails.setPassportNumber(uploadDocumentDetailsRequestDto.getDocumentNumber());
		employeePassportDocumentDetails.setDocumentName(fileName);
		documentManagementService.addPassportDocument(employeePassportDocumentDetails, loggedInUserName,
				uploadDocumentDetailsRequestDto.getEmployeeId());

	}

	public static void mapResponseUploadDocumentResponseDto(
			UploadDocumentDetailsResponseDto uploadDocumentDetailsResponseDto) {

		uploadDocumentDetailsResponseDto.setMessage("Upload is Successful");
	}

}
