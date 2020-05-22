package com.employee.product.utils;

import java.io.PrintWriter;
import java.util.List;

import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class GenerateCsvReportUtil {
	
	 public static void generateEmployeeDetails(PrintWriter writer,List<EmployeeDetails> employeeDetailsList, String companyName) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

	     

	            ColumnPositionMappingStrategy<EmployeeDetails> mapStrategy
	                    = new ColumnPositionMappingStrategy<>();
	            
	            mapStrategy.setType(EmployeeDetails.class);

	            String[] columns = new String[]{"id", "firstName", "lastName", "sex", "emailId", "country", "active"};
	            mapStrategy.setColumnMapping(columns);

	            StatefulBeanToCsv<EmployeeDetails> btcsv = new StatefulBeanToCsvBuilder<EmployeeDetails>(writer)
	                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
	                    .withMappingStrategy(mapStrategy)
	                    .withSeparator(',')
	                    .build();
	            
	           // ByteArrayOutputStream out = new ByteArrayOutputStream();

	            btcsv.write(employeeDetailsList);
	       

	        

}
}
