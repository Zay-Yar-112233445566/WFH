package com.WFHS.service;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import org.apache.poi.xssf.usermodel.XSSFFont;
import com.WFHS.entity.ApplicantForm;
import jakarta.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import jakarta.servlet.http.HttpServletResponse;

@Component
@ApplicationScope
public class OTPStaffIDExcelGenerator {
	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ApplicantForm> applicantFormList;
    
    public OTPStaffIDExcelGenerator(List<ApplicantForm> applicantFormList){
    	this.applicantFormList = applicantFormList;
    	workbook =new XSSFWorkbook();
    }
    
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Staff ID for OTP Sending Process");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        font.setBold(true);
        style.setFont(font);

        createCell(row, 0, "No", style);
        createCell(row, 1, "Staff ID", style);
        createCell(row, 2, "OTP Code", style);
        createCell(row, 3, "Form ID", style);
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
            createCell(row, columnCount++, applicantForm.getOperation().get(0).getUser().getStaffId(), style);
            createCell(row, columnCount++, " ", style);
            createCell(row, columnCount++, applicantForm.getId(), style);
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
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
//    public void export(HttpServletResponse response) throws IOException {
//        writeHeaderLine();
//        writeDataLines();
//        ServletOutputStream outputStream = response.getOutputStream();
//        workbook.write(outputStream);
//        workbook.close();
//        outputStream.close();
//    }
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
        String fileName = "OTP_SEND_STAFF_IDS.xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        
        writeHeaderLine();
        writeDataLines();
        
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
