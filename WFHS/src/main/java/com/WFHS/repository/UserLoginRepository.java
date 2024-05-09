package com.WFHS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

import com.WFHS.entity.UserLogin;

@ResponseBody
public interface UserLoginRepository extends JpaRepository<UserLogin, Integer>{

	UserLogin findByEmail(String email);
	UserLogin findByStaffId(String staff_id);
	UserLogin findByPassword(String password);
	UserLogin findById(int id);
	public UserLogin findByResetPasswordToken(String token);
	boolean existsByEmail(String email);
	
}
