package com.employee.product.dao.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee.product.dao.interfaces.IEMasterServiceComInterface;
import com.employee.product.dao.interfaces.IEMasterServiceInterface;
import com.employee.product.entity.masterdetails.CompanyDetailsMadm;
import com.employee.product.entity.masterdetails.MasterUsers;
import com.employee.product.masterdetails.dto.ECDA;
import com.employee.product.masterdetails.dto.ECDARes;
import com.employee.product.masterdetails.dto.EMasterSignReq;

@Service
public class EMasterService  {

    @Autowired
    IEMasterServiceInterface ieMasterServiceInterface;
    
    @Autowired
    IEMasterServiceComInterface ieMasterServiceComInterface;
    
    

    @Transactional
    public MasterUsers findByUser(String username) throws UsernameNotFoundException {
        return ieMasterServiceInterface.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }
    
    @Transactional
    public String signUpMaster(EMasterSignReq sign) {
        MasterUsers mUsers = new MasterUsers();
        mUsers.setCreatedAt(new Date());
        mUsers.setMFirstName(sign.getMFirstName());
        mUsers.setMLastName(sign.getMLastName());
        mUsers.setMPassword(sign.getMPassword());
        mUsers.setMRole(sign.getMrole());
        mUsers.setMUserName(sign.getMUserName());
        ieMasterServiceInterface.save(mUsers);
        return "MAdmin successfully added";
    }
    
    

    @Transactional
    public ECDARes comDetails(ECDARes eCDARes) throws Exception {
        List<CompanyDetailsMadm> compList = ieMasterServiceComInterface.findAll();
    	List<ECDA> ecdaList = new ArrayList<ECDA>();
        if(null == compList) {
        	throw new Exception("No companies registered Yet");
        }
        for(CompanyDetailsMadm cdm:compList) {
        	ECDA ecda = new ECDA();
        	ecda.setActive(cdm.getActive());
        	ecda.setId(cdm.getId());
        	ecda.setAddressLineOne(cdm.getAddressLineOne());
        	ecda.setAddressLineTwo(cdm.getAddressLineTwo());
        	ecda.setCity(cdm.getCity());
        	ecda.setState(cdm.getState());
        	ecda.setCompanyName(cdm.getCompanyName());
        	ecda.setContactNumber(cdm.getContactNumber());
        	ecda.setEmailId(cdm.getEmailId());
        	ecda.setCountry(cdm.getCountry());
        	ecda.setEmpCount(cdm.getCountOfEmp());
        	ecda.setVatNumber(cdm.getVatNumber());
        	ecda.setCompanyType(cdm.getCompanyType());
			ecdaList.add(ecda);	
        }
		eCDARes.setEcda(ecdaList);
        return eCDARes;
    }

}
