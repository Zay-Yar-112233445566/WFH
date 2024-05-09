package com.WFHS.controller.page;

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
import com.WFHS.entity.ApplicantForm;
import com.WFHS.entity.UserLogin;
import com.WFHS.service.ApplicantFormService;

@Controller
@RequestMapping("/applicants")
public class ApplicantController {
	
	@Autowired
	private ApplicantFormService formService;

	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("pending",1);
		return "applicant-home";
	}
	
	@GetMapping("/modify/{id}")
	public String modifyForm(@PathVariable("id") int id, Model model, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			model.addAttribute("userMain", userLogin.getUser());
			
			ApplicantForm form = formService.findById(id);
			ApplicantFormDto dto=new ApplicantFormDto();
			
			dto.setId(form.getId());
			dto.setWorkingPlace(form.getWorkingPlace());
			dto.setRequestPercentage(form.getRequestPercentage());
			dto.setRequestReason(form.getRequestReason());
			model.addAttribute("form", form);
			model.addAttribute("dto",dto);
			
			return "modify-form";
		} else {
			return "redirect:/login";
		}
	}

	
	@PostMapping("/modify/form")
	public String modifyForm(@ModelAttribute("dto") ApplicantFormDto dto) {
		formService.modifyForm(dto);
		return "redirect:/users";
	}
}
