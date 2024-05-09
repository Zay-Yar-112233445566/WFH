package com.WFHS.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WFHS.entity.ApplicantForm;
import com.WFHS.entity.Operation;
import com.WFHS.entity.UserLogin;
import com.WFHS.http.AuthoritiesConstants;
import com.WFHS.repository.ApplicantFormRepository;
import com.WFHS.repository.OperationRepository;
import com.WFHS.repository.UserLoginRepository;
import com.WFHS.service.OperationService;
import com.WFHS.service.UserService;

@Service
public class OperationServiceImpl implements OperationService{
	
	@Autowired
	ApplicantFormRepository formRepo;
	
	@Autowired
	OperationRepository repo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserLoginRepository userLoginRepo;
	
	@Override
	public void saveOperation(UserLogin userLogin,ApplicantForm form,String applierStaffId) {
		AuthoritiesConstants[] roles = AuthoritiesConstants.values();	
		String[] operations = Operation.operations;
		
		
		UserLogin login=userLoginRepo.findByStaffId(applierStaffId);
		
		
		//APPPLICANT=0,SERVICE_DESK=6, HR=7  applied form's operation process
		//Applied for team member feature is only available for staff role 
		if (login.getRole().getName().equals(roles[0].toString() ) 
			    || login.getRole().getName().equals(roles[6].toString()) 
			    || login.getRole().getName().equals(roles[7].toString())) {
			    
			    for (String operation : operations) {
			        Operation op = new Operation(); // Create a new Operation object in each iteration		    
			        op.setOperationName(operation);
			        if (operation.equals(operations[0])) {
			            op.setStatus(1);
			            op.setNotificationStatus(2);
			            op.setIssueDate(form.getSignDate());
			            op.setComment("Approved.");
			            op.setUser(userService.findByStaffId(applierStaffId));
			            op.setApplicantForm(form);
			        } else {
			            op.setStatus(0);
			            op.setNotificationStatus(0);
			            op.setIssueDate(null);
			            op.setComment(null);
			            op.setUser(null);
			            op.setApplicantForm(form);
			        }
			        repo.save(op);
			    }
			}

		
		//PROJECT_MANAGER applied form's operation process
		if (login.getRole().getName().equals(roles[1].toString())) {
			
			for (String operation : operations) {
				Operation op = new Operation();
				op.setOperationName(operation);
				if (operation.equals(operations[0]) || operation.equals(operations[1])) {
					op.setStatus(1);
					op.setNotificationStatus(2);
					op.setIssueDate(form.getSignDate());
					op.setComment("Approved.");
					op.setUser(login.getUser());
				} else {
					op.setStatus(0);
					op.setNotificationStatus(0);
					op.setComment(null);
					op.setIssueDate(null);
					op.setUser(null);
				}
				op.setApplicantForm(form);
				repo.save(op);
			}
		}
		//DEPARTMENT_HEAD applied form's operation process
		if (login.getRole().getName().equals(roles[2].toString())) {
			for (String operation : operations) {
				Operation op = new Operation();
				op.setOperationName(operation);
				if (operation.equals(operations[0]) || operation.equals(operations[1]) 
					|| operation.equals(operations[2])) {
					op.setStatus(1);
					op.setNotificationStatus(2);
					op.setComment("Approved.");
					op.setIssueDate(form.getSignDate());
					op.setUser(login.getUser());
				} else {
					op.setStatus(0);
					op.setNotificationStatus(0);
					op.setComment(null);
					op.setIssueDate(null);
					op.setUser(null);
				}
				op.setApplicantForm(form);
				repo.save(op);
			}
		}
		
		//DIVISION_HEAD applied form's operation process
		if (login.getRole().getName().equals(roles[3].toString())) {
			for (String operation : operations) {
				Operation op = new Operation();
				op.setOperationName(operation);
				if (operation.equals(operations[0]) || operation.equals(operations[1]) ||
					operation.equals(operations[2]) || operation.equals(operations[3])) {
					op.setStatus(1);
					op.setNotificationStatus(2);
					op.setIssueDate(form.getSignDate());
					op.setComment("Approved.");
					op.setUser(login.getUser());
				} else {
					op.setStatus(0);
					op.setNotificationStatus(0);
					op.setComment(null);
					op.setIssueDate(null);
					op.setUser(null);
				}
				op.setApplicantForm(form);
				repo.save(op);
			}
		}
		//CISO applied form's operation process
		if (login.getRole().getName().equals(roles[4].toString())) {
			for (String operation : operations) {
				Operation op = new Operation();
				op.setOperationName(operation);
				if (operation.equals(operations[0]) || operation.equals(operations[1]) ||
					operation.equals(operations[2]) || operation.equals(operations[3]) || operation.equals(operations[4])) {
					op.setStatus(1);
					op.setNotificationStatus(2);
					op.setIssueDate(form.getSignDate());
					op.setComment("Approved.");
					op.setUser(login.getUser());
				} else {
					op.setStatus(0);
					op.setNotificationStatus(0);
					op.setIssueDate(null);
					op.setComment(null);
					op.setUser(null);
				}
				op.setApplicantForm(form);
				repo.save(op);
			}
		}
		//CEO applied form's operation process
		if (login.getRole().getName().equals(roles[5].toString())) {
			for (String operation : operations) {
				Operation op = new Operation();
				op.setOperationName(operation);
					op.setStatus(1);
					op.setNotificationStatus(2);
					op.setComment("Approved.");
					op.setIssueDate(form.getSignDate());
					op.setUser(login.getUser());
					op.setApplicantForm(form);
					repo.save(op);
			}
			form.setCompleteStatus(1);
			formRepo.save(form);
		}	
	}

	@Override
	public Operation findById(int id) {
		return repo.findById(id);
	}
}
