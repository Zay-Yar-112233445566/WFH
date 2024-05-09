package com.WFHS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

import com.WFHS.entity.Position;

@ResponseBody
public interface PositionRepository extends JpaRepository<Position, Integer>{

	Optional<Position> findByName(String positionName);

	Position getByName(Position position);

	Position getPositionByName(String currentPosition);

}
