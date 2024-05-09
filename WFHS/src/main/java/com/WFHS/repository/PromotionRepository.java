package com.WFHS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

import com.WFHS.entity.Promotion;

@ResponseBody
public interface PromotionRepository extends JpaRepository<Promotion,Integer>{

}
