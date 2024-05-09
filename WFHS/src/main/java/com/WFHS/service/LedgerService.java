package com.WFHS.service;

import org.springframework.stereotype.Service;
import com.WFHS.entity.Ledger;

@Service
public interface LedgerService{
	Ledger saveLedger(Ledger ledger);
	int getGeneratedLedgerForCurrentMonth(String currentDate);
}
