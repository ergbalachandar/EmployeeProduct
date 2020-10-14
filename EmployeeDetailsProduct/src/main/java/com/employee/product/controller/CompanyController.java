package com.employee.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.product.companydetails.dto.CompanyResDto;
import com.employee.product.dao.services.DeleteCompanyService;
import com.employee.product.entity.companydetails.CompanyDetails;
import com.employee.product.utils.CompanyDetailsUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	DeleteCompanyService companyRetriaval;
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/details")
	@ApiOperation(value = "details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved companyDetails"),
			@ApiResponse(code = 401, message = "No Company Exist"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public CompanyResDto retrieveCompany(@RequestParam String companyId) throws Exception {
		if(null == companyId) {
			new Exception("Invalid details");
		}
		CompanyDetails company = companyRetriaval.getCompany(companyId);
		if(null == company) {
			new Exception("No Company found with your request !! Please contact our Customer Support");
		}
		CompanyResDto companyRes = new CompanyResDto();
		CompanyDetailsUtil.mapCompanyDetais(companyRes,company);
		return companyRes;
	}

}
