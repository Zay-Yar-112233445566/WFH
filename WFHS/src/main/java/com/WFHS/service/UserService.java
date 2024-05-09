package com.WFHS.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.WFHS.dto.UserDto;
import com.WFHS.entity.User;
import com.WFHS.entity.UserLogin;

@Service
public interface UserService {
	User findById(int id);
	List<User>findAllByTeamId(int teamId);
	User findByStaffId(String staffId);
	List<User> getAll();
	User getUserById(String staffId);
	User saveOrupdateUser(User existingUser);
	User findByStaffId(User user);
	List<User> getUsersByTeam(int teamId);
	List<User> getUsersByTeamName(String teamName);
	void registerUser(UserDto userDto);
	void updateUser(UserDto userDto);
	boolean checkStaffIdExist(String staffId);
	List<User> findUsersWithSameUpperLevelDepartmentHead(UserLogin userLogin);
	List<User> findUsersWithSameUpperLevelDivisionHead(UserLogin userLogin);
	List<User> findSameLevelandManagerLevel(UserLogin userLogin);
}
