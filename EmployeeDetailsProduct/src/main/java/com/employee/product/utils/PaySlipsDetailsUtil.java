package com.employee.product.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.employee.product.employeedetails.dto.EmployeeDetailsDto;
import com.employee.product.employeedetails.dto.MaritalStat;
import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDetails;
import com.employee.product.payslipsdetails.response.dto.EPaySlip;
import com.employee.product.payslipsdetails.response.dto.EPaySlipEmpRes;
import com.employee.product.payslipsdetails.response.dto.EPaySlipResDto;
import com.employee.product.payslipsdetails.response.dto.EPaySlips;
import com.employee.product.payslipsdetails.response.dto.PaySlipType;

/**
 * 
 * A class that helps to retrieve payslips and employee details
 * 
 * @author raj33
 *
 */
public class PaySlipsDetailsUtil {

	/**
	 * Mapping employee details & Payslips
	 * 
	 * @param ePaySlipResList
	 * @param paySlipDetails
	 */
	public static void mapPaySlipDetails(EPaySlipResDto ePaySlipResList, List<EmployeeDetails> paySlipDetails, String m,
			String y) {
		if (null != paySlipDetails) {
			List<EPaySlips> epayList = new ArrayList<EPaySlips>();
			for (EmployeeDetails employeeDetails : paySlipDetails) {
				EPaySlips ePaySlips = new EPaySlips();	
				if (null != employeeDetails && employeeDetails.getActive() == 1 && null != employeeDetails.getEmployeePaySlipDetails()) {
					mappingEmployeeDetailsForPayslip(ePaySlips, employeeDetails);
					List<EPaySlip> epaySlipList = new ArrayList<EPaySlip>();
					for (EmployeePaySlipDetails paySlip : employeeDetails.getEmployeePaySlipDetails()) {
						if (paySlip.getPaySlipMonth().equals(m)
								&& paySlip.getPaySlipYear().equals(y)) {
							epaySlipList.add(mapPaySlip(paySlip));
						}
					}
						ePaySlips.setEPaySlip(epaySlipList);
						epayList.add(ePaySlips);
					
				}
			}
			ePaySlipResList.setEPaySlips(epayList);
		}

	}

	/**
	 * Mapping employee details
	 * 
	 * @param ePaySlips
	 * @param employeeDetails
	 */
	private static void mappingEmployeeDetailsForPayslip(EPaySlips ePaySlips, EmployeeDetails employeeDetails) {
		EmployeeDetailsDto empDetails = new EmployeeDetailsDto();
		empDetails.setAddressLine1(employeeDetails.getAddressLine1());
		empDetails.setAddressLine2(employeeDetails.getAddressLine2());
		empDetails.setCity(employeeDetails.getCity());
		empDetails.setContactNumber(employeeDetails.getContactNumber());
		empDetails.setCountry(employeeDetails.getCountry());
		empDetails.setDateOfBirth(employeeDetails.getDateOfBirth());
		empDetails.setEmailId(employeeDetails.getEmailId());
		if (null != empDetails.getDateOfJoin())
			empDetails.setDateOfJoin(employeeDetails.getDateOfJoin());
		empDetails.setFirstName(employeeDetails.getFirstName());
		empDetails.setLastName(employeeDetails.getLastName());
		empDetails.setSex(employeeDetails.getSex());
		empDetails.setState(employeeDetails.getState());
		if (null != employeeDetails.getMaritalStatus())
			empDetails.setMaritalStatus(MaritalStat.valueOf(employeeDetails.getMaritalStatus()));
		empDetails.setJobRole(employeeDetails.getJobRole());
		empDetails.setId(employeeDetails.getId());
		ePaySlips.setEmpDetails(empDetails);
	}

	/**
	 * 
	 * @param paySlipDetails
	 * @param ePaySlipEmpRes
	 */
	public static void mapEmpPaySlipDetails(Set<EmployeePaySlipDetails> paySlipDetails, EPaySlipEmpRes ePaySlipEmpRes, String id) {
		List<EPaySlip> ePaySlipList = new ArrayList<EPaySlip>();
		for (EmployeePaySlipDetails paySlip : paySlipDetails) {
				ePaySlipList.add(mapPaySlip(paySlip,id));
		}
		ePaySlipEmpRes.setEPaySlip(ePaySlipList);
	}
	
	private static EPaySlip mapPaySlip(EmployeePaySlipDetails paySlip, String id) {
		EPaySlip paySlipEmp = new EPaySlip();
		paySlipEmp.setFileName(paySlip.getDocumentName());
		if (null != paySlip.getPayslipType())
			paySlipEmp.setPaySlipType(PaySlipType.valueOf(paySlip.getPayslipType()));
		paySlipEmp.setMonth(paySlip.getPaySlipMonth());
		paySlipEmp.setFileType(paySlip.getDocumentType());
		paySlipEmp.setDocumentNumber(paySlip.getPaySlipNumber());
		paySlipEmp.setYear(paySlip.getPaySlipYear());
		paySlipEmp.setDocumentType("3");
		paySlipEmp.setUploadedDate(String.valueOf(paySlip.getUploadedDate()));
		paySlipEmp.setId(id);
		return paySlipEmp;	
	}
	
	private static EPaySlip mapPaySlip(EmployeePaySlipDetails paySlip) {
		EPaySlip paySlipEmp = new EPaySlip();
		paySlipEmp.setFileName(paySlip.getDocumentName());
		paySlipEmp.setDocumentType("2");
		if (null != paySlip.getPayslipType())
			paySlipEmp.setPaySlipType(PaySlipType.valueOf(paySlip.getPayslipType()));
		paySlipEmp.setMonth(paySlip.getPaySlipMonth());
		paySlipEmp.setFileType(paySlip.getDocumentType());
		paySlipEmp.setDocumentNumber(paySlip.getPaySlipNumber());
		paySlipEmp.setYear(paySlip.getPaySlipYear());
		paySlipEmp.setUploadedDate(String.valueOf(paySlip.getUploadedDate()));
		return paySlipEmp;	
	}

}
