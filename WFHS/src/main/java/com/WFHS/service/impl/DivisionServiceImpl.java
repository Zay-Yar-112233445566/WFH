package com.WFHS.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.WFHS.entity.Division;
import com.WFHS.repository.DivisionRepository;
import com.WFHS.service.DivisionService;

@Service
public class DivisionServiceImpl implements DivisionService{
	
	@Autowired
	private DivisionRepository divisionRepo;

	@Override
	public Division findById(int id) {
		return divisionRepo.getById(id);
	}

	@Override
	public List<Division> getAll() {
		return divisionRepo.findAll();
	}

	@Override
	public Division getDivisionById(int divisionId) {
		return divisionRepo.findById(divisionId).orElse(null);
	}

	@Override
	public Division addDivision(Division division) {
		return divisionRepo.save(division);
	}

	@Override
	public String getDepartmentCodeById(int divisionId) {
		Optional<Division> divisionOptional = divisionRepo.findById(divisionId);

	    if (divisionOptional.isPresent()) {
	    	Division division = divisionOptional.get();
	        return division.getCode();
	    } else {
	        // Handle the case when the department is not found
	        return ""; // or throw an exception or handle accordingly
	    }
	}

	@Override
	public String generateNewDivisionCode() {
		long divisionCount = divisionRepo.count();
        long newCodeNumber = divisionCount + 1;

        // Format the code with leading zeros
        String newCode = String.format("%03d-%03d", 0, newCodeNumber);

        return newCode;
	}

	@Override
	public Division findDivisionByCode(String code) {
		return divisionRepo.findByCode(code)
	            .orElse(null); 
	}

	@Override
	public void updateDivision(Division existingDivision) {
		divisionRepo.save(existingDivision);
		
	}
}
