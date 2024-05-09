package com.WFHS.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.WFHS.dto.PositionDto;
import com.WFHS.dto.UserDto;
import com.WFHS.entity.Department;
import com.WFHS.entity.Division;
import com.WFHS.entity.Position;
import com.WFHS.entity.Promotion;
import com.WFHS.entity.Role;
import com.WFHS.entity.Team;
import com.WFHS.entity.User;
import com.WFHS.entity.UserLogin;
import com.WFHS.service.DepartmentService;
import com.WFHS.service.DivisionService;
import com.WFHS.service.PositionService;
import com.WFHS.service.PromotionService;
import com.WFHS.service.RoleService;
import com.WFHS.service.TeamService;
import com.WFHS.service.UserLoginService;
import com.WFHS.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("")
public class HRController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserLoginService userLoginService;
	
	@Autowired
	private DivisionService divisionService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private PromotionService promotionService;

	@GetMapping("/positions")
	public String positionManagement(Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User user = userLogin.getUser();
			model.addAttribute("userMain", user);
			PositionDto positionDto = new PositionDto();
			model.addAttribute("positionDto", positionDto);
			return "position-management";
		}
		else 
			return "redirect:/login";
	}
	
	@PostMapping("/position-management")
	public String createPosition(@Valid  @ModelAttribute("positionDto")  PositionDto positionDto, BindingResult result,RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("error", "Position Assign warning");
	        return "redirect:/positions?error";
	    } else {
	    	positionService.create(positionDto);
	    	redirectAttributes.addFlashAttribute("success", "Position Add successfully");
	        return "redirect:/positions";
	    }
	}
	
	@GetMapping("/promotions")
	public String promotionManagement(Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User user = userLogin.getUser();
			model.addAttribute("userMain", user);
			if (!model.containsAttribute("user")) {
                model.addAttribute("user", new User());
            }
			model.addAttribute("divisionList", divisionService.getAll());
			model.addAttribute("departmentList", departmentService.getAll());
			model.addAttribute("teamList",teamService.getAll());
			model.addAttribute("staffIdList",userService.getAll());
			model.addAttribute("positionList",positionService.getAll());
			model.addAttribute("promotion", new Promotion());
			return "promotion-management";
		}
		else 
			return "redirect:/login";
	}
	
	@PostMapping("/promotion-management")
	public String createPromotion(@Valid  @ModelAttribute("user")  User updatePosition,@ModelAttribute("promotion") Promotion promotionDto, BindingResult result,RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("error", "Promotion Assign warning");
			redirectAttributes.addFlashAttribute("user", updatePosition);
	        return "redirect:/promotions?error";
	    }
		
		System.out.println("Position " + updatePosition.getCurrentPosition());
		System.out.println("Staff Id " + updatePosition.getStaffId());
		
		if (updatePosition.getCurrentPosition() == null || updatePosition.getStaffId() == null) {
	        return "redirect:/promotions?error=positionOrUserIdNotSelected";
	    }
	
		User existingUser = userService.getUserById(updatePosition.getStaffId());
	    
	    if (existingUser == null) {
	        return "redirect:/promotions?error=userNotFound";
	    }
	    
	    Position selectedPosition = positionService.getPositionByName(updatePosition.getCurrentPosition());

	    if (selectedPosition == null) {
	        return "redirect:/promotions?error=roleNotFound";
	    }
	    
	    Promotion promotion = new Promotion();
	    promotion.setPosition(selectedPosition);
	    promotion.setUser(existingUser);
	    promotion.setPromoteStartDate(promotionDto.getPromoteStartDate());
	    promotionService.save(promotion);
	    existingUser.setCurrentPosition(selectedPosition.getName());
	    userService.saveOrupdateUser(existingUser);
	    redirectAttributes.addFlashAttribute("success", "Promotion Assign successfully");

	    return "redirect:/promotions";

	}
	
	@GetMapping("/applicants")
	public String applicantManagement(Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User user = userLogin.getUser();
			model.addAttribute("userMain", user);
			if (!model.containsAttribute("userDto")) {
                model.addAttribute("userDto", new UserDto());
            }
			model.addAttribute("division", new Division());
			model.addAttribute("divisionList", divisionService.getAll());
			model.addAttribute("department", new Department());
			model.addAttribute("departmentList", departmentService.getAll());
			model.addAttribute("team", new Team());
			model.addAttribute("teamList",teamService.getAll());
			model.addAttribute("role", new Role());
			model.addAttribute("roleList", roleService.getAll());
			model.addAttribute("position", new Position());
			model.addAttribute("positionList", positionService.getAll());
			return "applicant-register";
		}
		else 
			return "redirect:/login";	
	}
	
	@PostMapping("/applicant-register")
	public String addApplicant(@Valid @ModelAttribute("userDto") UserDto userDto,BindingResult result,RedirectAttributes redirectAttributes,Model model) {
		if (result.hasErrors()) {
			System.out.println("Applicant Register :   " + userDto.getName());
			redirectAttributes.addFlashAttribute("error", "Applicant register warning");
			redirectAttributes.addFlashAttribute("userDto", userDto);
	        return "redirect:/applicants?error";
	    }
		 
		 userService.registerUser(userDto);
		 redirectAttributes.addFlashAttribute("success", "Applicant Register successfully");
	     return "redirect:/applicants";
	}
	
	@GetMapping("/assign-roles")
	public String roleAssign(Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User user = userLogin.getUser();
			model.addAttribute("userMain", user);
			model.addAttribute("user", new User());
			model.addAttribute("userList", userService.getAll());
			model.addAttribute("divisionList", divisionService.getAll());
			model.addAttribute("departmentList", departmentService.getAll());
			model.addAttribute("teamList",teamService.getAll());
			model.addAttribute("staffIdList",userLoginService.getAll());
			model.addAttribute("roleList", roleService.getAll());
		
			return "assign-role";
		}
		else
			return "redirect:/login";
	}
	
	@PostMapping("/assign-roles")
	public String saveRoleAssign(@Valid @ModelAttribute("user") User updatedUser,BindingResult bindingResult,RedirectAttributes redirectAttributes) {
		
	    if (bindingResult.hasErrors()) {
	    	redirectAttributes.addFlashAttribute("error", "Assign role warning");
	        return "redirect:/assign-roles?error=validationError";
	    }
	    
	    if (updatedUser.getRoleName() == null || updatedUser.getStaffId() == null) {
	        return "redirect:/assign-roles?error=roleOrStaffIdNotSelected";
	    }
	    System.out.println("Selected Role Name : " + updatedUser.getRoleName());
	    User existingUser = userService.getUserById(updatedUser.getStaffId());
	    
	    if (existingUser == null) {
	    	redirectAttributes.addFlashAttribute("error", "Assign role warning");
	        return "redirect:/assign-roles?error=userNotFound";
	    }
	    
	    Role selectedRole = roleService.getRoleByName(updatedUser.getRoleName());

	    if (selectedRole == null) {
	    	redirectAttributes.addFlashAttribute("error", "Assign role warning");
	        return "redirect:/assign-roles?error=roleNotFound";
	    }
	    
	    UserLogin userLoginUpdate = userLoginService.getUserLoginById(existingUser.getId());
	    userLoginUpdate.setRole(selectedRole);
	    userLoginService.saveOrUpdateUser(userLoginUpdate);
	    
	    existingUser.setRoleName(selectedRole.getName());
	    userService.saveOrupdateUser(existingUser);
	    redirectAttributes.addFlashAttribute("success", "Role Assign successfully");

	    return "redirect:/assign-roles";
		
	}
	
	@GetMapping("/edit")
	public String showEditPage(Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User user = userLogin.getUser();
			model.addAttribute("userMain", user);
			model.addAttribute("userDto",new UserDto());
			model.addAttribute("userList", userService.getAll());
			model.addAttribute("divisionList", divisionService.getAll());
			model.addAttribute("departmentList", departmentService.getAll());
			model.addAttribute("teamList",teamService.getAll());
			model.addAttribute("roleList", roleService.getAll());
			model.addAttribute("positionList", positionService.getAll());
			return "edit-applicant";
		}
		else 
			return "redirect:/login";
	}
	
	@PostMapping("/edit-applicant")
	public String editApplicant(@Valid @ModelAttribute("userDto") UserDto userDto,BindingResult bindingResult,RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
	    	redirectAttributes.addFlashAttribute("error", "Edit applicant warning");
	        return "redirect:/edit?error=validationError";
	    }
		
		User existingUser = userService.getUserById(userDto.getStaffId());
		
		if (existingUser == null) {
	    	redirectAttributes.addFlashAttribute("error", "Edit applicant warning");
	        return "redirect:/edit?error=userNotFound";
	    }

		userService.updateUser(userDto);
		redirectAttributes.addFlashAttribute("success", "Edit Applicant's information successfully");
		
		return "redirect:/edit";
	}
	
}
