package com.employee.product.utils;

import java.util.ArrayList;
import java.util.List;

import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.employee.product.entity.employeedetails.EmployeePaySlipDetails;
import com.employee.product.payslipsdetails.response.dto.EPaySlip;
import com.employee.product.payslipsdetails.response.dto.EPaySlipResDto;
import com.employee.product.payslipsdetails.response.dto.EPaySlips;

public class PaySlipsDetailsUtil {
	

	public static void mapPaySlipDetails(EPaySlipResDto ePaySlipResList, List<EmployeeDetails> paySlipDetails) {
		if (null != paySlipDetails) {
			List<EPaySlips> epayList = new ArrayList<EPaySlips>();
			for (EmployeeDetails employeeDetails : paySlipDetails) {
				EPaySlips ePaySlips = new EPaySlips();
				ePaySlips.setFirstName(employeeDetails.getFirstName());
				ePaySlips.setLastName(employeeDetails.getLastName());
				List<EPaySlip> epaySlipList = new ArrayList<EPaySlip>();
					for(EmployeePaySlipDetails employeePaySlipDetails:employeeDetails.getEmployeePaySlipDetails()) {
							EPaySlip ePaySlip = new EPaySlip();
							ePaySlip.setDocumentName(employeePaySlipDetails.getDocumentName());
							ePaySlip.setDocumentNumber(employeePaySlipDetails.getPaySlipNumber());
							ePaySlip.setDocumentType(employeePaySlipDetails.getDocumentType());
							ePaySlip.setMonth(employeePaySlipDetails.getPaySlipMonth());
							epaySlipList.add(ePaySlip);
				}
				ePaySlips.setEPaySlip(epaySlipList);
				epayList.add(ePaySlips);
		 }
			ePaySlipResList.setEpayList(epayList);
		}
		
	}

}
