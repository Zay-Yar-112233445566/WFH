package com.WFHS.controller.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.WFHS.dto.CheckStaffIdRequest;
import com.WFHS.dto.CheckStaffIdResponse;
import com.WFHS.dto.DepartmentDto;
import com.WFHS.dto.DivisionDto;
import com.WFHS.dto.TeamDto;
import com.WFHS.dto.UserDto;
import com.WFHS.entity.Department;
import com.WFHS.entity.Division;
import com.WFHS.entity.Team;
import com.WFHS.entity.User;
import com.WFHS.repository.DepartmentRepository;
import com.WFHS.repository.TeamRepository;
import com.WFHS.repository.UserRepository;
import com.WFHS.service.DepartmentService;
import com.WFHS.service.DivisionService;
import com.WFHS.service.TeamService;
import com.WFHS.service.UserLoginService;
import com.WFHS.service.UserService;

@RestController
@RequestMapping("/api")
public class APIController {

	@Autowired
    private DepartmentService departmentService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private DivisionService divisionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private DepartmentRepository departmentRepo;
	
	@Autowired
	private TeamRepository teamRepo;
	
	@Autowired
	private UserLoginService userLoginService;
	
    @GetMapping("/getDepartmentsByDivision")
    public List<Department> getDepartmentsByDivision(@RequestParam int divisionId) {
        return departmentService.getDepartmentsByDivision(divisionId);
    }
    
    @GetMapping("/departmentName")
    public List<Department> getDepartmentsByDivisionName(@RequestParam("divisionName") String divisionName) {
        return departmentRepo.findByDivisionName(divisionName);
    }
    
    @GetMapping("/teams")
    public List<Team> getTeamsByDepartment(@RequestParam int departmentId) {
        return teamService.getTeamsByDepartment(departmentId);
    }
    
    @GetMapping("/teamName")
    public List<Team> getTeamsByDepartmentName(@RequestParam("departmentName") String departmentName) {
        return teamRepo.findByDepartmentName(departmentName);
    }
    
    @GetMapping("/userName")
    public List<User> getUsersByTeamName(@RequestParam("teamName") String teamName) {
        return userRepo.findByTeamName(teamName);
    }
    
    @GetMapping("/fetchUserData")
    public ResponseEntity<UserDto> fetchUserData(@RequestParam String staffId) {
    	 User user = userService.getUserById(staffId);
         if (user != null) {
             UserDto userDto = new UserDto();
             userDto.setName(user.getName());
             userDto.setDivisionName(user.getDivisionName());
             userDto.setDepartmentName(user.getDepartmentName());
             userDto.setTeamName(user.getTeamName());
             userDto.setCurrentPosition(user.getCurrentPosition());

             return ResponseEntity.ok(userDto);
         } else {
             return ResponseEntity.notFound().build();
         }
    }
    
    @PostMapping("/checkStaffId")
    public CheckStaffIdResponse checkStaffIdExist(@RequestBody CheckStaffIdRequest request) {
        boolean exists = userService.checkStaffIdExist(request.getStaffId());
        return new CheckStaffIdResponse(exists);
    }
    
    
    @PostMapping("/updateTeam")
    public ResponseEntity<?> updateTeam(@RequestBody TeamDto teamDto) {
    	System.out.println(" I am Here **********************");
    	System.out.println("Team name" + teamDto.getName());
    	System.out.println("Team code " + teamDto.getCode());
        try {
            // Validate the request
            if (teamDto.getCode() == null || teamDto.getName() == null) {
            	System.out.println(" I am now -------");
                return ResponseEntity.badRequest().body("Invalid request data");
            }

            // Check if the team exists
            Team existingTeam = teamService.findTeamByCode(teamDto.getCode());
            
            if (existingTeam == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found");
            }
            System.out.println("Existing Team : " + existingTeam.getName());

            // Update team data
            existingTeam.setName(teamDto.getName());
            teamService.updateTeam(existingTeam);

            // Return the updated team
            return ResponseEntity.ok(existingTeam);
        } catch (Exception e) {
        	System.out.println("Error is found");
            // Handle any exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating team data");
        }
    }
    
    
    @PostMapping("/updateDepartment")
    public ResponseEntity<?> updateDepartment(@RequestBody DepartmentDto departmentDto) {
    	System.out.println(" I am Here **********************");
    	System.out.println("Department name" + departmentDto.getName());
    	System.out.println("Department code " + departmentDto.getCode());
        try {
            // Validate the request
            if (departmentDto.getCode() == null || departmentDto.getName() == null) {
            	System.out.println(" I am now -------");
                return ResponseEntity.badRequest().body("Invalid request data");
            }

            // Check if the team exists
            Department existingDepartment = departmentService.findDepartmentByCode(departmentDto.getCode());
            
            if (existingDepartment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department not found");
            }
            System.out.println("Existing Department : " + existingDepartment.getName());

            // Update team data
            existingDepartment.setName(departmentDto.getName());
            departmentService.updateTeam(existingDepartment);

            // Return the updated team
            return ResponseEntity.ok(existingDepartment);
        } catch (Exception e) {
        	System.out.println("Error is found");
            // Handle any exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating department data");
        }
    }
    
    
    @PostMapping("/updateDivision")
    public ResponseEntity<?> updateDivision(@RequestBody DivisionDto divisionDto) {
    	System.out.println(" I am Here **********************");
    	System.out.println("Division name" + divisionDto.getName());
    	System.out.println("Division code " + divisionDto.getCode());
        try {
            // Validate the request
            if (divisionDto.getCode() == null || divisionDto.getName() == null) {
            	System.out.println(" I am now -------");
                return ResponseEntity.badRequest().body("Invalid request data");
            }

            // Check if the team exists
            Division existingDivision = divisionService.findDivisionByCode(divisionDto.getCode());
            
            if (existingDivision == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Department not found");
            }
            System.out.println("Existing Department : " + existingDivision.getName());

            // Update team data
            existingDivision.setName(divisionDto.getName());
            divisionService.updateDivision(existingDivision);

            // Return the updated team
            return ResponseEntity.ok(existingDivision);
        } catch (Exception e) {
        	System.out.println("Error is found");
            // Handle any exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating department data");
        }
    }
    
    @GetMapping("/check-email-exists")
    public Map<String, Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = userLoginService.isEmailExists(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return response;
    }


}
