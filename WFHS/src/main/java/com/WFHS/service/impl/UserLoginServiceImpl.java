package com.WFHS.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.WFHS.entity.ApplicantForm;
import com.WFHS.entity.UserLogin;
import com.WFHS.repository.ApplicantFormRepository;
import com.WFHS.repository.UserLoginRepository;
import com.WFHS.service.ApplicantFormService;
import com.WFHS.service.UserLoginService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UserLoginServiceImpl implements UserLoginService {

	@Autowired
	private UserLoginRepository userLoginRepo;
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private ApplicantFormService formService;
	
	@Autowired
	private ApplicantFormRepository formRepo;

	@Override
	public UserLogin getByEmail(String email) {
		return userLoginRepo.findByEmail(email);
	}

	@Override
	public void generateOTP(String email) {
		UserLogin userLogin = userLoginRepo.findByEmail(email);
	     String otp = RandomStringUtils.randomNumeric(6);
	     userLogin.setResetPasswordToken(otp);
	     userLoginRepo.save(userLogin);
	      try {
			sendOTPEmail(userLogin, otp);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private void sendOTPEmail(UserLogin userLogin, String otp) throws MessagingException {
		 MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);

	        helper.setFrom("kk");
	        helper.setTo(userLogin.getEmail());
	        String subject = "Here's your One Time Password (OTP)";
	        String content = "<p>Hello " + userLogin.getStaffId() + "</p>"
	                + "<p>For security reason, you're required to use the following "
	                + "One Time Password to reset your password:</p>"
	                + "<p><b>" + otp + "</b></p>"
	                + "<br>"
	                + "<p>Note: this OTP is set to expire in 5 minutes.</p>";
	        helper.setSubject(subject);
	        helper.setText(content, true);
	        mailSender.send(message);
	}
	
	@Override
	public UserLogin saveOrUpdateUser(UserLogin userLogin) {
		return userLoginRepo.save(userLogin);
		
	}

	@Override
	public UserLogin getUserById(String staffId) {
		return userLoginRepo.findByStaffId(staffId);
	}

	@Override
	public List<UserLogin> getAll() {
		return userLoginRepo.findAll();
	}

	@Override
	public UserLogin getUserLoginById(int id) {
		return userLoginRepo.findById(id);
	}

	@Override
	public String processExcelAndSendOTP(MultipartFile file) {
        List<String[]> data = readDataFromExcel(file);
        StringBuilder message = new StringBuilder("Excel file uploaded and OTP codes sent successfully.");
        if (data != null && !data.isEmpty()) {
            for (String[] rowData : data) {
                if (rowData.length >= 2) {
                    String staffId = rowData[1]; // Staff ID assumed to be in the first column
                    String otpCode = rowData[2]; // OTP code assumed to be in the second column
                    UserLogin userLogin = userLoginRepo.findByStaffId(staffId);
                    
                    double formId = Double.parseDouble(rowData[3]); // Parse as a floating-point number
                    int roundedFormId = (int) Math.round(formId); // Round to the nearest integer
                    
                    ApplicantForm form=formService.findById(roundedFormId);
                    if (userLogin != null) {
                        try {
                            sendEmail(userLogin.getEmail(), otpCode);
                            form.setCompleteStatus(4);
                            formRepo.save(form);
                        } catch (Exception e) {
                            message.append("\nFailed to send OTP to ").append(staffId).append(". No email address found.");
                        }
                    } else {
                        message.append("\nStaff ID ").append(staffId).append(" not found.");
                    }
                }
            }
            //code here to check that there is no form to download with ceo approved status and  if it not have
            //iterate the form change form status to 4 to ensure that forms are already sent otp // don't change in the otp sending looping
            //and finally iterate adn send email to HR role possessing users
            
        }
        return message.toString();
    }
	
	private List<String[]> readDataFromExcel(MultipartFile file) {
        List<String[]> data = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            
            if (rowIterator.hasNext()) {
                rowIterator.next(); // Move to the second row
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.iterator();
                List<String> rowData = new ArrayList<>();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            rowData.add(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            rowData.add(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case BOOLEAN:
                            rowData.add(String.valueOf(cell.getBooleanCellValue()));
                            break;
                        default:
                            rowData.add("");
                            break;
                    }
                }
                data.add(rowData.toArray(new String[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
	
	private void sendEmail(String to, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        String subject = "Radius OTP of DAT OA VDI For March 2024";
        String content = "Dear All,\n\n"
                + "We would like to send Radius OTP as following of DAT OA VDI environment external access.\n\n"
                + "OTP Code: " +  String.valueOf(otpCode) + "\n\n"
                + "Please type this OTP for external login access of DAT OA VDI.\n\n"
                + "Please let us know if you have any issue.\n\n"
                + "Best Regards,\n"
                + "DAT Service Desk";
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

	@Override
	public boolean isEmailExists(String email) {
		return userLoginRepo.existsByEmail(email);
	}


}