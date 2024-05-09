package com.WFHS.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import com.WFHS.entity.ApplicantForm;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Component
@ApplicationScope
public class LedgerExcelGenerator {

	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ApplicantForm> applicantFormList;
    
    public LedgerExcelGenerator(List<ApplicantForm> applicantFormList) {
    	this.applicantFormList = applicantFormList;
    	workbook =new XSSFWorkbook();
    }
    
    private void writeHeaderLine() {
        sheet = workbook.createSheet("WFH Ledger");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.BOTTOM);
        style.setAlignment(HorizontalAlignment.LEFT);
        XSSFFont font = workbook.createFont();
        font.setFontHeight(12);
        font.setBold(true);
        style.setFont(font);

        createCell(row, 0, "No", style);
        createCell(row, 1, "Division",style);
        createCell(row, 2, "Department", style);
        createCell(row, 3, "Project/Team", style);
        createCell(row, 4, "User ID", style);
        createCell(row, 5, "Name", style);
        createCell(row, 6, "E-Mail", style);
        createCell(row, 7, "Applied Date", style);
        createCell(row, 8, "From Period", style);
        createCell(row, 9, "To Period", style);
        createCell(row, 10, "Workcation(Work from other countries)", style);
        createCell(row, 11, "WFH request Percentage", style);
        createCell(row, 12, "Use Own Facilities", style);
        createCell(row, 13, "If No,Computer?", style);
        createCell(row, 14, "If No,Monitor", style);
        createCell(row, 15, "If No,UPS", style);
        createCell(row, 16, "If No,Phone", style);
        createCell(row, 17, "If No,Other", style);
        createCell(row, 18, "Environment Facilities", style);
        createCell(row, 19, "Project Manager", style);
        createCell(row, 20, "Approve Date", style);
        createCell(row, 21, "Dept Head", style);
        createCell(row, 22, "Approve Date", style);
        createCell(row, 23, "Division Head", style);
        createCell(row, 24, "Approve Date", style);
        createCell(row, 25, "CISO", style);
        createCell(row, 26, "Approve Date", style);
        createCell(row, 27, "CEO", style);
        createCell(row, 28, "Final Approve Date", style);
        createCell(row, 29, "Signed pledge letter date", style);
        createCell(row, 30, "Reason for WFH ", style);
    }
    private void writeDataLines() {
        int rowCount = 1;
        int seqNo = 1;
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        style.setFont(font);

        for (ApplicantForm applicantForm : this.applicantFormList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, seqNo++, style);
			if (applicantForm.getOperation().get(0).getUser().getDivisionName() != null) {
				createCell(row, columnCount++, applicantForm.getOperation().get(0).getUser().getDivisionName(),
						style);
			} else {
				createCell(row, columnCount++, "", style);
			}
            if(applicantForm.getOperation().get(0).getUser().getDepartmentName() != null) {
            	 createCell(row, columnCount++, applicantForm.getOperation().get(0).getUser().getDepartmentName(), style);
            }else {
            	 createCell(row, columnCount++, "", style);
            }
           if( applicantForm.getOperation().get(0).getUser().getTeamName() != null) {
        	   createCell(row, columnCount++, applicantForm.getOperation().get(0).getUser().getTeamName(), style);
           }else {
        	   createCell(row, columnCount++, "", style);
           }
            createCell(row, columnCount++, applicantForm.getOperation().get(0).getUser().getStaffId(), style);
            createCell(row, columnCount++, applicantForm.getOperation().get(0).getUser().getName(), style);
            createCell(row, columnCount++, applicantForm.getOperation().get(0).getUser().getEmail(), style);
            createCell(row, columnCount++, applicantForm.getOperation().get(0).getIssueDate(), style);
            createCell(row, columnCount++, applicantForm.getFromDate(), style);
            createCell(row, columnCount++, applicantForm.getToDate(), style);
            createCell(row, columnCount++, applicantForm.getWorkingPlace(), style);
            createCell(row, columnCount++, applicantForm.getRequestPercentage(), style);
            createCell(row, columnCount++, "Yes", style);
            createCell(row, columnCount++, "", style);
            createCell(row, columnCount++, "", style);
            createCell(row, columnCount++, "", style);
            createCell(row, columnCount++, "", style);
            createCell(row, columnCount++, "", style);
            createCell(row, columnCount++, "OK", style);
            createCell(row, columnCount++, "Approve", style);
            createCell(row, columnCount++, applicantForm.getOperation().get(1).getIssueDate(), style);
            createCell(row, columnCount++, "Approve", style);
            createCell(row, columnCount++, applicantForm.getOperation().get(2).getIssueDate(), style);
            createCell(row, columnCount++, "Approve", style);
            createCell(row, columnCount++, applicantForm.getOperation().get(3).getIssueDate(), style);
            createCell(row, columnCount++, "Approve", style);
            createCell(row, columnCount++, applicantForm.getOperation().get(4).getIssueDate(), style);
            createCell(row, columnCount++, "Approve", style);
            createCell(row, columnCount++, applicantForm.getOperation().get(6).getIssueDate(), style);
            createCell(row, columnCount++, applicantForm.getSignDate(), style);
            createCell(row, columnCount++, applicantForm.getRequestReason(), style);
            
        }
        for(int i = 0 ; i < 8; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        Cell cell = row.createCell(columnCount);
        if(value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if(value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }   else if (value instanceof java.sql.Date) {
            java.sql.Date dateValue = (java.sql.Date) value;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Specify your desired date format
            cell.setCellValue(dateFormat.format(dateValue));
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    
//    public void export(HttpServletResponse response) throws IOException {
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        writeHeaderLine();
//        writeDataLines();
//        ServletOutputStream outputStream = response.getOutputStream();
//        workbook.write(outputStream);
//        workbook.close();
//        outputStream.close();
//    }
    
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        
        // Set the filename for the downloaded Excel file
        String fileName = "WFH_Ledger.xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        
        writeHeaderLine();
        writeDataLines();
        
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
