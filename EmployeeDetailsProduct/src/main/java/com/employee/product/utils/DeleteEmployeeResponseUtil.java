package com.employee.product.utils;

import com.employee.product.employeedetails.response.dto.DeleteEmployeeResponseDto;

public class DeleteEmployeeResponseUtil {
	
	public static void mapResponseDeleteEmployeeResponse(DeleteEmployeeResponseDto deleteEmployeeResponseDto) {
		
		 deleteEmployeeResponseDto.setMessage("Employee Successfully deleted");
	}

}
