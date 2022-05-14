package com.employee.product.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.employee.product.entity.employeedetails.EmployeeLeaveDetails;
import com.employee.product.timesheet.res.dto.ELeavesRes;
import com.employee.product.timesheet.res.dto.Leave;

/**
 * 
 * A class that helps to retrieve timesheet,leaves and employee details
 * 
 * @author raj33
 *
 */
public class TimeSheetDetailsUtil {

	/**
	 * 
	 * @param empLeaveRes
	 * @param employeeLeaveDetails
	 */
	public static void mapLeaveDetails(ELeavesRes empLeaveRes, Set<EmployeeLeaveDetails> employeeLeaveDetails) {
		List<Leave> leaves = new ArrayList<Leave>();
		for(EmployeeLeaveDetails empDet:employeeLeaveDetails) {
			Leave leave = new Leave();
			leave.setCreatedDate(empDet.getCreatedDate());
			leave.setLeaveIdNumber(empDet.getLeaveIdNumber());
			leave.setLeaveType(empDet.getLeaveType());
			leave.setNoOfDays(empDet.getNoOfDays());
			leaves.add(leave);
		}
		empLeaveRes.setLeave(leaves);
	}

}
