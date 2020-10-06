package com.employee.product.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.product.dao.services.EMasterService;
import com.employee.product.masterdetails.dto.ECDAReq;
import com.employee.product.masterdetails.dto.ECDARes;
import com.employee.product.masterdetails.dto.EMasterLoginReq;
import com.employee.product.masterdetails.dto.EMasterLoginRes;
import com.employee.product.masterdetails.dto.EMasterSignReq;
import com.employee.product.masterdetails.dto.EMasterSignRes;
import com.employee.product.utils.EMasterLoginUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController

@RequestMapping("/emaster")
public class MasterController {

	@Autowired
	private EMasterService eMasterService;

	@RequestMapping(method = RequestMethod.POST, value = "/mlogin")
	@ApiOperation(value = "Master Login")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Master Login"),
			@ApiResponse(code = 401, message = "You are not authorized as Master login"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@ResponseBody
	public EMasterLoginRes loginUpDetails(@RequestBody EMasterLoginReq eMasterLoginReqDto, HttpSession httpSession)
			throws Exception {
		if (null == eMasterLoginReqDto || null == eMasterLoginReqDto.getMPassword()
				|| null == eMasterLoginReqDto.getMUserName()) {
			throw new Exception("InValid Request");
		}
		EMasterLoginRes emaster = new EMasterLoginRes();
		EMasterLoginUtil.validateMLoginDetails(eMasterService, eMasterLoginReqDto, emaster);

		return emaster;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/comdetails")
    @ApiOperation(value = "Master Admin retrieves all company Info")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "company information"),
            @ApiResponse(code = 401, message = "You are not authorized as Master Signup"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @ResponseBody
    public ECDARes companyDetailsForAudit(@RequestBody ECDAReq eCDAReq,
                                                          HttpSession httpSession) throws Exception {
    	if(null == eCDAReq || StringUtils.isBlank(eCDAReq.getUserName())) {
    		throw new Exception("InValid Request");
    	}	
    	ECDARes eCDARes = new ECDARes();
    	EMasterLoginUtil.retrieveMComDetails(eMasterService, eCDARes, eCDAReq.getUserName());	
        
        return eCDARes;
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/msignup")
    @ApiOperation(value = "Master Admin ESignup")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Master Login"),
            @ApiResponse(code = 401, message = "You are not authorized as Master Signup"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @ResponseBody
    public EMasterSignRes SignUp(@RequestBody EMasterSignReq eMasterSignReq,
                                                          HttpSession httpSession) throws Exception {
    	if(null == eMasterSignReq || null == eMasterSignReq.getMPassword() || null == eMasterSignReq.getMUserName() || null == eMasterSignReq.getMUserName() || null == eMasterSignReq.getMFirstName() || null == eMasterSignReq.getMLastName()) {
    		throw new Exception("InValid Request");
    	}
    	EMasterSignRes emaster = new EMasterSignRes();
    	EMasterLoginUtil.validateMSignDetails(eMasterService,eMasterSignReq,emaster);
        
        return emaster;
    }

}
