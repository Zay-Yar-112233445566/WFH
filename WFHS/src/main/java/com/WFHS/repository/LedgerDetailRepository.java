package com.WFHS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.WFHS.entity.LedgerDetail;

public interface LedgerDetailRepository extends JpaRepository<LedgerDetail, Integer>{

	@Query("SELECT ld.team, COUNT(ld) " +
	           "FROM LedgerDetail ld " +
	           "GROUP BY ld.team " +
	           "ORDER BY COUNT(ld) DESC")
	    List<Object[]> findTopTeamsWithCounts();
	     
	@Query("SELECT ld.division, COUNT(ld) " +
		       "FROM LedgerDetail ld " +
		       "GROUP BY ld.division " +
		       "ORDER BY COUNT(ld) DESC")
		List<Object[]> findTopDivisionsWithCounts();
		
	@Query("SELECT ld.department, COUNT(ld) " +
			   "FROM LedgerDetail ld " +
			   "GROUP BY ld.department " +
			   "ORDER BY COUNT(ld) DESC")
		List<Object[]> findTopDepartmentsWithCounts();
}
