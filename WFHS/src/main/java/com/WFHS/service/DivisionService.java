package com.WFHS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.WFHS.entity.Division;

@Service
public interface DivisionService {

	Division findById(int id);
	List<Division> getAll();
	Division getDivisionById(int divisionId);
	Division addDivision(Division division);
	String getDepartmentCodeById(int divisionId);
	String generateNewDivisionCode();
	Division findDivisionByCode(String code);
	void updateDivision(Division existingDivision);
}
