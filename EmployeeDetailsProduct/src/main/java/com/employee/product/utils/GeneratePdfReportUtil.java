package com.employee.product.utils;

import com.employee.product.entity.employeedetails.EmployeeDetails;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneratePdfReportUtil {

	public static ByteArrayInputStream employeeReport(List<EmployeeDetails> employeeDetailsList, String companyName) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {

			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 4, 4, 4, 4, 4, 4, 4 });
			table.setHeaderRows(1);

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			Paragraph header = new Paragraph(new Chunk(companyName, FontFactory.getFont(FontFactory.HELVETICA, 30)));
			header.setAlignment(Element.ALIGN_CENTER);
			header.setSpacingAfter(30);
			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("Id", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("FirstName", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("LastName", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Gender", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("EmailId", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Country", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Active", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			Collections.sort(employeeDetailsList, employeeDetailsComparator);

			for (EmployeeDetails employeeDetails : employeeDetailsList) {

				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(employeeDetails.getId())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(employeeDetails.getFirstName()));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(employeeDetails.getLastName()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(employeeDetails.getSex())));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(employeeDetails.getEmailId()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(employeeDetails.getCountry()));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				if (employeeDetails.getActive() == 0) {
					cell = new PdfPCell(new Phrase("Deleted"));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				} else if (employeeDetails.getActive() == 1) {
					cell = new PdfPCell(new Phrase("Active"));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}
			}

			PdfWriter.getInstance(document, out);
			document.open();
			document.add(header);
			document.add(table);

			document.close();

		}

		catch (DocumentException ex) {

			Logger.getLogger(GeneratePdfReportUtil.class.getName()).log(Level.SEVERE, null, ex);
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	public static Comparator<EmployeeDetails> employeeDetailsComparator = new Comparator<EmployeeDetails>() {

		public int compare(EmployeeDetails e1, EmployeeDetails e2) {
			int activeStatus1 = e1.getActive();
			int activeStatus2 = e2.getActive();

			// ascending order
			return activeStatus2 - activeStatus1;

		}

	};

}
