package com.employee.product.entity.employeedetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "passport_document_details")
public class EmployeePassportDocumentDetails {
	
	@Id
	@Column(name = "passport_number")
	private String passportNumber;
	
	@OneToOne (fetch = FetchType.LAZY)
    @MapsId // This is used to map the column employee id as both PK and FK
    @JoinColumn(name = "passport_number")
	private EmployeePassportDetails passport_number;
	
	@Column(name = "document_name")
	private String documentName;
	
	@Lob
	@Column(name = "document_data")
    private byte[] documentData;

}
