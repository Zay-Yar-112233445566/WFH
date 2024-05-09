package com.WFHS.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table( name = "user")
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "staff_id", length = 10)
	private String 	staffId;
	
	@Column(name = "name", length = 100)
	private String 	name;
	
	@Column(name = "join_date", length = 100)
	private String joinDate;
	
	@Column(name = "permanent_date", length = 100)
	private String permanentDate;
	
	@Column(name = "marital_status", length = 20)
	private String maritalStatus;
	
	@Column(name = "parent", length = 10)
	private String parent;
	
	@Column(name = "children", length = 10)
	private String children;
	
	@Column(name = "current_position", length = 100)
	private String currentPosition;
	
	@Column(name = "gender", length = 20)
	private String gender;
	
	@Column(name = "phone", length = 30)
	private String phone;
	
	@Column(name = "team_name", length = 100)
	private String teamName;
	
	@Column(name = "department_name", length = 100)
	private String departmentName;
	
	@Column(name = "division_name", length = 100)
	private String divisionName;
	
	@Column(name = "role_name", length = 30)
	private String roleName;
	
	@Column(name ="email", length = 30)
	private String email;
	
	@ManyToOne
	@JoinColumn(name = "team_id")
	@JsonIgnore
	private Team team;
	
	@OneToMany(mappedBy = "user" , cascade =CascadeType.ALL)
	private List<Promotion> promotion;
	
	@OneToMany(mappedBy = "user" , cascade =CascadeType.ALL)
	private List<Operation> operation;
}
