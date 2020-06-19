package com.employee.product.dao.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee.product.dao.interfaces.IEMasterServiceInterface;
import com.employee.product.entity.masterdetails.MasterUsers;
import com.employee.product.masterdetails.dto.EMasterSignReq;

@Service
public class EMasterService  {

    @Autowired
    IEMasterServiceInterface ieMasterServiceInterface;
    
    

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

}
