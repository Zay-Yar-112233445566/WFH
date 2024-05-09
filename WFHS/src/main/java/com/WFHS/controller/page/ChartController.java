package com.WFHS.controller.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.WFHS.repository.LedgerDetailRepository;

@RestController
public class ChartController {
	
	@Autowired
	private LedgerDetailRepository ledgerRepo;
	
	@GetMapping("/update-chart-team")
    public Map<String, Integer> getTop3Teams() {
        List<Object[]> teamCounts = ledgerRepo.findTopTeamsWithCounts(); // Assuming you have a method to find top 3 teams in your repository

        Map<String, Integer> top3Teams = new HashMap<>();
        for (Object[] row : teamCounts) {
            String teamName = (String) row[0];
            Long count = (Long) row[1];
            top3Teams.put(teamName, count.intValue());
        }

        return top3Teams;
    }
	
	@GetMapping("/update-chart-division")
	public Map<String, Integer> getTop3Divisions() {
	    List<Object[]> divisionCounts = ledgerRepo.findTopDivisionsWithCounts();

	    Map<String, Integer> top3Divisions = new HashMap<>();
	    for (Object[] row : divisionCounts) {
	        String divisionName = (String) row[0];
	        Long count = (Long) row[1];
	        top3Divisions.put(divisionName, count.intValue());
	    }

	    return top3Divisions;
	}
	
	@GetMapping("/update-chart-department")
	public Map<String, Integer> getTop3Departments() {
	    List<Object[]> departmentCounts = ledgerRepo.findTopDepartmentsWithCounts();

	    Map<String, Integer> top3Departments = new HashMap<>();
	    for (Object[] row : departmentCounts) {
	        String departmentName = (String) row[0];
	        Long count = (Long) row[1];
	        top3Departments.put(departmentName, count.intValue());
	    }

	    return top3Departments;
	}
}
