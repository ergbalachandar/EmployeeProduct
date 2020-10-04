package com.employee.product.utils;

import com.employee.product.dao.services.EMasterService;
import com.employee.product.entity.masterdetails.MasterUsers;
import com.employee.product.masterdetails.dto.ECDARes;
import com.employee.product.masterdetails.dto.EMasterLoginReq;
import com.employee.product.masterdetails.dto.EMasterLoginRes;
import com.employee.product.masterdetails.dto.EMasterSignReq;
import com.employee.product.masterdetails.dto.EMasterSignRes;
/**
 * 
 * @author mProduct
 * This class is used for master login for analytics
 *
 */
public class EMasterLoginUtil {

	
	
	/**
	 * 
	 * @param eMasterService
	 * @param eMasterLoginReqDto
	 * @param emaster
	 * @throws Exception
	 */
    public static void validateMLoginDetails(EMasterService eMasterService, EMasterLoginReq eMasterLoginReqDto, EMasterLoginRes emaster) throws Exception {

    	MasterUsers masterService = eMasterService.findByUser(eMasterLoginReqDto.getMUserName());
    	if(masterService.getMPassword().equals(eMasterLoginReqDto.getMPassword())) {
    			emaster.setMFirstName(masterService.getMFirstName());
    			emaster.setMLastName(masterService.getMLastName());
    			emaster.setMrole(masterService.getMRole());
    			emaster.setMUserName(masterService.getMUserName());
    	}else {
    		throw new Exception("Password is incorrect");
    	}
    	
    }
    
    /**
     * 
     * @param eMasterService
     * @param eMasterSignReq
     * @param emaster
     */
	public static void validateMSignDetails(EMasterService eMasterService, EMasterSignReq eMasterSignReq,
			EMasterSignRes emaster) {
		emaster.setMessage(eMasterService.signUpMaster(eMasterSignReq));
	}
	
	/**
	 * 
	 * @param eCDARes
	 * @param id
	 * @throws Exception 
	 */
	public static void retrieveMComDetails(EMasterService eMasterService,ECDARes eCDARes, String userName) throws Exception {
		MasterUsers masterAcc = eMasterService.findByUser(userName);
		eCDARes.setMfirstName(masterAcc.getMFirstName());
		eCDARes.setMLastName(masterAcc.getMLastName());
		eCDARes.setMRole(masterAcc.getMRole());
		if(masterAcc.getMRole().equals("mAdmin")) {
			eMasterService.comDetails(eCDARes);	
		}
	}
    }
