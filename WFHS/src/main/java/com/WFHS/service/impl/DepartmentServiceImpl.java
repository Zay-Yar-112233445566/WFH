package com.WFHS.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WFHS.entity.Department;
import com.WFHS.repository.DepartmentRepository;
import com.WFHS.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository repo;
	
	@Override
	public Department findById(int id) {
		return repo.getById(id);
	}

	@Override
	public List<Department> getAll() {
		return repo.findAll();
	}

	@Override
	public List<Department> getDepartmentsByDivision(int divisionId) {
		return repo.findByDivisionId(divisionId);
	}

	@Override
	public Department getDepartmentById(int departmentId) {
		return repo.findById(departmentId).orElse(null);
	}

	@Override
	public Department addDepartment(Department department) {
		return repo.save(department);
		
	}

	@Override
	public String getDepartmentCodeById(int departmentId) {
		Optional<Department> departmentOptional = repo.findById(departmentId);

	    if (departmentOptional.isPresent()) {
	        Department department = departmentOptional.get();
	        return department.getCode();
	    } else {
	        // Handle the case when the department is not found
	        return ""; // or throw an exception or handle accordingly
	    }
	}

	@Override
	public void deleteById(int id) {
		repo.deleteById(id);
		
	}

	@Override
	public Department findDepartmentByCode(String code) {
		
		return repo.findByCode(code);
	}

	@Override
	public void updateTeam(Department existingDepartment) {
		repo.save(existingDepartment);
	}

}
