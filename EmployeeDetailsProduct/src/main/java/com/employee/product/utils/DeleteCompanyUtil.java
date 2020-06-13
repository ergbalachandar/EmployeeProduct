package com.employee.product.utils;

import com.employee.product.companydetails.response.dto.DeleteCompanyResponseDto;

public class DeleteCompanyUtil {

	public static void deleteCompanyResponse(DeleteCompanyResponseDto deleteCompanyResponseDto) {
		deleteCompanyResponseDto.setMessage("Successfully Deleted");

	}

}
