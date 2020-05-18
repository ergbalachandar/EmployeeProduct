package com.employee.product.dao.services;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.product.dao.interfaces.EmployeePassportDocumentDetailsInterface;
import com.employee.product.dao.interfaces.EmployeePaySlipDocumentDetailsInterface;
import com.employee.product.dao.interfaces.EmployeeWorkPermitDocumentDetailsInterface;
import com.employee.product.dao.interfaces.LoginDetailsInterface;
import com.employee.product.entity.companydetails.Users;
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

	@Autowired
	private EmployeeWorkPermitDocumentDetailsInterface employeeWorkPermitDocumentDetailsInterface;

	@Autowired
	private EmployeePaySlipDocumentDetailsInterface employeePaySlipDocumentDetailsInterface;

	@Autowired
	private EmployeePassportDocumentDetailsInterface employeePassportDocumentDetailsInterface;

	@Autowired
	private LoginDetailsInterface loginDetailsInterface;

	@Transactional
	public void addWorkPermitDocument(EmployeeWorkPermitDocumentDetails employeeWorkPermitDocumentDetails,
			String loggedInUserName, String employeeId) throws Exception {
		EmployeeWorkPermitDetails employeeWorkPermitDetails = new EmployeeWorkPermitDetails();

		employeeWorkPermitDetails.setWorkPermitNumber(employeeWorkPermitDocumentDetails.getWorkPermitNumber());

		Users users = loginDetailsInterface.findByEmployeeDetailsEmployeeWorkPermitDetails(employeeWorkPermitDetails);

		Optional<Users> userRoleOfLoggedInEmployee = loginDetailsInterface.findById(loggedInUserName);
		accessValidation(userRoleOfLoggedInEmployee, users,employeeId);

		EmployeeWorkPermitDocumentDetails employeeWorkPermitDocumentDetailsRetrieval = entity
				.find(EmployeeWorkPermitDocumentDetails.class, employeeWorkPermitDocumentDetails.getWorkPermitNumber());
		if (null != employeeWorkPermitDocumentDetailsRetrieval) {

			employeeWorkPermitDocumentDetailsRetrieval
					.setDocumentData(employeeWorkPermitDocumentDetails.getDocumentData());
			employeeWorkPermitDocumentDetailsRetrieval
					.setDocumentName(employeeWorkPermitDocumentDetails.getDocumentName());
		} else {
			employeeWorkPermitDocumentDetails.setWorkpermit_number(users.getEmployeeDetails().stream().findFirst().get()
					.getEmployeeWorkPermitDetails().stream().findFirst().get());
			entity.persist(employeeWorkPermitDocumentDetails);
		}
	}

	@Transactional
	public void addPaySlipDocument(EmployeePaySlipDocumentDetails employeePaySlipDocumentDetails,
			String loggedInUserName, String employeeId) throws Exception {

		EmployeePaySlipDetails employeePaySlipDetails = new EmployeePaySlipDetails();

		employeePaySlipDetails.setPaySlipNumber(employeePaySlipDocumentDetails.getPaySlipNumber());

		Users users = loginDetailsInterface.findByEmployeeDetailsEmployeePaySlipDetails(employeePaySlipDetails);

		Optional<Users> userRoleOfLoggedInEmployee = loginDetailsInterface.findById(loggedInUserName);
		accessValidation(userRoleOfLoggedInEmployee, users, employeeId);

		EmployeePaySlipDocumentDetails employeePaySlipDocumentDetailsRetrieval = entity
				.find(EmployeePaySlipDocumentDetails.class, employeePaySlipDocumentDetails.getPaySlipNumber());
		if (null != employeePaySlipDocumentDetailsRetrieval) {

			employeePaySlipDocumentDetailsRetrieval.setDocumentData(employeePaySlipDocumentDetails.getDocumentData());
			employeePaySlipDocumentDetailsRetrieval.setDocumentName(employeePaySlipDocumentDetails.getDocumentName());
		} else {
			employeePaySlipDocumentDetails.setPayslip_number(users.getEmployeeDetails().stream().findFirst().get()
					.getEmployeePaySlipDetails().stream().findFirst().get());
			entity.persist(employeePaySlipDocumentDetails);
		}
	}

	@Transactional
	public void addPassportDocument(EmployeePassportDocumentDetails employeePassportDocumentDetails,
			String loggedInUserName, String employeeId) throws Exception {

		EmployeePassportDetails employeePassportDetails = new EmployeePassportDetails();

		employeePassportDetails.setPassportNumber(employeePassportDocumentDetails.getPassportNumber());

		Users users = loginDetailsInterface.findByEmployeeDetailsEmployeePassportDetails(employeePassportDetails);

		Optional<Users> userRoleOfLoggedInEmployee = loginDetailsInterface.findById(loggedInUserName);
		accessValidation(userRoleOfLoggedInEmployee, users, employeeId);

		EmployeePassportDocumentDetails employeePassportDocumentDetailsRetrieval = entity
				.find(EmployeePassportDocumentDetails.class, employeePassportDocumentDetails.getPassportNumber());
		if (null != employeePassportDocumentDetailsRetrieval) {

			employeePassportDocumentDetailsRetrieval.setDocumentData(employeePassportDocumentDetails.getDocumentData());
			employeePassportDocumentDetailsRetrieval.setDocumentName(employeePassportDocumentDetails.getDocumentName());
		} else {
			employeePassportDocumentDetails.setPassport_number(users.getEmployeeDetails().stream().findFirst().get()
					.getEmployeePassportDetails().stream().findFirst().get());
			entity.persist(employeePassportDocumentDetails);
		}
	}

	@Transactional
	public <T> Object downloadDocument(String documentNumber, String documentType) {

		if (documentType.equals("1")) {
			EmployeeWorkPermitDocumentDetails employeeWorkPermitDocumentDetails = entity
					.find(EmployeeWorkPermitDocumentDetails.class, documentNumber);
			return employeeWorkPermitDocumentDetails;
		} else if (documentType.equals("2")) {
			EmployeePaySlipDocumentDetails employeePaySlipDocumentDetails = entity
					.find(EmployeePaySlipDocumentDetails.class, documentNumber);
			return employeePaySlipDocumentDetails;
		} else if (documentType.equals("3")) {
			EmployeePassportDocumentDetails employeePassportDocumentDetails = entity
					.find(EmployeePassportDocumentDetails.class, documentNumber);
			return employeePassportDocumentDetails;
		}

		return null;
	}

	public void deleteDocument(String documentNumber, String documentType) {

		if (documentType.equals("1")) {
			employeeWorkPermitDocumentDetailsInterface.deleteById(documentNumber);
		} else if (documentType.equals("2")) {
			employeePaySlipDocumentDetailsInterface.deleteById(documentNumber);
		} else if (documentType.equals("3")) {
			employeePassportDocumentDetailsInterface.deleteById(documentNumber);
		}

	}

	public static void accessValidation(Optional<Users> userRoleOfLoggedInEmployee, Users users, String employeeId) throws Exception {

		if (userRoleOfLoggedInEmployee.get().getEmployeeDetails().stream().findFirst().get().getId() != users
				.getEmployeeDetails().stream().findFirst().get().getId()
				&& !userRoleOfLoggedInEmployee.get().getRole().equalsIgnoreCase("Admin")) {
			throw new Exception("You are not authorised to Manage the document");
		}

		if (userRoleOfLoggedInEmployee.get().getRole().equalsIgnoreCase("Admin")
				&& userRoleOfLoggedInEmployee.get().getCompanyDetails().getId() != users.getCompanyDetails().getId()) {
			throw new Exception("You are not authorised to manage the document of other company employee");
		}
		
		if(userRoleOfLoggedInEmployee.get().getRole().equalsIgnoreCase("Admin") && users.getEmployeeDetails().stream().findFirst().get().getId() != employeeId) {
			throw new Exception("Document you are trying to update is already associated with other employee");
		}

	}

}
