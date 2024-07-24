package com.GuideIn.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.GuideIn.config.JwtService;
import com.GuideIn.user.Role;
import com.GuideIn.user.User;
import com.GuideIn.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	  private final UserRepository repository;
	  private final PasswordEncoder passwordEncoder;
	  private final JwtService jwtService;
	  private final AuthenticationManager authenticationManager;
	  @Autowired
	  private final OtpService otpService;
	  
	  ///// TEST /////
	  @Transactional
	  public boolean register(RegisterRequest request) throws DataAccessException {
		  
		if(repository.findByEmailAndRole(request.getEmail(),request.getRole()).isEmpty() && 
				repository.findByMobileAndRole(request.getMobile(), request.getRole()).isEmpty()) {
			
			Boolean verified = false;
			if(request.getRole() == Role.ADMIN)
				verified = true;
			
			var user = User.builder()
			        .username(request.getUsername())
			        .email(request.getEmail())
			        .mobile(request.getMobile())
			        .password(passwordEncoder.encode(request.getPassword()))
			        .role(request.getRole())
			        .verified(verified)
			        .build();
			repository.save(user);
			if(user.getRole() != Role.ADMIN)
				otpService.sendOTP(user.getEmail(), user.getMobile());
			return true;
		}
		else return false; 
	  }
	  
	  //////// TEST /////////
	  
	  
//	  @Transactional
//	  public RegisterResponse register(RegisterRequest request) throws DataAccessException {
//		  
//		RegisterResponse response = null;
//		  
//		if(repository.findByEmailAndRole(request.getEmail(),request.getRole()).isEmpty() && 
//				repository.findByMobileAndRole(request.getMobile(), request.getRole()).isEmpty()) {
//			
//			Boolean verified = false;
//			if(request.getRole() == Role.ADMIN)
//				verified = true;
//			
//			var user = User.builder()
//			        .username(request.getUsername())
//			        .email(request.getEmail())
//			        .mobile(request.getMobile())
//			        .password(passwordEncoder.encode(request.getPassword()))
//			        .role(request.getRole())
//			        .verified(verified)
//			        .build();
//			
//			response = RegisterResponse.builder()
//					.mobile(user.getMobile())
//					.sessionUUID("Admin no OTP")
//					.build();
//			
//			if(user.getRole() != Role.ADMIN)
//				response = otpService.sendOTP(user.getEmail(), user.getMobile());
//			if(response.getSessionUUID() != "Unable to send OTP") //only save user after successful sending of OTP 
//				repository.save(user);
//			
//			return response;
//		}
//		else {
//			return response; 
//		}
//	  }
	  
	  @Transactional
	  public AuthenticationResponse authenticate(AuthenticationRequest request) throws DataAccessException{
	    		
	    User user = null;
	    
		try {
			if(request.getEmail().isEmpty())
				user = repository.findByMobileAndRole(request.getMobile(), request.getRole()).orElseThrow();
			else
				user = repository.findByEmailAndRole(request.getEmail(), request.getRole()).orElseThrow();
		} catch (Exception e) {
			return AuthenticationResponse
	    			.builder()
	    			.token("InValid credentials")
	    			.build();
		}
	
	    authenticationManager.authenticate(
		        new UsernamePasswordAuthenticationToken(
		            user.getEmail() +","+ request.getRole(),//because loadByUserName only has userEmail parameter
		            request.getPassword()
		        )
		    );
    
	    if(!user.getVerified())
	    	return AuthenticationResponse
	    			.builder()
	    			.token("Not a verfied user")
	    			.build();
	    
	    Map<String, Object> extraClaims = new HashMap<>();
	    extraClaims.put("authority",user.getRole());
	    extraClaims.put("username",user.getName());
	    extraClaims.put("mobile", user.getMobile());
	    var jwtToken = jwtService.generateToken(extraClaims,user);
	    
	    return AuthenticationResponse.builder()
	        .token(jwtToken)
	        .build();
	  }
	  
	  
	  @Transactional
	  public boolean forgetPassword(ForgetPasswordRequest request) {
		  
		 User user = null;
		 
		 try {
			 if(request.getEmail().isEmpty())
				 user = repository.findByMobileAndRole(request.getMobile(), request.getRole()).orElseThrow(); 
			 else
				user = repository.findByEmailAndRole(request.getEmail(), request.getRole()).orElseThrow();
		 }		 
		 catch (Exception e) {
			 return false;
		 }		 
		 user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		 repository.save(user);
		 return true;	 
	  }	 
	  
}
