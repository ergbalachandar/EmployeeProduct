package com.employee.product.utils;

import com.employee.product.companydetails.dto.CompanyResDto;
import com.employee.product.entity.companydetails.CompanyDetails;

public class CompanyDetailsUtil {
	/**
	 * 
	 * @param companyRes
	 * @param company
	 */
	public static void mapCompanyDetais(CompanyResDto companyRes, CompanyDetails company) {
		companyRes.setComName(company.getCompanyName());
		companyRes.setCity(company.getCity());
		companyRes.setCode(company.getId());
		companyRes.setCountry(company.getCountry());
	}

}
