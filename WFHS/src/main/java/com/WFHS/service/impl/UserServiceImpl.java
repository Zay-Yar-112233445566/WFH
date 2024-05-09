package com.WFHS.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.WFHS.dto.UserDto;
import com.WFHS.entity.Role;
import com.WFHS.entity.Team;
import com.WFHS.entity.User;
import com.WFHS.entity.UserLogin;
import com.WFHS.repository.RoleRepository;
import com.WFHS.repository.TeamRepository;
import com.WFHS.repository.UserLoginRepository;
import com.WFHS.repository.UserRepository;
import com.WFHS.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private UserLoginRepository userLoginRepo;
	
	@Autowired
	private TeamRepository teamRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passEncoder;
	
	@Override
	public User findById(int id) {
		return repo.getById(id);
	}

	@Override
	public List<User> getAll() {
		return repo.findAll();
	}
	@Override
	public List<User> findAllByTeamId(int teamId) {
		return repo.findAllByTeamId(teamId);
	}

	@Override
	public User findByStaffId(String staffId) {
		return repo.findByStaffId(staffId);
	}

	@Override
	public User getUserById(String staffId) {
		return repo.findByStaffId(staffId);
	}

	@Override
	public User saveOrupdateUser(User existingUser) {
		return repo.save(existingUser);
	}

	@Override
	public User findByStaffId(User user) {
		return repo.getByStaffId(user);
	}

	@Override
	public List<User> getUsersByTeam(int teamId) {
		System.out.println(repo.findByTeamId(teamId));
		return repo.findByTeamId(teamId);
	}

	@Override
	public List<User> getUsersByTeamName(String teamName) {
		return repo.findByTeamName(teamName);
	}

	@Override
	public void registerUser(UserDto userDto) {
		User user = mapDtoToEntity(userDto);
        User savedUser = repo.save(user);

        UserLogin userLogin = new UserLogin();
        userLogin.setPassword(passEncoder.encode("user")); 
        userLogin.setStaffId(userDto.getStaffId());
        userLogin.setUser(savedUser);
        
        Role role = roleRepo.findByName(userDto.getRoleName());
        if (role != null) {
            userLogin.setRole(role);
        } 

        userLoginRepo.save(userLogin);
		
	}
	
	private User mapDtoToEntity(UserDto userDto) {
        User user = new User();
        user.setStaffId(userDto.getStaffId());
        user.setName(userDto.getName());
        user.setJoinDate(userDto.getJoinDate());
        user.setPermanentDate(userDto.getPermanentDate());
        user.setMaritalStatus(userDto.getMaritalStatus());
        user.setParent(userDto.getParent());
        user.setChildren(userDto.getChildren());
        user.setCurrentPosition(userDto.getCurrentPosition());
        user.setGender(userDto.getGender());
        user.setPhone(userDto.getPhone());
        user.setRoleName(userDto.getRoleName());
        System.out.println("Role : " + user.getRoleName());
        user.setDivisionName(userDto.getDivisionName());
        user.setDepartmentName(userDto.getDepartmentName());
        user.setTeamName(userDto.getTeamName());

        Optional<Team> teams = teamRepo.findByName(userDto.getTeamName());
        if (teams.isPresent()) {
            user.setTeam(teams.get());
        } else {
            // Handle error when team is not found
        }
        return user;
    }
	
	@Override
	public void updateUser(UserDto userDto) {
		User user = repo.findByStaffId(userDto.getStaffId());
		System.out.println("User Service : " + user.getDepartmentName());
        user.setDivisionName(userDto.getDivisionName());
        user.setDepartmentName(userDto.getDepartmentName());
        user.setTeamName(userDto.getTeamName());
        Optional<Team> teams = teamRepo.findByName(userDto.getTeamName());
        if (teams.isPresent()) {
            user.setTeam(teams.get());
        } 
        
        repo.save(user);
	}

	@Override
	public boolean checkStaffIdExist(String staffId) {
		return repo.existsByStaffId(staffId);
	}

	@Override
	public List<User> findUsersWithSameUpperLevelDepartmentHead(UserLogin userLogin) {
		String departmentName=userLogin.getUser().getDepartmentName();
		String upperRole="DEPARTMENT_HEAD";
		return repo.findUsersWithSameUpperLevelDepartmentHead(upperRole,departmentName);
	}

	@Override
	public List<User> findUsersWithSameUpperLevelDivisionHead(UserLogin userLogin) {
		String divisionName=userLogin.getUser().getDivisionName();
		String upperRole="DIVISION_HEAD";
		return repo.findUsersWithSameUpperLevelDivisionHead(upperRole,divisionName);
	}

	@Override
	public List<User> findSameLevelandManagerLevel(UserLogin userLogin) {
		String teamName=userLogin.getUser().getTeamName();
		String sameRole=userLogin.getUser().getRoleName();
		String upperRole="PROJECT_MANAGER";
		String staffId=userLogin.getStaffId();
		return repo.findSameLevelandManagerLevel(teamName,sameRole,upperRole,staffId);
	}

}
