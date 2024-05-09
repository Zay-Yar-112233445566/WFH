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
@Table( name = "operation")
public class Operation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name = "issue_date", length = 50)
	private Date issueDate;
	
	
	@Column(name = "comment", length = 250)
	private String comment;
	
	@Column(name = "operation_name", length = 50)
	private String operationName;

	@Column(name = "status", length = 1)
	private int status;
	
	@Column(name = "notification_status", length = 1)
	private int notificationStatus;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "applicantForm_id")
	private ApplicantForm applicantForm;
	
    public static final String[] operations = {
            "formSubmit",//0
            "PMApprove",//1
            "departmentHeadApprove",//2
            "divisionHeadApprove",//3
            "CISOApprove",//4
            "serviceDeskApprove",//5
            "CEOApprove"//6
        };
}
