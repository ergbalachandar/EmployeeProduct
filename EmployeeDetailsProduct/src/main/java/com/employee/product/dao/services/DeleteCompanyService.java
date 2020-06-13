package com.employee.product.dao.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.product.dao.interfaces.CompanyDeleteInterface;
import com.employee.product.entity.companydetails.CompanyDetails;

@Service
public class DeleteCompanyService {

	@Autowired
	private CompanyDeleteInterface companyDeleteInterface;

	@Transactional
	public void deleteCompany(String companyId) {

		Optional<CompanyDetails> companyDetails = companyDeleteInterface.findById(companyId);
		companyDetails.get().setActive(0);
	}

}
