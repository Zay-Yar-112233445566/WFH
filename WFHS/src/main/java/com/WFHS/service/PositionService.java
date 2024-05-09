package com.WFHS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.WFHS.dto.PositionDto;
import com.WFHS.entity.Position;

@Service 
public interface PositionService {

	void create(PositionDto positionDto);

	List<Position> getAll();

	Position getById(Position position);

	Position getPositionByName(String currentPosition);
}
