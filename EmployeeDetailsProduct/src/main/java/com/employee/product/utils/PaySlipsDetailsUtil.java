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
/**
 * 
 * A class that helps to retrieve payslips and employee details
 * @author raj33
 *
 */
public class PaySlipsDetailsUtil {
	
	/**
	 * Mapping employee details & Payslips
	 * @param ePaySlipResList
	 * @param paySlipDetails
	 */
	public static void mapPaySlipDetails(EPaySlipResDto ePaySlipResList, List<EmployeeDetails> paySlipDetails) {
		if (null != paySlipDetails) {
			List<EPaySlips> epayList = new ArrayList<EPaySlips>();
			for (EmployeeDetails employeeDetails : paySlipDetails) {
				EPaySlips ePaySlips = new EPaySlips();
				mappingEmployeeDetailsForPayslip(ePaySlips,employeeDetails);
				List<EPaySlip> epaySlipList = new ArrayList<EPaySlip>();
					for(EmployeePaySlipDetails employeePaySlipDetails:employeeDetails.getEmployeePaySlipDetails()) {
							EPaySlip ePaySlip = new EPaySlip();
							ePaySlip.setDocumentName(employeePaySlipDetails.getDocumentName());
							ePaySlip.setDocumentNumber(employeePaySlipDetails.getPaySlipNumber());
							ePaySlip.setDocumentType(employeePaySlipDetails.getDocumentType());
							ePaySlip.setMonth(employeePaySlipDetails.getPaySlipMonth());
							ePaySlip.setYear(employeePaySlipDetails.getPaySlipYear());
							epaySlipList.add(ePaySlip);
				}
				ePaySlips.setEPaySlip(epaySlipList);
				epayList.add(ePaySlips);
		 }
			ePaySlipResList.setEPaySlips(epayList);
		}
		
	}
	
	/**
	 * Mapping employee details
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
		empDetails.setFirstName(employeeDetails.getFirstName());
		empDetails.setLastName(employeeDetails.getLastName());
		empDetails.setSex(employeeDetails.getSex());
		empDetails.setState(employeeDetails.getState());
		empDetails.setMaritalStatus(MaritalStat.valueOf(employeeDetails.getMaritalStatus()));
		empDetails.setJobRole(employeeDetails.getJobRole());
		ePaySlips.setEmpDetails(empDetails);
	}

	/**
	 * 
	 * @param paySlipDetails
	 * @param ePaySlipEmpRes
	 */
	public static void mapEmpPaySlipDetails(Set<EmployeePaySlipDetails> paySlipDetails,
			EPaySlipEmpRes ePaySlipEmpRes) {
			List<EPaySlip> ePaySlipList = new ArrayList<EPaySlip>();
			for(EmployeePaySlipDetails paySlip:paySlipDetails) {
				EPaySlip paySlipEmp = new EPaySlip();
				paySlipEmp.setDocumentName(paySlip.getDocumentName());
				paySlipEmp.setDocumentType(paySlip.getDocumentType());
				paySlipEmp.setMonth(paySlip.getPaySlipMonth());
				paySlipEmp.setDocumentNumber(paySlip.getPaySlipNumber());
				paySlipEmp.setYear(paySlip.getPaySlipYear());
				ePaySlipList.add(paySlipEmp);
			}
			ePaySlipEmpRes.setEPaySlip(ePaySlipList);
		}

}
