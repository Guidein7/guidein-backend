package com.GuideIn.auth;

import com.GuideIn.admin.AdminService;
import com.GuideIn.admin.Learner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GuideIn.plivo.DLTdetails;
import com.GuideIn.plivo.PlivoMessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guidein/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	  @Autowired	
	  private final AuthenticationService service;

	  @Autowired
	  private final AdminService adminService;
	  
	  @Autowired
	  private final OtpService otpService;
	  
	  @Autowired
	  private final OtpService1 otpService1;
	  
	  @Autowired
	  private final PlivoMessageService messageService;
	  
	  //test application
	  @GetMapping("/")
	  public ResponseEntity<String> greet(){
		  return ResponseEntity.ok("hi its running......");
	  }
	  
	  //test messages
	  @GetMapping("/message")
	  public ResponseEntity<String> message(){
		  messageService.sendMessage(DLTdetails.REFERRAL_REJECTED, "+919791657785", "Metrology Process Development engineer");
		  //messageService.sendOTPMessage(DLTdetails.OTP,"+919791657785" , "101234");
		  return ResponseEntity.ok("message sent");
	  }
	  
	  ////// TEST /////////
	  
	  @PostMapping("/register")
	  public ResponseEntity<String> register(@RequestBody RegisterRequest request) { 
		
		Boolean status = service.register(request);
		  
		if(status == null)
			return new ResponseEntity<>("User already registered", HttpStatus.CONFLICT);	
		else if(status)
			return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
		else
			return new ResponseEntity<>("Unable to send OTP", HttpStatus.FORBIDDEN);
	  }
	  
	  @PostMapping("/register/otpvalidate")
	  public ResponseEntity<String> validateOTP(@RequestBody OtpValidateRequest request){
		  
		  if(otpService1.validateOTP(request))
			  return ResponseEntity.ok("OTP validated successfully");
		  
		  else
			  return new ResponseEntity<>("Invalid OTP", HttpStatus.UNAUTHORIZED);
	  }
	  
	  @PostMapping("/register/sendotp")
	  public ResponseEntity<String> sendOTP(@RequestBody OtpRequest request){
		  if(otpService1.sendOTP(request))
			  return ResponseEntity.ok("OTP sent successfully");  
		  else 
			  return new ResponseEntity<>("unable to send OTP", HttpStatus.FORBIDDEN);	
	  }
	  
	  
	  // Using Plivo verify API

//	  @PostMapping("/register")
//	  public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) { 
//		RegisterResponse response = service.register(request);
//		if(response == null)
//			return new ResponseEntity<>(response,HttpStatus.CONFLICT); //already registered 
//		else if(response.getSessionUUID().equals("Unable to send OTP"))
//			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
//		else 
//			return new ResponseEntity<>(response, HttpStatus.CREATED);		
//	  }
//	  
//	  @PostMapping("/register/otpvalidate")
//	  public ResponseEntity<String> validateOTP(@RequestBody OtpValidateRequest request){
//		  
//		  if(otpService.validateOTP(request))
//			  return ResponseEntity.ok("OTP validated successfully");
//		  else
//			  return new ResponseEntity<>("Invalid OTP", HttpStatus.UNAUTHORIZED);
//	  }
//	  
//	  @PostMapping("/register/sendotp")
//	  public ResponseEntity<RegisterResponse> sendOTP(@RequestBody OtpRequest request){
//		  RegisterResponse response = otpService.sendOTP(request);
//		  if(response != null)
//			  return new ResponseEntity<>(response,HttpStatus.OK);  
//		  else 
//			  return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);	
//	  }
	  
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

	@PostMapping("/savelearnerdetails")
	public ResponseEntity<?> saveLearnerDetails(@RequestBody  Learner learner) {
		try {
			Learner savedLearner = adminService.saveLearnerDetails(learner);
			ApiResponse response = new ApiResponse(true, "Learner details saved successfully", savedLearner);
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			ApiResponse response = new ApiResponse(false, "Failed to save learner details. Please try again later.", null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
