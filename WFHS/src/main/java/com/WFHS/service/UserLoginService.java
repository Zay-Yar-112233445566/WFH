package com.WFHS.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.WFHS.entity.UserLogin;

@Service
public interface UserLoginService {
	UserLogin getByEmail(String email);
	void generateOTP(String email);
	UserLogin saveOrUpdateUser(UserLogin userLogin);
	UserLogin getUserById(String staffId);
	List<UserLogin> getAll();
	UserLogin getUserLoginById(int id);
	String processExcelAndSendOTP(MultipartFile file);
	boolean isEmailExists(String email);
}
