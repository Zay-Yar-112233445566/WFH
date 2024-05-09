package com.WFHS.service;

import org.springframework.stereotype.Service;
import com.WFHS.dto.CaptureDto;
import com.WFHS.entity.ApplicantForm;

@Service
public interface CaptureService {
	void saveCapture(CaptureDto captureDto,ApplicantForm form);
	void updateCapture(CaptureDto dto,int id);
}
