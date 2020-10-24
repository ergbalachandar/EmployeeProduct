package com.employee.product.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.employee.product.employeedetails.request.dto.AddEmployeeRequestDto;
import com.employee.product.employeedetails.request.dto.EmployeeDetailsRequestDto;
import com.employee.product.employeedetails.request.dto.EmployeeFamilyDetailsRequestDto;
import com.employee.product.employeedetails.request.dto.EmployeePassportDetailsRequestDto;
import com.employee.product.employeedetails.request.dto.EmployeePaySlipDetailsRequestDto;
import com.employee.product.employeedetails.request.dto.EmployeeWorkPermitDetailsRequestDto;
import com.employee.product.entity.companydetails.CompanyDetails;
import com.employee.product.entity.companydetails.Users;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.employeedetails.EmployeeExpenseDetails;
import com.employee.product.entity.employeedetails.EmployeeFamilyDetails;
import com.employee.product.entity.employeedetails.EmployeePassportDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDetails;
import com.employee.product.entity.employeedetails.EmployeeWorkPermitDetails;
import com.employee.product.expensedetails.response.dto.TypeCurrency;
import com.employee.product.expensedetails.response.dto.TypeExpense;
import com.employee.product.expensedetails.response.dto.TypeStatus;

public class AddEmployeeDetailsUtil {

	private static void mapEmployeeDetails(EmployeeDetailsRequestDto emloyeeDetailsRequestDto,
			EmployeeDetails employeeDetails, EmployeeDetails empActual) {

		employeeDetails.setAddressLine1(emloyeeDetailsRequestDto.getAddressLine1());
		employeeDetails.setAddressLine2(emloyeeDetailsRequestDto.getAddressLine2());
		employeeDetails.setCity(emloyeeDetailsRequestDto.getCity());
		employeeDetails.setContactNumber(emloyeeDetailsRequestDto.getContactNumber());
		employeeDetails.setCountry(emloyeeDetailsRequestDto.getCountry());
		employeeDetails.setDateOfBirth(emloyeeDetailsRequestDto.getDateOfBirth());
		employeeDetails.setDepartment(emloyeeDetailsRequestDto.getDepartment());
		employeeDetails.setEmailId(emloyeeDetailsRequestDto.getEmailId());
		employeeDetails.setFirstName(emloyeeDetailsRequestDto.getFirstName());
		employeeDetails.setJobRole(emloyeeDetailsRequestDto.getJobRole());
		employeeDetails.setLastName(emloyeeDetailsRequestDto.getLastName());
		employeeDetails.setReportingPerson(emloyeeDetailsRequestDto.getReportingPerson());
		employeeDetails.setSex(emloyeeDetailsRequestDto.getSex());
		employeeDetails.setState(emloyeeDetailsRequestDto.getState());
		employeeDetails.setWorkLocation(emloyeeDetailsRequestDto.getWorkLocation());
		employeeDetails.setActive(1);
		employeeDetails.setPostalCode(emloyeeDetailsRequestDto.getPostalCode());
		if (null != emloyeeDetailsRequestDto.getId()) {
			employeeDetails.setId(emloyeeDetailsRequestDto.getId());
		}
		employeeDetails.setUpdated_at(new Date());
		employeeDetails.setIban(emloyeeDetailsRequestDto.getIban());
		employeeDetails.setBic(emloyeeDetailsRequestDto.getBic());
		employeeDetails.setMaritalStatus(emloyeeDetailsRequestDto.getMaritalStatus().name());
		employeeDetails.setDateOfJoin(emloyeeDetailsRequestDto.getDateOfJoin());
		Set<EmployeeWorkPermitDetails> employeeWorkPermitDetailsSet = new HashSet<EmployeeWorkPermitDetails>();

		List<EmployeeWorkPermitDetailsRequestDto> employeeWorkPermitDetailsRequestDtoList = emloyeeDetailsRequestDto
				.getWorkPermitDetails();
		if (null != employeeWorkPermitDetailsRequestDtoList) {
			for (EmployeeWorkPermitDetailsRequestDto employeeWorPermitDetailsRequestDto : employeeWorkPermitDetailsRequestDtoList) {

				EmployeeWorkPermitDetails employeeWorkPermitDetails = new EmployeeWorkPermitDetails();

				employeeWorkPermitDetails.setWorkPermitNumber(employeeWorPermitDetailsRequestDto.getWorkPermitNumber());
				employeeWorkPermitDetails.setValidity(employeeWorPermitDetailsRequestDto.getValidity());
				employeeWorkPermitDetails.setStartDate(employeeWorPermitDetailsRequestDto.getStartDate());
				employeeWorkPermitDetails.setEndDate(employeeWorPermitDetailsRequestDto.getEndDate());
				employeeWorkPermitDetails.setDocumentName(employeeWorPermitDetailsRequestDto.getDocumentName());
				employeeWorkPermitDetails.setDocumentType(employeeWorPermitDetailsRequestDto.getDocumentType());

				employeeWorkPermitDetailsSet.add(employeeWorkPermitDetails);

			}
		}

		employeeDetails.setEmployeeWorkPermitDetails(employeeWorkPermitDetailsSet);

		Set<EmployeeFamilyDetails> employeeFamilyDetailsSet = new HashSet<EmployeeFamilyDetails>();

		List<EmployeeFamilyDetailsRequestDto> employeeFamilyDetailsRequestDtoList = emloyeeDetailsRequestDto
				.getFamilyDetails();
		if (employeeFamilyDetailsRequestDtoList != null)
			for (EmployeeFamilyDetailsRequestDto employeeFamilyDetailsRequestDto : employeeFamilyDetailsRequestDtoList) {
				EmployeeFamilyDetails employeeFamilyDetails = new EmployeeFamilyDetails();
				employeeFamilyDetails.setContactNumber(employeeFamilyDetailsRequestDto.getContactNumber());
				employeeFamilyDetails.setFirstName(employeeFamilyDetailsRequestDto.getFirstName());
				employeeFamilyDetails.setLastName(employeeFamilyDetailsRequestDto.getLastName());
				employeeFamilyDetails.setRelation(employeeFamilyDetailsRequestDto.getRelation());
				if (null != employeeFamilyDetailsRequestDto.getId()) {
					employeeFamilyDetails.setId(employeeFamilyDetailsRequestDto.getId());
				}
				employeeFamilyDetailsSet.add(employeeFamilyDetails);

			}

		employeeDetails.setEmployeeFamilyDetails(employeeFamilyDetailsSet);

		Set<EmployeePassportDetails> employeePassportDetailsSet = new HashSet<EmployeePassportDetails>();

		List<EmployeePassportDetailsRequestDto> employeePassportDetailsRequestDtoList = emloyeeDetailsRequestDto
				.getPassportDetails();

		if (null != employeePassportDetailsRequestDtoList) {
			for (EmployeePassportDetailsRequestDto employeePassportDetailsRequestDto : employeePassportDetailsRequestDtoList) {

				EmployeePassportDetails employeePassportDetails = new EmployeePassportDetails();

				employeePassportDetails.setEndDate(employeePassportDetailsRequestDto.getEndDate());
				employeePassportDetails.setIssuePlace(employeePassportDetailsRequestDto.getIssuePlace());
				employeePassportDetails.setPassportNumber(employeePassportDetailsRequestDto.getPassportNumber());
				employeePassportDetails.setStartDate(employeePassportDetailsRequestDto.getStartDate());
				employeePassportDetails.setValidity(employeePassportDetailsRequestDto.getValidity());
				employeePassportDetails.setEndDate(employeePassportDetailsRequestDto.getEndDate());
				employeePassportDetails.setBirthPlace(employeePassportDetailsRequestDto.getBirthPlace());
				employeePassportDetails.setDocumentName(employeePassportDetailsRequestDto.getDocumentName());
				employeePassportDetails.setDocumentType(employeePassportDetailsRequestDto.getDocumentType());

				employeePassportDetailsSet.add(employeePassportDetails);

			}
		}
		employeeDetails.setEmployeePassportDetails(employeePassportDetailsSet);
		//Not to modify Payslip Data when its not coming from FE
		if(null != empActual) {
		Set<EmployeePaySlipDetails> employeePaySlips = new HashSet<EmployeePaySlipDetails>();
		for(EmployeePaySlipDetails empAct:empActual.getEmployeePaySlipDetails()) {
			EmployeePaySlipDetails employeePaySlip = new EmployeePaySlipDetails();
			employeePaySlip.setDocumentData(empAct.getDocumentData());
			employeePaySlip.setDocumentName(empAct.getDocumentName());
			employeePaySlip.setDocumentType(empAct.getDocumentType());
			employeePaySlip.setPaySlipMonth(empAct.getPaySlipMonth());
			employeePaySlip.setPaySlipNumber(empAct.getPaySlipNumber());
			employeePaySlip.setPayslipType(empAct.getPayslipType());
			employeePaySlip.setPaySlipYear(empAct.getPaySlipYear());
			employeePaySlips.add(employeePaySlip);
		}
		employeeDetails.setEmployeePaySlipDetails(employeePaySlips);
		}
		//Not to modify expense Data when its  not coming from FE
		if(null != empActual) {
			Set<EmployeeExpenseDetails> employeeExpenses = new HashSet<EmployeeExpenseDetails>();
			for(EmployeeExpenseDetails empActExp:empActual.getEmployeeExpenseDetails()) {
				EmployeeExpenseDetails employeeExpense = new EmployeeExpenseDetails();
				employeeExpense.setAmount(empActExp.getAmount());
				employeeExpense.setApprovedDate(empActExp.getApprovedDate());
				employeeExpense.setApprovedId(empActExp.getApprovedId());
				employeeExpense.setApproverName(empActExp.getApproverName());
				employeeExpense.setCreatedDate(empActExp.getCreatedDate());
				employeeExpense.setCurrencyType(empActExp.getCurrencyType());
				employeeExpense.setDocumentName(empActExp.getDocumentName());
				employeeExpense.setDocumentType(empActExp.getDocumentType());
				employeeExpense.setId(empActExp.getId());
				employeeExpense.setReason(empActExp.getReason());
				employeeExpense.setProofDate(empActExp.getProofDate());
				employeeExpense.setStatus(empActExp.getStatus());
				employeeExpense.setTypeExpense(empActExp.getTypeExpense());
				employeeExpenses.add(employeeExpense);
			}
			employeeDetails.setEmployeeExpenseDetails(employeeExpenses);
			}
	}

	public static void mapAddEmployeeRequest(AddEmployeeRequestDto addEmployeeRequestDto, Users users,
			EmployeeDetails employeeDetails, CompanyDetails companyDetails, boolean newEmployee,
			PasswordEncoder encoder, EmployeeDetails empActual) {

		EmployeeDetailsRequestDto emloyeeDetailsRequestDto = addEmployeeRequestDto.getEmployeeDetails();

		users.setActive(1);
		users.setCountry(emloyeeDetailsRequestDto.getCountry());
		users.setFirstName(emloyeeDetailsRequestDto.getFirstName());
		users.setLastName(emloyeeDetailsRequestDto.getLastName());
		users.setPassword(encoder.encode(emloyeeDetailsRequestDto.getEmailId().toLowerCase()));
		users.setRole("Employee");
		users.setUserName(emloyeeDetailsRequestDto.getEmailId());
		users.setCreatedAt(new Date());

		Set<EmployeeDetails> employeeDetailsSet = new HashSet<EmployeeDetails>();
		// EmployeeDetails employeeDetails = new EmployeeDetails();

		mapEmployeeDetails(emloyeeDetailsRequestDto, employeeDetails, empActual);
//		CompanyDetails companyDetails = new CompanyDetails();
//		companyDetails.setId(Integer.valueOf(addEmployeeRequestDto.getCompanyId()));
//		employeeDetails.setCompanyDetails(companyDetails);

		employeeDetails.setCompanyDetails(companyDetails);
		employeeDetailsSet.add(employeeDetails);

		// users.setEmployeeDetails(employeeDetailsSet);

		// users.setCompanyDetails(companyDetails);
	}

	public static String generateEmployeeId(String companyName, String firstName, String lastName) {
		String comp = null;
		String fName = null;
		String sName = null;
		int num = generateRandomNumber();
		if (companyName.length() > 4) {
			comp = removeSpaces(companyName).substring(0, 4);
		} else {
			comp = companyName;
		}
		fName = firstName.toUpperCase().substring(0, 1);
		sName = lastName.toUpperCase().substring(0, 1);
		String employeeId = comp.toUpperCase() + fName + sName + String.valueOf(num);

		return employeeId;

	}

	// Remove all space characters
	private static String removeSpaces(String input) {
		return input.replaceAll(" ", "");
	}

	public static int generateRandomNumber() {
		Random generator = new Random();
		generator.setSeed(System.currentTimeMillis());
		int num = generator.nextInt(900000) + 100000;
		return num;

	}

	public static boolean checkForNewOrUpdateEmployee(boolean newEmployee,
			AddEmployeeRequestDto addEmployeeRequestDto) {

		if (StringUtils.isBlank(addEmployeeRequestDto.getEmployeeDetails().getId())) {
			newEmployee = true;
		}

		return newEmployee;
	}
}
