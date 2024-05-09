package com.WFHS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.WFHS.entity.Team;

@Service
public interface TeamService {

	Team findById(int id);
	List<Team> getAll();
	List<Team> getTeamsByDepartment(int departmentId);
	Team addTeam(Team team);
	void deleteById(int id);
	Team findTeamByCode(String code);
	void updateTeam(Team existingTeam);
}
