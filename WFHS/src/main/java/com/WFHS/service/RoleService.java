package com.WFHS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.WFHS.dto.RoleDto;
import com.WFHS.entity.Role;

import jakarta.validation.Valid;

@Service
public interface RoleService {

	List<Role> getAll();

	Role getRoleById(Role role);

	void create(@Valid RoleDto roleDto);

	Role getRoleByName(String roleName);
}
