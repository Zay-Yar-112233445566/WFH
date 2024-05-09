package com.WFHS.dto;

import com.WFHS.entity.Team;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private int id;
	
	@NotBlank(message = "Staff ID is required")
	private String staffId;
	
	@NotBlank(message = "Name is required")
	private String name;
	
	@NotBlank(message = "Join Date is required")
	private String joinDate;
	
	@NotBlank(message = "Permanent Date is required")
	private String permanentDate;
	private String maritalStatus;
	private String parent;
	private String children;
	
	@NotBlank(message = "Current Position is required")
	private String currentPosition;
	private String gender;
	private String phone;
	
	@NotBlank(message = "Team name is required")
	private String teamName;
	
	@NotBlank(message = "Department Name is required")
	private String departmentName;
	
	@NotBlank(message = "Division Name is required")
	private String divisionName;
	
	@NotBlank(message = "Role Name is required")
	private String roleName;
	private Team team;
}
