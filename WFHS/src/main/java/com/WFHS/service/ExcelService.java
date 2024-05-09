package com.WFHS.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.WFHS.entity.Department;
import com.WFHS.entity.Division;
import com.WFHS.entity.Position;
import com.WFHS.entity.Role;
import com.WFHS.entity.Team;
import com.WFHS.entity.User;
import com.WFHS.entity.UserLogin;
import com.WFHS.repository.DepartmentRepository;
import com.WFHS.repository.DivisionRepository;
import com.WFHS.repository.PositionRepository;
import com.WFHS.repository.RoleRepository;
import com.WFHS.repository.TeamRepository;
import com.WFHS.repository.UserLoginRepository;
import com.WFHS.repository.UserRepository;
import java.text.ParseException;

@Service
public class ExcelService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserLoginRepository userLoginRepo;
	
	@Autowired
	private DivisionRepository divisionRepo;
	
	@Autowired
	private DepartmentRepository departmentRepo;
	
	@Autowired
	private TeamRepository teamRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PositionRepository positionRepo;
	
	DataFormatter formatter = new DataFormatter();
	
	public String uploadFile(MultipartFile file) throws ParseException {
        try (InputStream inputStream = file.getInputStream()) {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                if (i == 3 || i == 4) {
                    processSheet(sheet, i);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
	
	private void processSheet(XSSFSheet sheet, int sheetIndex) throws ParseException {

            switch (sheetIndex) {
                case 3:{
	                	for (Row row : sheet) {
	                        if (row.getRowNum() == 0) { // header row
	                            continue;
	                        }else if(row.getRowNum()>=1 && row.getRowNum()<=4) {
	                        	processDivisionSheet(row);
	                        }else if(row.getRowNum()>=7 && row.getRowNum()<=18) {
	                        	processDepartmentSheet(row);
	                        }else if(row.getRowNum()>=21 && row.getRowNum()<=67) {
	                        	processTeamSheet(row);
	                        }else 
	                        	continue;
	                	}
	                }
                    break;
                    
                case 4:{
	                	for (Row row : sheet) {
	                        if (row.getRowNum() == 0 || row.getRowNum() ==1 || row.getRowNum() ==2) { // header row
	                            continue;
	                        }else if(row.getRowNum()>=3 && row.getRowNum()<=161)
	                        processUserSheet(row);
	                	}
	                }             	
                    break;
                    
                default:
                    // Handle other sheets if needed
                    break;
            }
    }
	
	private void processDivisionSheet(Row row) {
        Division division = new Division();
        division.setCode(formatter.formatCellValue(row.getCell(0)));
        division.setName(formatter.formatCellValue(row.getCell(1)));
        divisionRepo.save(division);
    }
	
	private void processDepartmentSheet(Row row) {
	    Department department = new Department();
	    department.setCode(formatter.formatCellValue(row.getCell(0)));
	    department.setName(formatter.formatCellValue(row.getCell(1)));

	    // Assuming the division name is in the second column
	    String divisionName = formatter.formatCellValue(row.getCell(2));
	    Optional<Division> optionalDivision = divisionRepo.findByName(divisionName);

	    optionalDivision.ifPresent(div -> {
	        department.setDivision(div);
	        departmentRepo.save(department); // Save the department with the division information
	    });
	}
	
	private void processTeamSheet(Row row) {
        Team team = new Team();
        team.setCode(formatter.formatCellValue(row.getCell(0)));
        team.setName(formatter.formatCellValue(row.getCell(1)));

        // Assuming the department name is in the second column
        String departmentName = formatter.formatCellValue(row.getCell(2));
        Optional<Department> optionalDepartment = departmentRepo.findByName(departmentName);

        optionalDepartment.ifPresent(dep -> {
	        team.setDepartment(dep);
	        teamRepo.save(team); // Save the department with the division information
	    });
    }
	
	private void processUserSheet(Row row) throws ParseException {
		User user = new User();
	    user.setStaffId(formatter.formatCellValue(row.getCell(5)));
	    user.setName(formatter.formatCellValue(row.getCell(6)));
	    
	    user.setJoinDate(formatter.formatCellValue(row.getCell(13)));
	    user.setPermanentDate(formatter.formatCellValue(row.getCell(14)));
	    
	    user.setMaritalStatus(formatter.formatCellValue(row.getCell(10)));
	    user.setParent(formatter.formatCellValue(row.getCell(11)));
	    user.setChildren(formatter.formatCellValue(row.getCell(12)));
	    user.setCurrentPosition(formatter.formatCellValue(row.getCell(8)));
	    user.setGender(formatter.formatCellValue(row.getCell(7)));
	    user.setPhone(formatter.formatCellValue(row.getCell(15)));
	    user.setTeamName(formatter.formatCellValue(row.getCell(4)));
	    user.setDepartmentName(formatter.formatCellValue(row.getCell(3)));
	    
	    String divisionCode = formatter.formatCellValue(row.getCell(2));
	    Optional<Division> optionalDivision = divisionRepo.findByCode(divisionCode);
	    
	    optionalDivision.ifPresent(division -> {
	        user.setDivisionName(division.getName()); // Set the division name
	    });

	    // Assign role based on whether it's the first person or not
	    if (userRepo.count() == 0) {
	        user.setRoleName("HR");
	    } else {
	        user.setRoleName("APPLICANT");
	    }
	    
	    // Create or get the team
	    String teamName = formatter.formatCellValue(row.getCell(4));
	    Optional<Team> optionalTeam = teamRepo.findByName(teamName);
	    //optionalTeam.ifPresent(user::setTeam)

	    // Save the user
	    optionalTeam.ifPresent(team -> {
	        user.setTeam(team);
	    });
	    userRepo.save(user); // Save the user with the team information
	    
	    
	    // Create a UserLogin entity and set the email and encoded password
	    UserLogin userLogin = new UserLogin();
	    userLogin.setPassword(passwordEncoder.encode("user")); // Assuming you have a password encoder bean
	    userLogin.setStaffId(user.getStaffId());  // Set staffId from the saved user

	    //Create or get the position
	    String positionName = user.getCurrentPosition();
	    String positionAbbrevation = formatter.formatCellValue(row.getCell(9)); // Get the abbrevation from cell 8
	    Position position = positionRepo.findByName(positionName).orElseGet(() -> {
	    	Position newPosition = new Position();
	    	newPosition.setName(positionName);
	    	newPosition.setAbbrevation(positionAbbrevation); // Set the abbrevation
	    	positionRepo.save(newPosition);
	        return newPosition;
	    });
	    
	    // Create or get the role
	    String roleName = user.getRoleName();
	    Role role = roleRepo.findAllByName(roleName).orElseGet(() -> {
	        Role newRole = new Role();
	        newRole.setName(roleName);
	        roleRepo.save(newRole);
	        return newRole;
	    });

	    // Set the role for the UserLogin
	    userLogin.setRole(role);

	    // Associate the User and UserLogin entities
	    userLogin.setUser(user);

	    // Save the UserLogin
	    userLoginRepo.save(userLogin);
	}
	
	
	
}
