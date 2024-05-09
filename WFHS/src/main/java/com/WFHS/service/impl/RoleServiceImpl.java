package com.WFHS.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WFHS.dto.RoleDto;
import com.WFHS.entity.Role;
import com.WFHS.repository.RoleRepository;
import com.WFHS.service.RoleService;

import jakarta.validation.Valid;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepo;

	@Override
	public List<Role> getAll() {
		return roleRepo.findAll();
	}

//	@Override
//	public Role getRoleById(int id) {
//		return roleRepo.findById(id).orElse(null);
//	}

	@Override
	public void create(@Valid RoleDto roleDto) {
		Role role = new Role();
		role.setName(roleDto.getName());
		
		roleRepo.save(role);
	}

	@Override
	public Role getRoleById(Role role) {
		return roleRepo.findById(role);
	}

	@Override
	public Role getRoleByName(String roleName) {
		return roleRepo.findByName(roleName);
	}

}
