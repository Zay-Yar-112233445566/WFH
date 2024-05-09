package com.WFHS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.WFHS.dto.ApplicantFormDto;
import com.WFHS.dto.OperationDto;
import com.WFHS.entity.ApplicantForm;
import com.WFHS.entity.User;
import com.WFHS.entity.UserLogin;

import jakarta.servlet.http.HttpServletResponse;

@Service
public interface ApplicantFormService {
	ApplicantForm save(ApplicantFormDto dto,UserLogin userLogin);
	ApplicantForm findById(int id);
	void applyForm(ApplicantFormDto dto,UserLogin userLogin);
	List<ApplicantForm> findApproveForms(User user);
	void approveForm(OperationDto dto,UserLogin userLogin);
	ApplicantForm getNGForm(UserLogin userlogin);
	void modifyForm(ApplicantFormDto dto);
	List<ApplicantForm> findCompleteForm();
	void exportStaffIdsForOTP(List<Integer>  formId,HttpServletResponse response);
	int getIncompleteFormCount(int completeStatus);
	void exportLedgerExcel(List<Integer> formId,HttpServletResponse response,UserLogin userLogin);
	List<ApplicantForm> findOTPSentForms();
	ApplicantForm getPendingApplyForm(UserLogin userLogin);
	ApplicantForm getCompleteApplyForm(UserLogin userLogin);
	void deleteAllForm();
	List<ApplicantForm> getNgForms(UserLogin userLogin);
}
