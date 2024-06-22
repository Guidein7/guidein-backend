package com.GuideIn.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GuideIn.auth.AuthenticationService;
import com.GuideIn.auth.OtpService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guidein/v1/admin")
@RequiredArgsConstructor
public class AdminController {
	
	@GetMapping("/greet")
	public ResponseEntity<String> greet(){
		return ResponseEntity.ok("In admin pannel");
	}

}
