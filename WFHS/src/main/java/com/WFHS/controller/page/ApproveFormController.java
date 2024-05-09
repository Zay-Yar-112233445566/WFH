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

import com.WFHS.dto.OperationDto;
import com.WFHS.entity.ApplicantForm;
import com.WFHS.entity.Operation;
import com.WFHS.entity.UserLogin;
import com.WFHS.repository.ApplicantFormRepository;
import com.WFHS.service.ApplicantFormService;
import com.WFHS.service.DepartmentService;
import com.WFHS.service.DivisionService;
import com.WFHS.service.OperationService;
import com.WFHS.service.TeamService;

import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/approved")
public class ApproveFormController {

	@Autowired
	private ApplicantFormService formService;

	@Autowired
	private OperationService operationService;

	@Autowired
	private ApplicantFormRepository formRepo;
	
	@Autowired
	private DivisionService divisionService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private TeamService teamService;
	
	@GetMapping("/forms")
	public String showApproveForm(Model model, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			model.addAttribute("userMain", userLogin.getUser());
			model.addAttribute("divisionList", divisionService.getAll());
			model.addAttribute("departmentList", departmentService.getAll());
			model.addAttribute("teamList",teamService.getAll());
			List<Operation> operations = new ArrayList<>();
			for (ApplicantForm form : formService.findApproveForms(userLogin.getUser())) {
				operations.add(form.getOperation().get(0));
			}
			model.addAttribute("operations", operations);
			return "applied-form-list";
		} else {
			return "redirect:/login";
		}
	}

	// Form approve method for current user(approver)
	@GetMapping("/{id}")
	public String showApproveForm(@PathVariable("id") int operationId, Model model, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			Operation operation=operationService.findById(operationId);
			ApplicantForm form=formRepo.findById(operation.getApplicantForm().getId());
			model.addAttribute("userMain", userLogin.getUser());
			model.addAttribute("operation",operation );
			model.addAttribute("form",form);
			return "form-approve";
		} else {
			return "redirect:/login";
		}
	}

	// Form approve method for current user(approver)
	@PostMapping("/form/")
	public String approveForm(@RequestBody OperationDto dto, Authentication authentication, Model model) {
		UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
		formService.approveForm(dto, userLogin);
		return "redirect:/approved/forms";
	}

	// Batch Form approve method for CEO
	@PostMapping("/forms")
	public String batchApproveForm(@RequestParam("idList") List<Integer> idList,
			@RequestParam("approveReason") String reason, Authentication authentication,@RequestParam("status") int status) {
		
		UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
		OperationDto dto = new OperationDto();
		dto.setComment(reason);
		for (int i : idList) {
			dto.setId(i);
			dto.setStatus(status);
			formService.approveForm(dto, userLogin);
		}
		return "redirect:/approved/forms";
	}
	
	//Method for show CEO Approved Form List<For Service Desk To download Approved form for downloading staff id(OTP Sending>
	@GetMapping("/download/staffId-for-otp")
	public String showCEOApprovedForms(Model model, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			model.addAttribute("userMain", userLogin.getUser());
			List<ApplicantForm> forms = formService.findCompleteForm();
			List<Integer> Ids = new ArrayList<>();
			for (ApplicantForm form : forms) {
			    Ids.add(form.getId());
			}
			
			model.addAttribute("forms",forms);
			model.addAttribute("Ids",Ids);
			return "ceo-approve-form-list";
			
		} else {
			return "redirect:/login";
		}
	}
	
	@GetMapping("/download/staffId-for-otp/{Ids}")
	public String downloadCompleteFormStaffId(@PathVariable("Ids") List<Integer> Ids,HttpServletResponse response) {
		response.setContentType("application/octet-stream");
		formService.exportStaffIdsForOTP(Ids,response);
		return "redirect:/approved/download/staffId-for-otp";
	}

	@GetMapping("/download/ledgers/{Ids}")
	public String downloadLedgerExcel(@PathVariable("Ids") List<Integer> Ids, HttpServletResponse response,
			Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			System.out.println("HEllo BROTHER:");
			response.setContentType("application/octet-stream");
			formService.exportLedgerExcel(Ids, response, userLogin);
			return "ledgers";
		} else {
			return "redirect:/login";
		}

	}


}
//@GetMapping("/download/staffId-for-otp/{Ids}")
//public ResponseEntity<String> exportStaffIdsForOTP(List<Integer> formIds, HttpServletResponse response) {
//    List<ApplicantForm> formList = formRepo.findAllById(formIds);
//    if (formList != null) {
//        OTPStaffIDExcelGenerator generator = new OTPStaffIDExcelGenerator(formList);
//        for(ApplicantForm form:formList) {
//            form.setCompleteStatus(3);
//            this.formRepo.save(form);
//        }
//        try {
//            generator.export(response);
//            return ResponseEntity.ok().body("Export successful"); // Return a success status code
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Export failed"); // Return an error status code
//        }
//    } else {
//        System.out.println("There is no Form List for OTP");
//        return ResponseEntity.notFound().build(); // Return a not found status code
//    }
//}

