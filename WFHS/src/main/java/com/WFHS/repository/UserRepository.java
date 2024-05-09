package com.WFHS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.WFHS.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User getById(int id);
	User findByStaffId(String staffId);
	List<User> findAllByTeamId(int teamId);
	User getByStaffId(User user);
	
	List<User> findByTeamId(int teamId);

	List<User> findByTeamName(String teamName);
	
	int countByTeamId(int teamId);

	boolean existsByStaffId(String staffId);
	

	String retreiveUpperLevelDepartmentHead = "SELECT  u.* FROM user u WHERE u.department_name = :departmentName and u.role_name = :role  ";
	String retreiveUpperLevelDivisionHead = "SELECT  u.* FROM user u WHERE u.division_name = :divisionName and u.role_name = :role  ";
	String findSameLevelandManagerLevel ="SELECT u.* FROM user u WHERE u.team_name =:teamName and (u.role_name = :sameRole or u.role_name = :upperRole) and u.staff_id  <> :staffId";
	
	
	@Query(value = retreiveUpperLevelDepartmentHead, nativeQuery = true)
	List<User> findUsersWithSameUpperLevelDepartmentHead(@Param("role")String role, @Param("departmentName")String departmentName);
	
	@Query(value = retreiveUpperLevelDivisionHead, nativeQuery = true)
	List<User> findUsersWithSameUpperLevelDivisionHead(@Param("role")String role, @Param("divisionName")String divisionName);

	@Query(value = findSameLevelandManagerLevel, nativeQuery = true)
	List<User> findSameLevelandManagerLevel(@Param("teamName")String teamName,@Param("sameRole") String sameRole,@Param("upperRole") String upperRole,@Param("staffId")String staffId);
	
	
	
	
	
}
