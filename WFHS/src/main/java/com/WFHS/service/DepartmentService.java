package com.WFHS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.WFHS.entity.Department;

@Service
public interface DepartmentService {

	Department findById(int id);
	List<Department> getAll();
	List<Department> getDepartmentsByDivision(int divisionId);
	Department getDepartmentById(int departmentId);
	Department addDepartment(Department department);
	String getDepartmentCodeById(int departmentId);
	void deleteById(int id);
	Department findDepartmentByCode(String code);
	void updateTeam(Department existingDepartment);
}
