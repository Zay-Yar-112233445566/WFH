package com.WFHS.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WFHS.entity.Team;
import com.WFHS.repository.TeamRepository;
import com.WFHS.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService{
	
	@Autowired
	private TeamRepository repo;

	@Override
	public Team findById(int id) {
		return repo.getById(id);
	}

	@Override
	public List<Team> getAll() {
		return repo.findAll();
	}

	@Override
	public List<Team> getTeamsByDepartment(int departmentId) {
		return repo.findByDepartmentId(departmentId);
	}

	@Override
	public Team addTeam(Team team) {
		return repo.save(team);
	}

	@Override
	public void deleteById(int id) {
		repo.deleteById(id);
		
	}

	@Override
	public Team findTeamByCode(String code) {
		
		return repo.findByCode(code);
	}

	@Override
	public void updateTeam(Team existingTeam) {
		repo.save(existingTeam);
	}


}
