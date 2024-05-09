package com.WFHS.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.WFHS.dto.ApplicantFormDto;
import com.WFHS.dto.OperationDto;
import com.WFHS.entity.ApplicantForm;
import com.WFHS.entity.Capture;
import com.WFHS.entity.Ledger;
import com.WFHS.entity.LedgerDetail;
import com.WFHS.entity.Operation;
import com.WFHS.entity.User;
import com.WFHS.entity.UserLogin;
import com.WFHS.http.AuthoritiesConstants;
import com.WFHS.repository.ApplicantFormRepository;
import com.WFHS.repository.CaptureRepository;
import com.WFHS.repository.LedgerDetailRepository;
import com.WFHS.repository.LedgerRepository;
import com.WFHS.repository.OperationRepository;
import com.WFHS.service.ApplicantFormService;
import com.WFHS.service.CaptureService;
import com.WFHS.service.LedgerExcelGenerator;
import com.WFHS.service.OTPStaffIDExcelGenerator;
import com.WFHS.service.OperationService;
import jakarta.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;

@Service
public class ApplicantFormServiceImpl implements ApplicantFormService {

	@Autowired
	private ApplicantFormRepository repo;

	@Autowired
	private CaptureService captureService;

	@Autowired
	private OperationService operationService;

	@Autowired
	private OperationRepository opRepo;
	
	@Autowired
	private LedgerRepository ledgerRepo;
	
	
	@Autowired
	private LedgerDetailRepository detailRepo;
	
	@Autowired
	private CaptureRepository captureRepo;

	AuthoritiesConstants[] roles = AuthoritiesConstants.values();
	String[] operations = Operation.operations;

	
	
	/* Method to save form when applicant applied the applicant_form */
	@Override
	public ApplicantForm save(ApplicantFormDto dto, UserLogin userLogin) {
		MultipartFile image = dto.getSignatureImg();
		Date createdAt = new Date();
		LocalDateTime timestamp = LocalDateTime.now();
		String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
		try {
			String uploadDir = "public/images/";
			Path uploadPath = Paths.get(uploadDir);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try (InputStream inputStream = image.getInputStream()) {
				Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception ex) {
			System.out.println("Exception : " + ex.getMessage());
		}
		ApplicantForm form = new ApplicantForm();
		form.setWorkingPlace(dto.getWorkingPlace());
		form.setRequestPercentage(dto.getRequestPercentage());
		form.setRequestReason(dto.getRequestReason());
		form.setFromDate(dto.getFromDate());
		form.setToDate(dto.getToDate());
		form.setSignDate(dto.getSignDate());
		form.setCreatedTimestamp(timestamp);
		form.setSignatureImg(storageFileName);
		form.setApplierStaffId(userLogin.getStaffId());
		form.setCompleteStatus(0);
		return repo.save(form);
	}

	/* Method to find applicant_form by formId */
	@Override
	public ApplicantForm findById(int id) {
		return repo.findById(id);
	}

	/*
	 * Method to apply applicant_form including operation saving process and capture
	 * saving process by current user User may be all role
	 */
	@Override
	public void applyForm(ApplicantFormDto dto, UserLogin userLogin) {
		ApplicantForm form = this.save(dto, userLogin);
		captureService.saveCapture(dto.getCaptureDto(), form);
		operationService.saveOperation(userLogin, form, dto.getApplierStaffId());
	}

	/* Find all applicant_forms these are not still approved by related approver */
	@Override
	public List<ApplicantForm> findApproveForms(User user) {
		// For PM Role
		if (user.getRoleName().equals(roles[1].toString())) {
			List<ApplicantForm> unFinishForm = new ArrayList<>();
			for (ApplicantForm form : repo.findAllManagerApproveForms(user.getTeam().getId())) {
				for (Operation o : form.getOperation()) {
					if (o.getOperationName().equals(operations[1]) && o.getStatus() == 0) {
						unFinishForm.add(form);
					}
				}
			}
			return unFinishForm;
		}

		// For Department Heakd Role
		if (user.getRoleName().equals(roles[2].toString())) {
			List<ApplicantForm> unFinishForm = new ArrayList<>();
			boolean isFinished = false;
			for (ApplicantForm form : repo.findAllDepartmentHeadApproveForms(user.getTeam().getDepartment().getId())) {
				List<Operation> op = form.getOperation();
				for (int i = 0; i < op.size(); i++) {
					isFinished = ((op.get(1).getOperationName().equals(operations[1]) && op.get(1).getStatus() == 1)
							&& (op.get(2).getOperationName().equals(operations[2]) && op.get(2).getStatus() == 0));

				}
				if (isFinished) {
					unFinishForm.add(form);
				}
			}
			return unFinishForm;
		}

		// For Division Head Role
		if (user.getRoleName().equals(roles[3].toString())) {
			List<ApplicantForm> unFinishForm = new ArrayList<>();
			boolean isFinished = false;
			for (ApplicantForm form : repo
					.findAllDivisionHeadApproveForms(user.getTeam().getDepartment().getDivision().getId())) {
				List<Operation> op = form.getOperation();
				for (int i = 0; i < op.size(); i++) {
					isFinished = ((op.get(1).getOperationName().equals(operations[1]) && op.get(1).getStatus() == 1)
							&& (op.get(2).getOperationName().equals(operations[2]) && op.get(2).getStatus() == 1)
							&& (op.get(3).getOperationName().equals(operations[3]) && op.get(3).getStatus() == 0));
				}
				if (isFinished) {
					unFinishForm.add(form);
				}
			}
			return unFinishForm;
		}

		// For CISO Role
		if (user.getRoleName().equals(roles[4].toString())) {
			List<ApplicantForm> unFinishForm = new ArrayList<>();
			boolean isFinished = false;
			for (ApplicantForm form : repo.findAllNotApproveForms()) {
				List<Operation> op = form.getOperation();
				for (int i = 0; i < op.size(); i++) {
					isFinished = ((op.get(1).getOperationName().equals(operations[1]) && op.get(1).getStatus() == 1)
							&& (op.get(2).getOperationName().equals(operations[2]) && op.get(2).getStatus() == 1)
							&& (op.get(3).getOperationName().equals(operations[3]) && op.get(3).getStatus() == 1)
							&& (op.get(4).getOperationName().equals(operations[4]) && op.get(4).getStatus() == 0));
				}
				if (isFinished) {
					unFinishForm.add(form);
				}
			}
			return unFinishForm;
		}
		
		
		//SERVICE_DESK,//5
		if(user.getRoleName().equals(roles[5].toString())) {
			List<ApplicantForm> unFinishForm = new ArrayList<>();
			boolean isFinished = false;
			for (ApplicantForm form : repo.findAllNotApproveForms()) {
				List<Operation> op = form.getOperation();
				for (int i = 0; i < op.size(); i++) {
					isFinished = ((op.get(1).getOperationName().equals(operations[1]) && op.get(1).getStatus() == 1)
							&& (op.get(2).getOperationName().equals(operations[2]) && op.get(2).getStatus() == 1)
							&& (op.get(3).getOperationName().equals(operations[3]) && op.get(3).getStatus() == 1)
							&& (op.get(4).getOperationName().equals(operations[4]) && op.get(4).getStatus() == 2)
							&& (op.get(5).getOperationName().equals(operations[5]) && op.get(5).getStatus() == 1));
				}
				if (isFinished) {
					unFinishForm.add(form);
				}
			}
			return unFinishForm;
		}
		
		//for CEO //6
		else{
			List<ApplicantForm> unFinishForm = new ArrayList<>();
			boolean isFinished = false;
			for (ApplicantForm form : repo.findAllNotApproveForms()) {
				List<Operation> op = form.getOperation();
				for (int i = 0; i < op.size(); i++) {
					isFinished = ((op.get(1).getOperationName().equals(operations[1]) && op.get(1).getStatus() == 1)
							&& (op.get(2).getOperationName().equals(operations[2]) && op.get(2).getStatus() == 1)
							&& (op.get(3).getOperationName().equals(operations[3]) && op.get(3).getStatus() == 1)
							&& (op.get(4).getOperationName().equals(operations[4]) && op.get(4).getStatus() == 1)
							&& (op.get(6).getOperationName().equals(operations[6]) && op.get(6).getStatus() == 0));
				}
				if (isFinished) {
					unFinishForm.add(form);
				}
			}
			return unFinishForm;
		}
	}

	@Override
	public void approveForm(OperationDto dto, UserLogin userLogin) {	
		
		Date currentDate = new Date(System.currentTimeMillis());
		java.sql.Date current = new java.sql.Date(currentDate.getTime());
		Operation ope=operationService.findById(dto.getId());
		ApplicantForm form=ope.getApplicantForm();

		//If approver is PROJECT_MANAGER, will run this code
		if(userLogin.getRole().getName().equals(roles[1].toString())) {
			Operation operation=form.getOperation().get(1);
			if(dto.getStatus() == 2) {
				form.setCompleteStatus(2);
				repo.save(form);
			}
			operation.setStatus(dto.getStatus());
			operation.setComment(dto.getComment());
			operation.setNotificationStatus(2);
			operation.setUser(userLogin.getUser());
			operation.setIssueDate(current); 
			opRepo.save(operation);
		}
		
		//If approver is DEPARTMENT_HEAD, will run this code
		if(userLogin.getRole().getName().equals(roles[2].toString())) {
			Operation operation=form.getOperation().get(2);
			if(dto.getStatus() == 2) {
				form.setCompleteStatus(2);
				repo.save(form);
			}
			operation.setStatus(dto.getStatus());
			operation.setComment(dto.getComment());
			operation.setNotificationStatus(2);
			operation.setUser(userLogin.getUser());
			operation.setIssueDate(current); 
			opRepo.save(operation);
		}
		
		//If approver is DIVISION_HEAD, will run this code
		if(userLogin.getRole().getName().equals(roles[3].toString())) {
			Operation operation=form.getOperation().get(3);
			if(dto.getStatus() == 2) {
				form.setCompleteStatus(2);
				repo.save(form);
			}
			operation.setStatus(dto.getStatus());
			operation.setComment(dto.getComment());
			operation.setNotificationStatus(2);
			operation.setUser(userLogin.getUser());
			operation.setIssueDate(current); 
			opRepo.save(operation);
		}
		
		//If approver is  CISO, will run this code
		if(userLogin.getRole().getName().equals(roles[4].toString())) {
			Operation CISOOperation=form.getOperation().get(4);
			Operation serviceDeskOperation=form.getOperation().get(5);
			if(dto.getStatus() == 2) {
				CISOOperation.setStatus(dto.getStatus());
				serviceDeskOperation.setStatus(1);
				opRepo.save(serviceDeskOperation);
			}else {
				CISOOperation.setStatus(dto.getStatus());
			}
			CISOOperation.setComment(dto.getComment());
			CISOOperation.setNotificationStatus(2);
			CISOOperation.setUser(userLogin.getUser());
			CISOOperation.setIssueDate(current); 
			opRepo.save(CISOOperation);
		}
		
		//If approver is  SERVICE_DESK, will run this code
		if (userLogin.getRole().getName().equals(roles[5].toString())) {
			Operation serviceDeskOperation = form.getOperation().get(5);
			Operation CISOoperation = form.getOperation().get(4);
			serviceDeskOperation.setStatus(dto.getStatus());
			serviceDeskOperation.setComment(dto.getComment());
			serviceDeskOperation.setNotificationStatus(2);
			serviceDeskOperation.setUser(userLogin.getUser());
			serviceDeskOperation.setIssueDate(current);
			opRepo.save(serviceDeskOperation);
			CISOoperation.setStatus(3);
			opRepo.save(CISOoperation);
			}
		
		//If approver is  CEO, will run this code
		if(userLogin.getRole().getName().equals(roles[6].toString())) {
			Operation operation=form.getOperation().get(6);
			operation.setStatus(dto.getStatus());
			operation.setComment(dto.getComment());
			operation.setNotificationStatus(2);
			operation.setUser(userLogin.getUser());
			operation.setIssueDate(current); 
			opRepo.save(operation);

			if(dto.getStatus() == 2) {
				form.setCompleteStatus(2);
			}
			if(dto.getStatus() == 1) {
				form.setCompleteStatus(1);
			}
			repo.save(form);
		}
		
	//If approver is  HR, will run this code
	if(userLogin.getRole().getName().equals(roles[7].toString())) {
//			Operation operation=form.getOperation().get(6);
//			operation.setStatus(dto.getStatus());
//			operation.setComment(dto.getComment());
//			operation.setNotificationStatus(2);
//			operation.setUser(userLogin.getUser());
//			operation.setIssueDate(current); 
//			opRepo.save(operation);
//
//			if(dto.getStatus() == 2) {
//				form.setCompleteStatus(2);
//			}
//			if(dto.getStatus() == 1) {
//				form.setCompleteStatus(1);
//			}
//			repo.save(form);
		}
	}

	
	@Override
	public ApplicantForm getNGForm(UserLogin userlogin) {
		ApplicantForm unFinishForm=new ApplicantForm();
		for (ApplicantForm form : repo.findAllNotApproveForms()) {
			List<Operation> op = form.getOperation();
			if((op.get(0).getUser().getId() == userlogin.getUser().getId()) && (op.get(4).getStatus() == 3) && (op.get(5).getStatus() == 2)){
				unFinishForm = form;
			}
		}
		return unFinishForm;
		
	}

	@Override
	public void modifyForm(ApplicantFormDto dto) {
		ApplicantForm form=repo.findById(dto.getId());
		Capture capture=form.getCapture();
		Operation CISOOperation=form.getOperation().get(4);
		Operation serviceDeskOperation=form.getOperation().get(5);
		
		CISOOperation.setStatus(0);
		serviceDeskOperation.setStatus(0);
		
		form.setWorkingPlace(dto.getWorkingPlace());
		form.setRequestPercentage(dto.getRequestPercentage());
		form.setRequestReason(dto.getRequestReason());
		captureService.updateCapture(dto.getCaptureDto(), capture.getId());
		repo.save(form);
		opRepo.save(CISOOperation);
		opRepo.save(serviceDeskOperation);
	}


	
	@Override
	public void exportStaffIdsForOTP(List<Integer> formIds, HttpServletResponse response) {
		List<ApplicantForm> formList = repo.findAllById(formIds);
		if (formList != null) {
			OTPStaffIDExcelGenerator generator = new OTPStaffIDExcelGenerator(formList);
			for (ApplicantForm form : formList) {
				form.setCompleteStatus(3);
				this.repo.save(form);
			}
			try {
				generator.export(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("There is no Form List for OTP");
		}
	}

	
	@Override
	public void exportLedgerExcel(List<Integer> formIds, HttpServletResponse response,UserLogin userLogin) {
		 List<ApplicantForm> formList = repo.findAllById(formIds);
		 
		 //Here code is to write the approved form data to WFH Ledger excel file
		   if (formList != null) {
		        LedgerExcelGenerator generator = new LedgerExcelGenerator(formList);
		        try {
		            generator.export(response);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    } else {
		        System.out.println("There is no Form List for OTP");
		    }
		   
		  //Here code is to write the printed(WFH Ledger excel) form data to Ledger and LedgerDetail table
		 LocalDate nextMonthStartDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);
		 LocalDate currentDate = LocalDate.now();
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		 String confirmDate = currentDate.format(formatter);
		 String businessMonth=nextMonthStartDate.format(formatter);

		 //create new ledger file for next business month and save ledger
		 Ledger ledger=new Ledger();
		 ledger.setBusinessMonth(businessMonth);
		 ledger.setGenerateDate(confirmDate);
		 ledger.setConfirmedHRId(userLogin.getStaffId());
		 ledger.setHrConfirmDate(confirmDate);
		 ledger=ledgerRepo.save(ledger);

		 //create ledger detail(excel row data) to write to the ledger table
		 for(ApplicantForm form:formList) {
			 LedgerDetail detail=new LedgerDetail();
			 detail.setLedger(ledger);
			 detail.setDepartment(form.getOperation().get(0).getUser().getDepartmentName());
			 detail.setDivision(form.getOperation().get(0).getUser().getDivisionName());
			 detail.setTeam(form.getOperation().get(0).getUser().getTeamName());
			 detail.setStaffId(form.getOperation().get(0).getUser().getStaffId());
			 detail.setName(form.getOperation().get(0).getUser().getName());
			 detail.setEmail(form.getOperation().get(0).getUser().getEmail());
			 detail.setAppliedDate(form.getOperation().get(0).getIssueDate());
			 detail.setFromPeriod(form.getFromDate());
			 detail.setToPeriod(form.getToDate());
			 detail.setWorkcation(form.getWorkingPlace());
			 detail.setRequestPercentage(form.getRequestPercentage());
			 detail.setUseOwnFacilities("YES");
			 detail.setIfNoComputer(" ");
			 detail.setIfNoMonitor("");
			 detail.setIfNoUPS("");
			 detail.setIfNoPhone("");
			 detail.setIfNoOther("");	
			 detail.setEnvironmentFacilities("OK");
			 detail.setPmStatus("Approved");
			 detail.setPmApproveDate(form.getOperation().get(1).getIssueDate());
			 detail.setDeptHeadStatus("Approved");
			 detail.setDeptHeadApproveDate(form.getOperation().get(2).getIssueDate());
			 detail.setDiviHeadStatus("Approved");
			 detail.setDiviHeadApproveDate(form.getOperation().get(3).getIssueDate());
			 detail.setCisoStatus("OK");
			 detail.setCisoApproveDate(form.getOperation().get(4).getIssueDate());
			 detail.setCeoApproveDate(form.getOperation().get(6).getIssueDate());
			 detail.setReasonForWFH(form.getRequestReason());
			 detailRepo.save(detail);
		 }
		 for(ApplicantForm form:formList) {
			 Capture capture=form.getCapture();
			 String uploadDir = "public/images/"; // Define uploadDir here
			 
			 //delete OS Type Image from Project Folder
			 if (capture.getOsTypeImg() != null) {
                 Path oldImagePath = Paths.get(uploadDir + capture.getOsTypeImg());
                 try {
					Files.delete(oldImagePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
			 //delete Security Patch Image from Project Folder
			 if (capture.getSecurityPatchUpdateImg() != null) {
                 Path oldImagePath = Paths.get(uploadDir + capture.getSecurityPatchUpdateImg());
                 try {
					Files.delete(oldImagePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
			//delete AntivirusSoftwareImg Image from Project Folder
			 if (capture.getAntivirusSoftwareImg() != null) {
                 Path oldImagePath = Paths.get(uploadDir + capture.getAntivirusSoftwareImg());
                 try {
					Files.delete(oldImagePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
			 
			//delete AntivirusPatternUpdatedImg Image from Project Folder
			 if (capture.getAntivirusPatternUpdatedImg() != null) {
                 Path oldImagePath = Paths.get(uploadDir + capture.getAntivirusPatternUpdatedImg());
                 try {
					Files.delete(oldImagePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
			//delete AntivirusFullScanImg Image from Project Folder
			 if (capture.getAntivirusFullScanImg() != null) {
                 Path oldImagePath = Paths.get(uploadDir + capture.getAntivirusFullScanImg());
                 try {
					Files.delete(oldImagePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
			 //delete capture from database
			 captureRepo.deleteById(form.getCapture().getId());
			 
			 //delete approve operations
			 for(Operation operation: form.getOperation()) {
				 opRepo.deleteById(operation.getId());
			 }
			 
			//delete AntivirusFullScanImg Image from Project Folder
			 if (form.getSignatureImg() != null) {
                 Path oldImagePath = Paths.get(uploadDir + form.getSignatureImg());
                 try {
					Files.delete(oldImagePath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
			 repo.deleteById(form.getId());
		 }
		 repo.deleteAll();
	}
	
	@Override
	public List<ApplicantForm> findCompleteForm() {
		return repo.findAllApproveForms();
	}

	@Override
	public int getIncompleteFormCount(int completeStatus) {
		return repo.countByCompleteStatus(completeStatus);
	}

	@Override
	public List<ApplicantForm> findOTPSentForms() {
		return repo.findAllByCompleteStatus(4);
	}

	@Override
	public ApplicantForm getPendingApplyForm(UserLogin userLogin) {
		ApplicantForm unFinishForm=new ApplicantForm();
		for (ApplicantForm form : repo.findAllNotApproveForms()) {
			
			if((form.getCompleteStatus() == 0) && (form.getOperation().get(0).getUser().getId() == userLogin.getUser().getId())) {
				unFinishForm = form;
				break;
			}
			
		}
		return unFinishForm;
	}

	@Override
	public ApplicantForm getCompleteApplyForm(UserLogin userLogin) {
		ApplicantForm finishForm=new ApplicantForm();
		for (ApplicantForm form : repo.findAllApproveForms()) {


			System.out.println("Complete: "+form.getId());
			if((form.getCompleteStatus() == 1) && (form.getOperation().get(0).getUser().getId() == userLogin.getUser().getId())) {
				finishForm = form;
				break;
			}
			
		}
		return finishForm;
	}

	@Override
	public void deleteAllForm() {
		repo.deleteAll();
	}

	@Override
	public List<ApplicantForm> getNgForms(UserLogin userLogin) {
		List<ApplicantForm> ngForms = new ArrayList<>();
		
		for(ApplicantForm form:repo.findNGForms()){
			if(form.getOperation().get(0).getUser().getStaffId().equals(userLogin.getStaffId())){
				ngForms.add(form);
			}
		}
		return ngForms;
	}
}
