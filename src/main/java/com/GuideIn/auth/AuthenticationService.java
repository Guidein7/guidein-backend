package com.GuideIn.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.GuideIn.config.JwtService;
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
	  private final OtpService otpService;
	  
	  @Transactional
	  public boolean register(RegisterRequest request) throws DataAccessException {
		if(repository.findByEmailAndRole(request.getEmail(),request.getRole()).isEmpty()) {
			var user = User.builder()
			        .username(request.getUsername())
			        .email(request.getEmail())
			        .mobile(request.getMobile())
			        .password(passwordEncoder.encode(request.getPassword()))
			        .role(request.getRole())
			        .verified(false)
			        .build();
			repository.save(user);
			otpService.sendOTP(user.getName() , user.getMobile());
			return true;
		}
		else return false; 
	  }
	  
	  @Transactional
	  public AuthenticationResponse authenticate(AuthenticationRequest request) throws DataAccessException{
	    authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(
	            request.getEmail(),
	            request.getPassword()
	        )
	    );
	    var user = repository.findByEmailAndRole(request.getEmail(), request.getRole())
	        .orElseThrow();
	    
	    if(!user.getVerified())
	    	return AuthenticationResponse
	    			.builder()
	    			.token("Not a verfied user")
	    			.build();
	    
	    Map<String, Object> extraClaims = new HashMap<>();
	    extraClaims.put("authority",user.getRole());
	    extraClaims.put("username",user.getName());
	    var jwtToken = jwtService.generateToken(extraClaims,user);
	    
	    return AuthenticationResponse.builder()
	        .token(jwtToken)
	        .build();
	  }
	
}
