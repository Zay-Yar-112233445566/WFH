package com.WFHS.controller.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.WFHS.dto.DepartmentDto;
import com.WFHS.dto.DivisionDto;
import com.WFHS.dto.TeamDto;
import com.WFHS.entity.ApplicantForm;
import com.WFHS.entity.Department;
import com.WFHS.entity.Division;
import com.WFHS.entity.Team;
import com.WFHS.entity.User;
import com.WFHS.entity.UserLogin;
import com.WFHS.repository.DepartmentRepository;
import com.WFHS.repository.TeamRepository;
import com.WFHS.service.ApplicantFormService;
import com.WFHS.service.DepartmentService;
import com.WFHS.service.DivisionService;
import com.WFHS.service.TeamService;
import com.WFHS.service.UserService;



@Controller
@RequestMapping("")
public class HomeController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ApplicantFormService formService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private DivisionService divisionService;
	
	@Autowired
	private TeamRepository teamRepo;
	
	@Autowired
	private DepartmentRepository departmentRepo;
	
	@GetMapping("/approved-forms")
	public String formApprove(Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User users = userLogin.getUser();
			model.addAttribute("userMain", users);
			return "form-approve";
		}
		else 
			return "redirect:/login";
	}

	
	@GetMapping("/ledgers")
	public String ledger(Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User user = userLogin.getUser();
			model.addAttribute("userMain", user);
			List<ApplicantForm> forms = formService.findOTPSentForms();
			List<Integer> Ids = new ArrayList<>();
			for (ApplicantForm form : forms) {
			    Ids.add(form.getId());
			}
			model.addAttribute("forms",forms);
			model.addAttribute("Ids",Ids);
			return "ledger";
		}
		else 
			return "redirect:/login";
	}
	
//	
//	@GetMapping("/form-histories")
//	public String history() {
//		return "history-form";
//	}
	
	@GetMapping("/form-histories")
	public String history(Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User user = userLogin.getUser();
			model.addAttribute("userMain", user);
			List<ApplicantForm> ngForms=formService.getNgForms(userLogin);
			model.addAttribute("ngForms",ngForms);
 			return "history-form";
		}
		else 
			return "redirect:/login";
	}
	
	
	@GetMapping("/applied-form-list")
	public String appliedFormList(Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User users = userLogin.getUser();
			model.addAttribute("userMain", users);
			return "applied-form-list";
		}
		else 
			return "redirect:/login";
	}
	
	

	//View From Team's Id
	@GetMapping("/team-member-list-id")
	public String teamMemberListId(@RequestParam int id, Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User users = userLogin.getUser();
			model.addAttribute("userMain", users);
			List<User> user = userService.getUsersByTeam(id);
			model.addAttribute("users", user);
			return "team-member-list";
		}
		else 
			return "redirect:/login";
	}
	
	//View From Project Manager's Id
	@GetMapping("/team-member-list")
	public String teamMemberList(Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User user = userLogin.getUser();
			model.addAttribute("userMain", user);
			System.out.println(user.getTeamName());
			List<User> users = userService.getUsersByTeamName(user.getTeamName());
			model.addAttribute("users", users);
			return "team-member-list";
		}
		else 
			return "redirect:/login";
	}
	
	//View From All
	@GetMapping("/team-list-all")
	public String teamListAll(Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User user = userLogin.getUser();
			model.addAttribute("userMain", user);
			model.addAttribute("division", new Division());
			model.addAttribute("divisionList", divisionService.getAll());
			model.addAttribute("department",new Department());
			
			model.addAttribute("departmentList", departmentService.getAll());
			model.addAttribute("team", new Team());
			List<Team> teams = teamService.getAll();
			model.addAttribute("teams", teams);
			return "team-list";
		}
		else 
			return "redirect:/login";
	}
	
	
	//for team code
	@GetMapping("/get-next-team-code/{departmentId}")
	@ResponseBody
	public String getNextTeamCode(@PathVariable int departmentId) {
		int teamCount = teamRepo.countByDepartmentId(departmentId) + 1;
		System.out.println("Teams " + teamCount);
	    return generateTeamCode(departmentId, teamCount);
	}
	
	private String generateTeamCode(int departmentId, int teamCount) {
	    String departmentCode = departmentService.getDepartmentCodeById(departmentId);
	    System.out.println("Department Code : " + departmentCode);
	    return String.format("%s-%04d", departmentCode, teamCount);
	}
	
	
	
	@PostMapping("/add-team")
	@ResponseBody
    public String addTeam(@RequestBody TeamDto teamDto) {
        Team team = new Team();
        team.setCode(teamDto.getCode());
        team.setName(teamDto.getName());

        Department department = departmentService.getDepartmentById(teamDto.getDepartmentId());

        if (department == null) {
            return "Error: Department not found";
        }

        team.setDepartment(department);

        teamService.addTeam(team);

        return "Team added successfully";
    }
	
	//View From Department_Head's Id
	@GetMapping("/team-list")
	public String teamList(Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User user = userLogin.getUser();
			model.addAttribute("userMain", user);
			Team team = user.getTeam();
			Department department = team.getDepartment();		
			System.out.println("Department Id : " + department.getName());
			List<Team> teams = teamService.getTeamsByDepartment(department.getId());
			model.addAttribute("teams", teams);
			return "team-list";
		}
		else 
			return "redirect:/login";
	}
	
	//View From Department's Id
	@GetMapping("/team-list-id")
	public String teamListId(@RequestParam int id, Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User user = userLogin.getUser();
			model.addAttribute("userMain", user);
			List<Team> teams = teamService.getTeamsByDepartment(id);
			System.out.println("Team List Id." + id);
			model.addAttribute("teams", teams);
			return "team-list";
		}
		else 
			return "redirect:/login";
	}
	
	//View From CEO,HR
	@GetMapping("/department-list-all")
	public String departmentListAll(Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User user = userLogin.getUser();
			model.addAttribute("userMain", user);
			model.addAttribute("division",new Division());
			model.addAttribute("divisionList", divisionService.getAll());
			model.addAttribute("department", new Department());
			List<Department> departments = departmentService.getAll();
			model.addAttribute("departments", departments);
			
			return "department-list";
		}
		else 
			return "redirect:/login";
	}
	
	//for department code
	@GetMapping("/get-next-department-code/{divisionId}")
	@ResponseBody
	public String getNextDepartmentCode(@PathVariable int divisionId) {
		int departmentCount = departmentRepo.countByDivisionId(divisionId) + 1;
		System.out.println("Department " + departmentCount);
	    return generateDepartmentCode(divisionId, departmentCount);
	}
	
	private String generateDepartmentCode(int divisionId, int departmentCount) {
	    String divisionCode = divisionService.getDepartmentCodeById(divisionId);
	    System.out.println("Department Code : " + divisionCode);
	    return String.format("%s-%03d", divisionCode, departmentCount);
	}	
	
	@PostMapping("/add-department")
	@ResponseBody
    public String addDepartment(@RequestBody DepartmentDto departmentDto) {
        Department department = new Department();
        department.setCode(departmentDto.getCode());
        department.setName(departmentDto.getName());
        Division division = divisionService.getDivisionById(departmentDto.getDivisionId());

        if (division == null) {
            return "Error: Division not found";
        }

        department.setDivision(division);

        departmentService.addDepartment(department);

        return "Department added successfully";
    }
	
	//View From Division_Head's Id
		@GetMapping("/department-list")
		public String departmentList(Model model,Authentication authentication) {
			if (authentication != null && authentication.isAuthenticated()) {
				UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
				User user = userLogin.getUser();
				model.addAttribute("userMain", user);
				Team team = user.getTeam();
				Department department = team.getDepartment();
				Division division = department.getDivision();
				System.out.println("Division Id : " + division.getName());
				List<Department> departments = departmentService.getDepartmentsByDivision(division.getId());
				model.addAttribute("departments", departments);
				return "department-list";
			}
			else 
				return "redirect:/login";
		}
	
	//View From Division's Id
	@GetMapping("/department-list-id")
	public String departmentListId(@RequestParam int id, Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User user = userLogin.getUser();
			model.addAttribute("userMain", user);
			List<Department> departments = departmentService.getDepartmentsByDivision(id);
			model.addAttribute("departments", departments);
			return "department-list";
		}
		else 
			return "redirect:/login";
	}
	
	//View From CEO,HR
	@GetMapping("/division-list")
	public String divisionList(Model model,Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			User user = userLogin.getUser();
			model.addAttribute("userMain", user);
			model.addAttribute("division", new Division());
			List<Division> divisions = divisionService.getAll();
			model.addAttribute("divisions", divisions);
			return "division-list";
		}
		else
			return "redirect:/login";
	}
	
	@GetMapping("/getNewDivisionCode")
    @ResponseBody
    public String getNewDivisionCode() {
        return divisionService.generateNewDivisionCode();
    }
	
	@PostMapping("/add-division")
	@ResponseBody
    public String addDivision(@RequestBody DivisionDto divisionDto) {
        // Map the TeamRequest to a Team entity
        Division division = new Division();
        division.setCode(divisionDto.getCode());
        division.setName(divisionDto.getName());
        
        divisionService.addDivision(division);

        return "Division added successfully";
    }
	
	
	@GetMapping("/getDepartments")
	@ResponseBody
	public List<Department> getDepartments() {
	    return departmentService.getAll();
	}

	@GetMapping("/getDivisions")
	@ResponseBody
	public List<Division> getDivisions() {
	    return divisionService.getAll();
	}
	
	@GetMapping("/deleteTeam")
	public String deleteTeam(@RequestParam int id,Model model) {
		teamService.deleteById(id);
		List<Team> team = teamService.getAll();
		model.addAttribute("team",team);
		return "redirect:/team-list-all";
	}
	
	@GetMapping("/deleteDepartment")
	public String deleteDepartment(@RequestParam int id,Model model) {
		departmentService.deleteById(id);
		List<Department> department = departmentService.getAll();
		model.addAttribute("department",department);
		return "redirect:/department-list-all";
	}
	
	@GetMapping("/profiles")
	public String profile() {
		return "profile";
	}

	@GetMapping("/dashboard")
	public String dashboard() {
		return "ciso-home";
	}
	@GetMapping("/noti")
	public String dakshboard() {
		return "noti";
	}
	
	
}