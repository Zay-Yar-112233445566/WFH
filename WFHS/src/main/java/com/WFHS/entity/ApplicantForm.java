package com.WFHS.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "applicant_form")
public class ApplicantForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name = "working_place", length = 100)
	private String workingPlace;
	
	@Column(name = "request_percentage", length = 3)
	private int requestPercentage;
	
	@Column(name = "request_reason", length = 250)
	private String requestReason;
	
	@Column(name = "from_date", length = 50)
	private Date fromDate;
	
	@Column(name = "to_date", length = 50)
	private Date toDate;
	
	@Column(name = "sign_date", length = 50)
	private Date signDate;
	
	@Column(name = "signature_img", length = 50)
	private String signatureImg;
	
	@Column(name = "applier_staff_id", length = 10)
	private String  applierStaffId;
	
	@Column(name = "complete_status", length = 1)
	private int completeStatus;
	
    @Column(name = "created_time_stamp")
    @CreationTimestamp
    private LocalDateTime createdTimestamp;

	
	@OneToMany(mappedBy = "applicantForm" , cascade =CascadeType.ALL)
	private List<Operation> operation;
	
	@OneToOne(mappedBy = "applicantForm")
	private Capture capture;	
}
