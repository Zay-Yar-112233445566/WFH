package com.WFHS.dto;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantFormDto {

	int id;
	
	@NotEmpty(message = "The name is required")
	private String workingPlace;
	
	@Size(min=25,max=100, message = "Request Percentage should between  25% to 100%")
	private int requestPercentage;
	
	@Size(min=1,max=2550, message = "Request reason should between  1 to 255 characters.")
	private String requestReason;
	
	private Date fromDate;
	
	private Date toDate;
	
	private Date signDate;
	
	private MultipartFile signatureImg;
	
	@Pattern(regexp = "^(26|25)-\\d{5}$", message = "The applierStaffId should  the pattern 26-XXXXX or 25-XXXXX")
	private String  applierStaffId;
	
	private int completeStatus;
	
	
	private CaptureDto captureDto;
	
}
