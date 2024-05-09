package com.WFHS.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LedgerDto {
	private int id;
	private String businessMonth;
	private String confirmedHRId;
	private Date hrConfirmDate;
	private int status;
}
