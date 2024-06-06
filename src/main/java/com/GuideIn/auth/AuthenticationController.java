package com.GuideIn.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guidein/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	  @Autowired	
	  private final AuthenticationService service;
	  
	  @Autowired
	  private final OtpService otpService;

	  @PostMapping("/register")
	  public ResponseEntity<String> register(@RequestBody RegisterRequest request) { 
		  
		if(service.register(request))
			return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
		else
			return new ResponseEntity<>("User already registered", HttpStatus.CONFLICT);
	  }
	  
	  @PostMapping("/register/otpvalidate")
	  public ResponseEntity<String> validateOTP(@RequestBody OtpValidateRequest request){
		  
		  if(otpService.validateOTP(request))
			  return ResponseEntity.ok("OTP validated successfully");
		  
		  else
			  return new ResponseEntity<>("Invalid OTP", HttpStatus.UNAUTHORIZED);
	  }
	  
	  @PostMapping("/register/sendotp")
	  public ResponseEntity<String> sendOTP(@RequestBody OtpRequest request){
		  if(otpService.sendOTP(request))
			  return ResponseEntity.ok("OTP sent successfully");
		  
		  else 
			  return new ResponseEntity<>("unable to send OTP", HttpStatus.FORBIDDEN);	
	  }
	  
	  @PostMapping("/authenticate")
	  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
		
		AuthenticationResponse response = service.authenticate(request);
		
		if(response.getToken().equals("InValid credentials"))
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		  
		if(response.getToken().equals("Not a verfied user"))
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		
	    return ResponseEntity.ok(response);
	  }
	  
	  @PostMapping("register/forgetpassword")
	  public ResponseEntity<String> forgetPassword(@RequestBody ForgetPasswordRequest request){
		  
		  if(!service.forgetPassword(request))
			  return new ResponseEntity<>("Unable to reset password", HttpStatus.FORBIDDEN);
		  
		  return ResponseEntity.ok("Password rest successful");
		  
	  }
}
