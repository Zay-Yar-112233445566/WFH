package com.WFHS.controller.page;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.WFHS.entity.ApplicantForm;
import com.WFHS.entity.Department;
import com.WFHS.entity.Division;
import com.WFHS.entity.Role;
import com.WFHS.entity.Team;
import com.WFHS.entity.User;
import com.WFHS.entity.UserLogin;
import com.WFHS.repository.DepartmentRepository;
import com.WFHS.repository.DivisionRepository;
import com.WFHS.repository.TeamRepository;
import com.WFHS.repository.UserLoginRepository;
import com.WFHS.repository.UserRepository;
import com.WFHS.service.ApplicantFormService;
import com.WFHS.service.UserLoginService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	@Autowired
	private UserLoginRepository userLoginRepo;
	
	@Autowired
	private UserLoginService userLoginService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ApplicantFormService formService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TeamRepository teamRepo;
	
	@Autowired
	private DepartmentRepository departmentRepo;
	
	@Autowired
	private DivisionRepository divisionRepo;

	
	
	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}
	
	@GetMapping("/forgot-password")
	public String forgotPassword(Model model) {
		return "forgot-password";
	}
	
	@PostMapping("/forgot-password")
	public String forgot(@RequestParam("email") String email) {
		if (email == null || email.isEmpty()) {
            return "redirect:/forgot-password?error=empty_email";
        }
		
        if (!userLoginService.isEmailExists(email)) {
            return "redirect:/forgot-password?error=email_not_found";
        }
		userLoginService.generateOTP(email);
		return "reset-password";
	}
	
	@PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String email,
        @RequestParam("otp") String otp,
        @RequestParam("password") String password) {
        UserLogin userLogin = userLoginService.getByEmail(email);
        System.out.println(userLogin.getResetPasswordToken());
        

        if (userLogin != null && userLogin.getResetPasswordToken().equals(otp)) {

            // set new password and clear reset password token
        	String encodedPassword = passwordEncoder.encode(password);
            userLogin.setPassword(encodedPassword);
        	userLogin.setResetPasswordToken(null);
        	userLoginRepo.save(userLogin);
        } else {
            System.out.println("Not successful.");
        }
        return "login";

    }
	
	@GetMapping("/users")
	public String showSignUpForm(Model model,Authentication authentication) {
		
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			Role userRole = userLogin.getRole();
			User user = userLogin.getUser();
			Department department = null;
			Division division = null;
			Team team = null;
			System.out.println(userLogin.getStaffId());
			ApplicantForm pendingForm=formService.getPendingApplyForm(userLogin);
			ApplicantForm completeForm=formService.getCompleteApplyForm(userLogin);
			List<ApplicantForm> approveForms=formService.findApproveForms(user);
			ApplicantForm NGForm=formService.getNGForm(userLogin);
			model.addAttribute("pendingForm",pendingForm);
			model.addAttribute("completeForm",completeForm);
			model.addAttribute("NGForm",NGForm );
			model.addAttribute("approveForms",approveForms);
			
			System.out.println("Pending form .id "+pendingForm.getId());
			
			if (!userLogin.getRole().getName().equals("CEO")) {
				team = user.getTeam();
				department = team.getDepartment();
				division = department.getDivision();
			}
			String role = userRole.getName();
			model.addAttribute("userMain", user);
			if (userRole != null && role.equals("APPLICANT")) {	
				return "applicant-home";

			} else if (userRole != null && role.equals("PROJECT_MANAGER")) {
				model.addAttribute("members", userRepo.countByTeamId(team.getId()));
				return "pm-home";

			} else if (userRole != null && role.equals("DEPARTMENT_HEAD")) {
				model.addAttribute("totalTeams", teamRepo.countByDepartmentId(department.getId()));
				return "department-head-home";

			} else if (userRole != null && role.equals("DIVISION_HEAD")) {
				model.addAttribute("totalDepartments", departmentRepo.countByDivisionId(division.getId()));
				return "division-head-home";

			} else if (userRole != null && role.equals("CISO")) {
				model.addAttribute("teams", teamRepo.count());
				model.addAttribute("departments", departmentRepo.count());
				model.addAttribute("divisions", divisionRepo.count());
				return "ciso-home";

			} else if (userRole != null && role.equals("SERVICE_DESK")) {
				List<ApplicantForm> forms = formService.findCompleteForm();				
				model.addAttribute("forms",forms);
				return "service-desk-home";
			} else if (userRole != null && role.equals("CEO")) {

				model.addAttribute("teams", teamRepo.count());
				model.addAttribute("departments", departmentRepo.count());
				model.addAttribute("divisions", divisionRepo.count());
				return "ceo-home";

			} else if (userRole != null && role.equals("HR")) { // if (userRole != null &&
																// userRole.getName().equals("HR"))
				model.addAttribute("teams", teamRepo.count());
				model.addAttribute("departments", departmentRepo.count());
				model.addAttribute("divisions", divisionRepo.count());
				List<ApplicantForm> forms = formService.findOTPSentForms();		
				model.addAttribute("forms",forms);
				return "hr-home";
			}
		}
		return "redirect:/login";		
	}


}