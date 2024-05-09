package com.WFHS.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WFHS.entity.Promotion;
import com.WFHS.repository.PromotionRepository;
import com.WFHS.service.PromotionService;

@Service 
public class PromotionServiceImpl implements PromotionService {
	
	@Autowired
	private PromotionRepository repo;

	@Override
	public void save(Promotion promotion) {
		repo.save(promotion);
		
	}


}
