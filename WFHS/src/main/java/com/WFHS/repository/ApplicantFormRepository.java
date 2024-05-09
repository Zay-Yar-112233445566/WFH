package com.WFHS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.WFHS.entity.ApplicantForm;

@Repository
public interface ApplicantFormRepository extends JpaRepository<ApplicantForm, Integer> {
	String retreiveManagerApproveForms = "SELECT DISTINCT af.* " + "FROM applicant_form af "
			+ "JOIN operation o ON af.id = o.applicant_form_id " + "JOIN user u ON o.user_id = u.id "
			+ "JOIN team t ON u.team_id = t.id " + "WHERE af.complete_status = 0 " 		
			+ "AND t.id = :teamId";

	String retreiveDepartmentHeadApproveForms = "SELECT DISTINCT af.* "
			+ "FROM applicant_form af "
			+ "JOIN operation o ON af.id = o.applicant_form_id "
			+ "JOIN user u ON o.user_id = u.id "
			+ "JOIN team t ON u.team_id = t.id "
			+ "JOIN department d ON t.department_id = d.id "
			+ "WHERE af.complete_status = 0 "
			+ "AND d.id = :departmentId";
	
	String retriveDivisionHeadApproveForms="SELECT DISTINCT af.* "
			+ "FROM applicant_form af "
			+ "JOIN operation o ON af.id = o.applicant_form_id "
			+ "JOIN user u ON o.user_id = u.id "
			+ "JOIN team t ON u.team_id = t.id "
			+ "JOIN department d ON t.department_id = d.id "
			+ "JOIN division dv ON d.division_id = dv.id "
			+ "WHERE af.complete_status = 0 "
			+ "AND dv.id = :divisionId";
	
	
	//String 
	
	String retreiveNotCompleteForms = "SELECT  af.* FROM applicant_form af WHERE af.complete_status = 0";
	
	String retreiveCompleteForms = "SELECT  af.* FROM applicant_form af WHERE af.complete_status = 1 ";
	
	String retreiveNGForms = "SELECT  af.* FROM applicant_form af WHERE af.complete_status = 2 ";
	
	@Query(value = retreiveManagerApproveForms, nativeQuery = true)
	List<ApplicantForm> findAllManagerApproveForms(@Param("teamId") int teamId);

	@Query(value = retreiveDepartmentHeadApproveForms, nativeQuery = true)
	List<ApplicantForm> findAllDepartmentHeadApproveForms(@Param("departmentId") int departmentId);
	
	@Query(value = retriveDivisionHeadApproveForms, nativeQuery = true)
	List<ApplicantForm> findAllDivisionHeadApproveForms(@Param("divisionId") int departmentId);
	
	@Query(value = retreiveNotCompleteForms, nativeQuery = true)
	List<ApplicantForm> findAllNotApproveForms();
	
	
	@Query(value = retreiveCompleteForms, nativeQuery = true)
	List<ApplicantForm> findAllApproveForms();
	
	@Query(value = retreiveNGForms, nativeQuery = true)
	List<ApplicantForm> findNGForms();
	
	ApplicantForm findById(int id);
	
	// Method to count ApplicantForm entities with completeStatus = 1
    int countByCompleteStatus(int completeStatus);
    
    List<ApplicantForm> findAllByCompleteStatus(int completeStatus);
}
