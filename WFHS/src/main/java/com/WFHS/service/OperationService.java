package com.WFHS.service;

import org.springframework.stereotype.Service;

import com.WFHS.entity.ApplicantForm;
import com.WFHS.entity.Operation;
import com.WFHS.entity.UserLogin;

@Service
public interface OperationService {
	void saveOperation(UserLogin userLogin, ApplicantForm form,String staffId);
	Operation findById(int id);
}
