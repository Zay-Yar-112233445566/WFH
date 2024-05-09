package com.WFHS.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.WFHS.entity.User;
import com.WFHS.entity.UserLogin;
import com.WFHS.repository.UserLoginRepository;
import com.WFHS.repository.UserRepository;

@Controller
public class ProfileController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserLoginRepository userLoginRepo;
	
	@Autowired
	private UserRepository uerRepo;
	
	
	@GetMapping("/profile")
	public String profilePage(Model model,Authentication authentication,RedirectAttributes redirectAttributes) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			model.addAttribute("userMain",userLogin.getUser());
			if(userLogin.getEmail() == null) {
	            model.addAttribute("required", "Email is not exist!");
	        }else {
	        	model.addAttribute("emailExist",true);
	        }
			User user = userLogin.getUser();
			String userName = user.getName();
			System.out.println("Name : " + userName);
			model.addAttribute("user",user);
			return "profile";
		}else 
			return "redirect:/login";
	}
	
	@PostMapping("/email-submit")
	public String submitEmail(@RequestParam("email") String email,Authentication authentication) {
		System.out.println("Email Submit process!");
		 if (authentication != null && authentication.isAuthenticated()) {
			 UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			 
			 if(userLogin != null){
				 userLogin.setEmail(email);
				 User user=userLogin.getUser();
				 user.setEmail(email);
				 uerRepo.save(user);
				 userLoginRepo.save(userLogin);
				 System.out.println("Email Submit process successful");
			 }
		 }
		return "redirect:/profile";
	}
	
	 @PostMapping("/change-password")
	 public String changePassword(@RequestParam("currentPassword") String currentPassword,@RequestParam("newPassword") String newPassword,@RequestParam("confirmPassword") String confirmPassword,Authentication authentication,RedirectAttributes redirectAttributes) {
		 System.out.println("Change password process.");
			 if (authentication != null && authentication.isAuthenticated()) {
			 UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			
			 String staffId = userLogin.getStaffId();
			 System.out.println("Controller : " + staffId);
			 if(userLogin != null){
				 
				 if (passwordEncoder.matches(currentPassword, userLogin.getPassword())) {
					 if(newPassword.equals(confirmPassword)) {
						 userLogin.setPassword(passwordEncoder.encode(newPassword));
				            userLoginRepo.save(userLogin);
				            System.out.println("Password changed successfully");
				            redirectAttributes.addFlashAttribute("success", "Password changed successfully");
					 }else {
						 System.out.println("New Password and Confirm Password are not match!");
				    	  redirectAttributes.addFlashAttribute("fail", "Current password is incorrect");
					 }
					 
			      } else {
			    	  System.out.println("Current password is incorrect");
			    	  redirectAttributes.addFlashAttribute("error", "Current password is incorrect");
			        }
			 	}
			 }

		 return "redirect:/profile";
	 }
	
}