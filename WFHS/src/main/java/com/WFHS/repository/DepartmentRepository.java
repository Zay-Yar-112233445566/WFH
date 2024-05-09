package com.WFHS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WFHS.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{

	Department getById(int id);

	Optional<Department> findByName(String departmentName);

	List<Department> findByDivisionId(int divisionId);
	
	long count();
	
	int countByDivisionId(int divisionId);

	List<Department> findByDivisionName(String divisionName);

	Department findByCode(String code);
}
