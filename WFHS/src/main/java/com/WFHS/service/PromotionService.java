package com.WFHS.service;

import org.springframework.stereotype.Service;

import com.WFHS.entity.Promotion;

@Service 
public interface PromotionService {

	void save(Promotion promotion);
}
