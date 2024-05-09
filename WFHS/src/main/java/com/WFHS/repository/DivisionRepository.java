package com.WFHS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WFHS.entity.Division;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Integer>{

	Division getById(int id);
	Optional<Division> getByName(String name);
	Optional<Division> findByName(String divisionName);
	Optional<Division> findByCode(String divisionCode);
	long count();
}
