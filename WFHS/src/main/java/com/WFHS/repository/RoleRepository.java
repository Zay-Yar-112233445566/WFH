package com.WFHS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

import com.WFHS.entity.Role;

@ResponseBody
public interface RoleRepository extends JpaRepository<Role, Integer>{

	Optional<Role> findAllByName(String roleName);

	Role findById(Role role);
	
	Role getById(int id);
	
	Role findByName(String name);
}

