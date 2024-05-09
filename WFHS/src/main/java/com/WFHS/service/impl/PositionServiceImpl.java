package com.WFHS.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WFHS.dto.PositionDto;
import com.WFHS.entity.Position;
import com.WFHS.repository.PositionRepository;
import com.WFHS.service.PositionService;

@Service
public class PositionServiceImpl implements PositionService{
	
	@Autowired
	private PositionRepository repo;

	@Override
	public void create(PositionDto positionDto) {
		Position position = new Position();
		position.setName(positionDto.getName());
		position.setAbbrevation(positionDto.getAbbrevation());
		
		repo.save(position);
	}

	@Override
	public List<Position> getAll() {
		return repo.findAll();
	}

	@Override
	public Position getById(Position position) {
		return repo.getByName(position);
	}

	@Override
	public Position getPositionByName(String currentPosition) {
		return repo.getPositionByName(currentPosition);
	}

}
