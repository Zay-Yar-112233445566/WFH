package com.WFHS.entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table( name = "capture")
public class Capture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(name = "os_type", length = 15)
	private String osType;
	
	
	@Column(name = "os_type_img", length = 200)
	private String osTypeImg;
	
	
	@Column(name = "security_patch_update_img", length = 200)
	private String securityPatchUpdateImg;
	
	@Column(name = "antivirus_software_img", length = 200)
	private String antivirusSoftwareImg;
	
	@Column(name = "antivirus_pattern_updated_img", length =200)
	private String antivirusPatternUpdatedImg;
	
	@Column(name = "antivirus_full_scan_img", length = 200)
	private String antivirusFullScanImg;
	
	@OneToOne(cascade =CascadeType.ALL)
	@JoinColumn(name = "applicantForm_id",referencedColumnName = "id")
	private ApplicantForm applicantForm;
}
