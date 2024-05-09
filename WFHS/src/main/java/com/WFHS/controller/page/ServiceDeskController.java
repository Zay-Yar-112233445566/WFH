package com.WFHS.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.WFHS.service.UserLoginService;

@Controller
public class ServiceDeskController {
	
	@Autowired
	private UserLoginService userLoginService;

	@PostMapping("/process")
    public String processExcelAndSendOTP(@RequestParam("action") String action,
                                         @RequestParam("excelFile") MultipartFile file,
                                         Model model,RedirectAttributes redirectAttributes) {
        if ("upload".equals(action)) {
            if (file.isEmpty()) {
            	redirectAttributes.addFlashAttribute("error", "Please select a file to upload.");
            } else {
            	String message = userLoginService.processExcelAndSendOTP(file);
            	redirectAttributes.addFlashAttribute("message", message);
            }
        }
        return "redirect:/users";
    }

}