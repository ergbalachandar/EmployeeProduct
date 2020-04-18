package com.employee.product.dao.services;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.product.dao.interfaces.EmployeeDetailsInterface;
import com.employee.product.entity.employeedetails.EmployeePassportDetails;
import com.employee.product.entity.employeedetails.EmployeePassportDocumentDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDocumentDetails;
import com.employee.product.entity.employeedetails.EmployeeWorkPermitDetails;
import com.employee.product.entity.employeedetails.EmployeeWorkPermitDocumentDetails;

@Service
public class DocumentManagementService {

	@Autowired
	private EntityManager entity;
	
	
	@Transactional
	public void addWorkPermitDocument(
			EmployeeWorkPermitDocumentDetails employeeWorkPermitDocumentDetails) {
		EmployeeWorkPermitDetails employeeWorkPermitDetails = entity.find(EmployeeWorkPermitDetails.class,
				employeeWorkPermitDocumentDetails.getWorkPermitNumber());

		employeeWorkPermitDocumentDetails.setWorkpermit_number(employeeWorkPermitDetails);
		entity.persist(employeeWorkPermitDocumentDetails);

	}
	
	
	@Transactional
	public void addPaySlipDocument(
			EmployeePaySlipDocumentDetails employeePaySlipDocumentDetails) {

		EmployeePaySlipDetails employeePaySlipDetails = entity.find(EmployeePaySlipDetails.class,
				employeePaySlipDocumentDetails.getPaySlipNumber());
		employeePaySlipDocumentDetails.setPayslip_number(employeePaySlipDetails);
		entity.persist(employeePaySlipDocumentDetails);
	}
	
	@Transactional
	public void addPassportDocument(
			EmployeePassportDocumentDetails employeePassportDocumentDetails) {

		EmployeePassportDetails employeePassportDetails = entity.find(EmployeePassportDetails.class,
				employeePassportDocumentDetails.getPassportNumber());
		employeePassportDocumentDetails.setPassport_number(employeePassportDetails);
		entity.persist(employeePassportDocumentDetails);
	}

	
	
	@Transactional
	public <T> Object downloadDocument(
			String documentNumber, String documentType) {
		
		if(documentType.equals("1")) {
			EmployeeWorkPermitDocumentDetails employeeWorkPermitDocumentDetails = entity.find(EmployeeWorkPermitDocumentDetails.class, documentNumber);
			return employeeWorkPermitDocumentDetails;
			}
		else if(documentType.equals("2")) {
			EmployeePaySlipDocumentDetails employeePaySlipDocumentDetails = entity.find(EmployeePaySlipDocumentDetails.class, documentNumber);
			return employeePaySlipDocumentDetails;
			}
		else if(documentType.equals("3")) {
			EmployeePassportDocumentDetails employeePassportDocumentDetails = entity.find(EmployeePassportDocumentDetails.class, documentNumber);
			return employeePassportDocumentDetails;
			}

		return null;
	}

}
