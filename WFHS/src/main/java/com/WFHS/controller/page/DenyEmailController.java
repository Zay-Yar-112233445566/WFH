package com.WFHS.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.WFHS.entity.Role;
import com.WFHS.entity.User;
import com.WFHS.entity.UserLogin;
import com.WFHS.repository.UserLoginRepository;

@RestController
public class DenyEmailController {

	@Autowired
    private UserLoginRepository repo;
	
	@Autowired
    private JavaMailSender mailSender;

	@PostMapping("/send_rejection_email")
    public void sendEmail(@RequestParam("staffId") String staffId,
                          @RequestParam("reason") String reason,Authentication authentication) {
		UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
		User userName = userLogin.getUser();
		Role role = userLogin.getRole();
		System.out.println("Staff id : " + staffId);
        // Retrieve the user's email from the database using the staffId
        UserLogin user = repo.findByStaffId(staffId);
        User receiveUser = user.getUser();
        String to = user.getEmail();

        System.out.println("Email : " + to);
        
        String content = "Hello " + receiveUser.getName() + "\n";
        String footer = "Best Regards,\n"
                + userName.getName() + "(" + role.getName() + ")";
        // Create a Simple MailMessage object
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("kk");
        mailMessage.setTo(to);
        mailMessage.setSubject("Rejection Email");
        mailMessage.setText(content + "\n" + reason + "\n\n" + footer);
        

        // Send the email using the JavaMailSender

        mailSender.send(mailMessage);

    }
}
