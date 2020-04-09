package com.employee.product.utils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.employee.product.employeedetails.request.dto.AddEmployeeRequestDto;
import com.employee.product.employeedetails.request.dto.EmployeeDetailsRequestDto;
import com.employee.product.employeedetails.request.dto.EmployeeFamilyDetailsRequestDto;
import com.employee.product.employeedetails.request.dto.EmployeePassportDetailsRequestDto;
import com.employee.product.employeedetails.request.dto.EmployeePaySlipDetailsRequestDto;
import com.employee.product.employeedetails.request.dto.EmployeeWorkPermitDetailsRequestDto;
import com.employee.product.entity.companydetails.CompanyDetails;
import com.employee.product.entity.companydetails.Users;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.employeedetails.EmployeeFamilyDetails;
import com.employee.product.entity.employeedetails.EmployeePassportDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDetails;
import com.employee.product.entity.employeedetails.EmployeeWorkPermitDetails;

public class AddEmployeeDetailsUtil {

	private static void mapEmployeeDetails(EmployeeDetailsRequestDto emloyeeDetailsRequestDto,
			EmployeeDetails employeeDetails) {

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
		if (null != emloyeeDetailsRequestDto.getId()) {
			employeeDetails.setId(emloyeeDetailsRequestDto.getId());
		}
		employeeDetails.setUpdated_at(new Date());

		Set<EmployeeWorkPermitDetails> employeeWorkPermitDetailsSet = new HashSet<EmployeeWorkPermitDetails>();

		List<EmployeeWorkPermitDetailsRequestDto> employeeWorkPermitDetailsRequestDtoList = emloyeeDetailsRequestDto
				.getEmployeeWorkPermitDetailsRequestDto();
		if (null != employeeWorkPermitDetailsRequestDtoList) {
			for (EmployeeWorkPermitDetailsRequestDto employeeWorPermitDetailsRequestDto : employeeWorkPermitDetailsRequestDtoList) {

				EmployeeWorkPermitDetails employeeWorkPermitDetails = new EmployeeWorkPermitDetails();

				employeeWorkPermitDetails.setWorkPermitNumber(employeeWorPermitDetailsRequestDto.getWorkPermitNumber());
				employeeWorkPermitDetails.setValidity(employeeWorPermitDetailsRequestDto.getValidity());
				employeeWorkPermitDetails.setStartDate(employeeWorPermitDetailsRequestDto.getStartDate());
				employeeWorkPermitDetails.setEndDate(employeeWorPermitDetailsRequestDto.getEndDate());

				employeeWorkPermitDetailsSet.add(employeeWorkPermitDetails);

			}
		}

		employeeDetails.setEmployeeWorkPermitDetails(employeeWorkPermitDetailsSet);

		Set<EmployeeFamilyDetails> employeeFamilyDetailsSet = new HashSet<EmployeeFamilyDetails>();

		List<EmployeeFamilyDetailsRequestDto> employeeFamilyDetailsRequestDtoList = emloyeeDetailsRequestDto
				.getEmployeeFamilyDetailsRequestDto();
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
				.getEmployeePassportDetailRequestDto();

		if (null != employeePassportDetailsRequestDtoList) {
			for (EmployeePassportDetailsRequestDto employeePassportDetailsRequestDto : employeePassportDetailsRequestDtoList) {

				EmployeePassportDetails employeePassportDetails = new EmployeePassportDetails();

				employeePassportDetails.setEndDate(employeePassportDetailsRequestDto.getEndDate());
				employeePassportDetails.setIssuePlace(employeePassportDetailsRequestDto.getIssuePlace());
				employeePassportDetails.setPassportNumber(employeePassportDetailsRequestDto.getPassportNumber());
				employeePassportDetails.setStartDate(employeePassportDetailsRequestDto.getStartDate());
				employeePassportDetails.setValidity(employeePassportDetailsRequestDto.getValidity());
				employeePassportDetails.setEndDate(employeePassportDetailsRequestDto.getEndDate());

				employeePassportDetailsSet.add(employeePassportDetails);

			}
		}
		employeeDetails.setEmployeePassportDetails(employeePassportDetailsSet);

		Set<EmployeePaySlipDetails> employeePaySlipDetailsSet = new HashSet<EmployeePaySlipDetails>();

		List<EmployeePaySlipDetailsRequestDto> employeePaySlipDetailsRequestDtoList = emloyeeDetailsRequestDto
				.getEmployeePaySlipDetailsRequestDto();

		if (null != employeePaySlipDetailsRequestDtoList) {
			for (EmployeePaySlipDetailsRequestDto employeePaySlipDetailsRequestDto : employeePaySlipDetailsRequestDtoList) {
				EmployeePaySlipDetails employeePaySlipDetails = new EmployeePaySlipDetails();

				employeePaySlipDetails.setPaySlipMonth(employeePaySlipDetailsRequestDto.getPaySlipMonth());
				employeePaySlipDetails.setPaySlipNumber(employeePaySlipDetailsRequestDto.getPaySlipNumber());
				employeePaySlipDetailsSet.add(employeePaySlipDetails);
			}

			employeeDetails.setEmployeePaySlipDetails(employeePaySlipDetailsSet);
		}

	}

	public static void mapAddEmployeeRequest(AddEmployeeRequestDto addEmployeeRequestDto, Users users,
			EmployeeDetails employeeDetails, CompanyDetails companyDetails) {

		EmployeeDetailsRequestDto emloyeeDetailsRequestDto = addEmployeeRequestDto.getEmployeeDetailsRequestDto();

		users.setActive(1);
		users.setCountry(emloyeeDetailsRequestDto.getCountry());
		users.setFirstName(emloyeeDetailsRequestDto.getFirstName());
		users.setLastName(emloyeeDetailsRequestDto.getLastName());
		users.setPassword(emloyeeDetailsRequestDto.getEmailId());
		users.setRole("Employee");
		users.setUserName(emloyeeDetailsRequestDto.getEmailId());
		users.setCreatedAt(new Date());

		Set<EmployeeDetails> employeeDetailsSet = new HashSet<EmployeeDetails>();
		// EmployeeDetails employeeDetails = new EmployeeDetails();

		mapEmployeeDetails(emloyeeDetailsRequestDto, employeeDetails);
//		CompanyDetails companyDetails = new CompanyDetails();
//		companyDetails.setId(Integer.valueOf(addEmployeeRequestDto.getCompanyId()));
//		employeeDetails.setCompanyDetails(companyDetails);
		
        employeeDetails.setCompanyDetails(companyDetails);
		employeeDetailsSet.add(employeeDetails);

		// users.setEmployeeDetails(employeeDetailsSet);

		//users.setCompanyDetails(companyDetails);
	}
}
