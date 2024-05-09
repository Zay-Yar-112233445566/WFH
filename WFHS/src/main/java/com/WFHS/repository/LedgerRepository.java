package com.WFHS.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.WFHS.entity.Ledger;

public interface LedgerRepository extends JpaRepository<Ledger, Integer> {
	
	String retriveSameMonthLedger=" SELECT count(*) from ledger l where month(l.generate_date) = MONTH(:currentDate)";
	@Query(value = retriveSameMonthLedger, nativeQuery = true)
    int countByGenerateDateMonth(@Param("currentDate") String currentDate);
	
}
