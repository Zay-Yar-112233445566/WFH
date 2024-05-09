package com.WFHS.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaptureDto {

	private String osType;
	
	private MultipartFile windowOsTypeImg;
	
	private MultipartFile windowSecurityPatchUpdateImg;
	
	private MultipartFile windowAntivirusSoftwareImg;
	
	private MultipartFile antivirusPatternUpdatedImg;
	
	private MultipartFile antivirusFullScanImg;
	
	
	private MultipartFile macOsTypeImg;
	
	private MultipartFile macSecurityPatchUpdateImg;
	
	private MultipartFile macAntivirusSoftwareImg;
	
	
	private MultipartFile linuxOsTypeImg;
	
	private MultipartFile linuxSecurityPatchUpdateImg;
	
	private MultipartFile linuxAntivirusSoftwareImg;
}
