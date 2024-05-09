package com.WFHS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WFHS.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer>{

	Team getById(int id);

	Optional<Team> findByName(String teamName);


	List<Team> findByDepartmentId(int departmentId);
	
	long count();
	
	int countByDepartmentId(int departmentId);

	List<Team> findByDepartmentName(String departmentName);
	
	Team findByCode(String teamCode);
}
