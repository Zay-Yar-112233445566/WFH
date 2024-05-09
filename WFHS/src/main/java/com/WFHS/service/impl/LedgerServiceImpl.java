package com.WFHS.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.WFHS.entity.Ledger;
import com.WFHS.repository.LedgerRepository;
import com.WFHS.service.LedgerService;

@Service
public class LedgerServiceImpl implements LedgerService{

	@Autowired
	private LedgerRepository repo;
	
	@Override
	public Ledger saveLedger(Ledger ledger) {	
		return repo.save(ledger);
	}

	@Override
	public int getGeneratedLedgerForCurrentMonth(String currentDate) {
		return repo.countByGenerateDateMonth(currentDate);
	}

}
