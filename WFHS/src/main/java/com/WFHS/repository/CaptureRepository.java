package com.WFHS.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.WFHS.entity.Capture;

@Repository
public interface CaptureRepository extends JpaRepository<Capture, Integer>{
	
	Capture findById(int id);
}
