package com.employee.product.utils;

import com.employee.product.dao.services.DocumentManagementService;
import com.employee.product.documentdetails.request.dto.UploadDocumentDetailsRequestDto;

public class DownloadDocumentUtil {

	public static <T> Object downloadDocument(UploadDocumentDetailsRequestDto uploadDocumentDetailsRequestDto,
			DocumentManagementService documentManagementService) {
		
	return documentManagementService.downloadDocument(uploadDocumentDetailsRequestDto.getDocumentNumber(), uploadDocumentDetailsRequestDto.getDocumentType());
	
}

}
