package com.WFHS.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.WFHS.entity.Role;
import com.WFHS.entity.User;
import com.WFHS.entity.UserLogin;
import com.WFHS.repository.UserLoginRepository;
import com.WFHS.repository.UserRepository;

@Service
public class OurUserDetailService implements UserDetailsService {

	@Autowired
	private UserLoginRepository userLoginRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	
	@Override
    public UserDetails loadUserByUsername(String staff_id) throws UsernameNotFoundException {

        System.out.println("Loading user details for: " + staff_id);

        UserLogin userLogin = userLoginRepo.findByStaffId(staff_id);
        if (userLogin == null) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = userRepo.findByStaffId(staff_id);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        Role userRole = userLogin.getRole();

        System.out.println("User details loaded successfully");
        System.out.println(userRole.getName());
        System.out.println("User staff-id: " + user.getStaffId());
        System.out.println("User email: " + userLogin.getEmail());
        System.out.println("User roles: " + userRole.getName());

        return userLogin;
    }

}
