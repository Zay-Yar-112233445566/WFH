package com.WFHS.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table( name = "ledger_detail")
public class LedgerDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name = "department", length = 200)
	private String department;
	
	@Column(name = "division", length = 200)
	private String division;
	
	@Column(name = "team", length = 200)
	private String team;
	
	@Column(name = "staff_id", length = 15)
	private String staffId;
	
	@Column(name = "name", length = 100)
	private String name;
	
	@Column(name = "email", length = 60)
	private String email;
	
	@Column(name = "applied_date", length = 50)
	private Date appliedDate;
	
	@Column(name = "from_period", length = 50)
	private Date fromPeriod;
	
	@Column(name = "to_period", length = 50)
	private Date toPeriod;
	
	@Column(name = "workcation", length = 100)
	private String workcation;
	
	@Column(name = "request_percentage", length = 3)
	private int requestPercentage;
	
	@Column(name = "use_own_facilities", length = 3)
	private String useOwnFacilities;
	
	@Column(name = "if_no_computer", length = 3)
	private String ifNoComputer;
	
	@Column(name = "if_no_monitor", length = 3)
	private String ifNoMonitor;
	
	@Column(name = "if_no_UPS", length = 3)
	private String ifNoUPS;
	
	@Column(name = "if_no_phone", length = 3)
	private String ifNoPhone;
	
	@Column(name = "if_no_other", length = 3)
	private String ifNoOther;
	
	@Column(name = "environment_facilities", length = 2)
	private String environmentFacilities;
	
	@Column(name = "pm_status", length = 15)
	private String pmStatus;
	
	@Column(name = "pm_approve_date", length = 50)
	private Date pmApproveDate;
	
	@Column(name = "dept_head_status", length = 15)
	private String deptHeadStatus;
	
	@Column(name = "dept_head_approve_date", length = 50)
	private Date deptHeadApproveDate;
	
	@Column(name = "divi_head_status", length = 15)
	private String diviHeadStatus;
	
	@Column(name = "divi_head_approve_date", length = 50)
	private Date diviHeadApproveDate;
	
	@Column(name = "ciso_status", length = 15)
	private String cisoStatus;
	
	@Column(name = "ciso_approve_date", length = 50)
	private Date cisoApproveDate;
	
	@Column(name = "ceo_approve_date", length = 50)
	private Date ceoApproveDate;
	
	@Column(name = "reason_for_WFH", length = 250)
	private String reasonForWFH;
	
	@ManyToOne
	@JoinColumn(name = "ledger_id")
	private Ledger ledger;
	
}

