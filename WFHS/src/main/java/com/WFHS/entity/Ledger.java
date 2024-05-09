package com.WFHS.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "ledger")
public class Ledger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name = "business_month", length = 25)
	private String businessMonth;
	
	@Column(name = "generate_date", length = 25)
	private String generateDate;
	
	
	@Column(name = "confirmed_HR_id", length = 15)
	private String confirmedHRId;
	
	@Column(name = "hr_confirm_date", length = 50)
	private String hrConfirmDate;
	
	
	@OneToMany(mappedBy = "ledger" , cascade =CascadeType.ALL)
	private List<LedgerDetail> ledgerDetail;
	
}
