package com.WFHS.controller.page;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.WFHS.dto.ApplicantFormDto;
import com.WFHS.entity.User;
import com.WFHS.entity.UserLogin;
import com.WFHS.service.ApplicantFormService;
import com.WFHS.service.UserService;

@Controller
@RequestMapping("/applied")
public class ApplyFormController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private ApplicantFormService formService;
	
	// Form applying method for current user
	@GetMapping("/forms/{userself}")
	public String appliedForm(@PathVariable("userself") int userself, Model model, Authentication authentication) {
		
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User users = userLogin.getUser();
			model.addAttribute("userMain", users);
			
			String currentRole = userLogin.getRole().getName();
			model.addAttribute("user", users);
			model.addAttribute("userself", userself);
			
			ApplicantFormDto dto=new ApplicantFormDto();
			LocalDate nextMonthStart = LocalDate.now().plusMonths(1).withDayOfMonth(1);
			Date nextMonthStartDate = Date.valueOf(nextMonthStart);
	        Date nextMonthEndDate = Date.valueOf(YearMonth.from(nextMonthStart).atEndOfMonth());
	        Date currentDate = Date.valueOf(LocalDate.now());
	        
	        dto.setFromDate(nextMonthStartDate);
	        dto.setToDate(nextMonthEndDate);
	        dto.setSignDate(currentDate);
	        model.addAttribute("applicantFormDto", dto);
			if (currentRole.equals("CEO") || currentRole.equals("CISO")) {
				model.addAttribute("userself", 1);
			} 
			if (currentRole.equals("PROJECT_MANAGER")) {
				// Applicant,PM,DepartmentHea,Division Head
				model.addAttribute("users",userService.findUsersWithSameUpperLevelDepartmentHead(userLogin));
				for(User user: userService.findUsersWithSameUpperLevelDepartmentHead(userLogin)) {
					System.out.println("Upper User: "+user.getStaffId());
				}
			}
			if (currentRole.equals("DEPARTMENT_HEAD")) {
				model.addAttribute("users", userService.findUsersWithSameUpperLevelDivisionHead(userLogin));
			}
			if (currentRole.equals("APPLICANT")) {
				model.addAttribute("users", userService.findSameLevelandManagerLevel(userLogin));
			}
			return "applied-form";
		} else {
			return "redirect:/login";
		}

	}
	
	//Form applying method for current user
	@PostMapping("/forms")
	public String apply(@ModelAttribute("applicantFormDto") ApplicantFormDto applicantFormDto, Model model,Authentication authentication) {
		if(authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			formService.applyForm(applicantFormDto,userLogin);
			return "redirect:/users";
		}else {
			return "redirect:/login";
		}
	}
}
