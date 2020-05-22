package com.employee.product.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.employee.product.entity.employeedetails.EmployeeDetails;

public class GenerateExcelReportUtil {
	public static ByteArrayInputStream employeeReport(List<EmployeeDetails> employeeDetailsList, String companyName)
			throws IOException {

		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Employee Report");
		sheet.setDefaultColumnWidth(30);

		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		font.setBold(true);
		font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setAlignment(HorizontalAlignment.CENTER);

		Row title = sheet.createRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		title.createCell(0);
		title.getCell(0).setCellValue(companyName);
		title.getCell(0).setCellStyle(style);

		// create header row
		Row header = sheet.createRow(1);
		header.createCell(0).setCellValue("Id");
		header.getCell(0).setCellStyle(style);
		header.createCell(1).setCellValue("Firstname");
		header.getCell(1).setCellStyle(style);
		header.createCell(2).setCellValue("LastName");
		header.getCell(2).setCellStyle(style);
		header.createCell(3).setCellValue("Gender");
		header.getCell(3).setCellStyle(style);
		header.createCell(4).setCellValue("EmailId");
		header.getCell(4).setCellStyle(style);
		header.createCell(5).setCellValue("Country");
		header.getCell(5).setCellStyle(style);
		header.createCell(6).setCellValue("Active");
		header.getCell(6).setCellStyle(style);

		int rowCount = 2;
		CellStyle styleForData = workbook.createCellStyle();
		styleForData.setVerticalAlignment(VerticalAlignment.CENTER);
		styleForData.setAlignment(HorizontalAlignment.CENTER);

		Collections.sort(employeeDetailsList, GeneratePdfReportUtil.employeeDetailsComparator);
		for (EmployeeDetails employeeDetails : employeeDetailsList) {
			Row userRow = sheet.createRow(rowCount++);
			userRow.createCell(0).setCellValue(employeeDetails.getId());
			userRow.getCell(0).setCellStyle(styleForData);
			userRow.createCell(1).setCellValue(employeeDetails.getFirstName());
			userRow.getCell(1).setCellStyle(styleForData);
			userRow.createCell(2).setCellValue(employeeDetails.getLastName());
			userRow.getCell(2).setCellStyle(styleForData);
			userRow.createCell(3).setCellValue(employeeDetails.getSex());
			userRow.getCell(3).setCellStyle(styleForData);
			userRow.createCell(4).setCellValue(employeeDetails.getEmailId());
			userRow.getCell(4).setCellStyle(styleForData);
			userRow.createCell(5).setCellValue(employeeDetails.getCountry());
			userRow.getCell(5).setCellStyle(styleForData);
			if (employeeDetails.getActive() == 0) {
				userRow.createCell(6).setCellValue("Deleted");
				userRow.getCell(6).setCellStyle(styleForData);
			} else if (employeeDetails.getActive() == 1) {
				userRow.createCell(6).setCellValue("Active");
				userRow.getCell(6).setCellStyle(styleForData);
			}

		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		out.flush();
		out.close();

		return new ByteArrayInputStream(out.toByteArray());

	}

}
