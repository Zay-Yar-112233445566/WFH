package com.WFHS.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.WFHS.entity.User;
import com.WFHS.entity.UserLogin;
import com.WFHS.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("")
	public ResponseEntity<List<User>> findAllByStaffId(Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			UserLogin userLogin = (UserLogin) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal();
			return new ResponseEntity<>(this.userService.findAllByTeamId(userLogin.getUser().getTeam().getId()), HttpStatus.OK);
		}
		return null;
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") String id) {
        return new ResponseEntity<>(this.userService.findByStaffId(id), HttpStatus.OK);
    }
    
	// Batch Form approve method for CEO
	@PostMapping("/forms")
	public String batchApproveForm(@RequestParam("idList") List<Integer> idList,@RequestParam("approveReason") String reason) {
		System.out.println("Yo I'm Here");
		for (int i : idList) {
			System.out.println("YoooooooooooooooooooooID : " + i);
		}
		return "redirect:/approved/forms";
	}

}
