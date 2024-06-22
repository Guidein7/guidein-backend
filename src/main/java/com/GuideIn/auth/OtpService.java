package com.GuideIn.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.GuideIn.twilio.TwilioConfig;
import com.GuideIn.user.Role;
import com.GuideIn.user.User;
import com.GuideIn.user.UserRepository;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

import jakarta.transaction.Transactional;

@Service
public class OtpService {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TwilioConfig config;
	
	
	public boolean sendOTP(String email, String mobile) {
		
//		Twilio.init(config.getAccountSid(), config.getAuthToken());
//		
//		try {
//			Verification verification = Verification.creator(
//			        config.getServiceSid(),
//			        mobile,
//			        "sms")
//			    .create();
//		} catch (ApiException e) {
//			e.printStackTrace();
//			return false;
//		}
		
		return true;
		
	}

	public boolean sendOTP(OtpRequest request) { 
		
		User user = null;
		 
		try {
			if(request.getEmail().isEmpty())
				user = repo.findByMobileAndRole(request.getMobile(), request.getRole()).orElseThrow(); 
			else
				user = repo.findByEmailAndRole(request.getEmail(), request.getRole()).orElseThrow();
		 }		 
		catch (Exception e) {
			return false;
		}
//		
//		Twilio.init(config.getAccountSid(), config.getAuthToken());
//		
//		try {
//			Verification verification = Verification.creator(
//			        config.getServiceSid(),
//			        user.getMobile(),
//			        "sms")
//			    .create();
//		} catch (ApiException e) {
//			e.printStackTrace();
//			return false;
//		}
		return true;	
	}
	
	@Transactional
	public boolean validateOTP(OtpValidateRequest request) throws DataAccessException {
		
//		Twilio.init(config.getAccountSid(), config.getAuthToken());
//
//	    VerificationCheck verificationCheck = null;
//		try {
//			verificationCheck = VerificationCheck.creator(
//			        config.getServiceSid())
//			        .setTo(request.getMobile())
//			        .setCode(request.getOtp())
//			        .create();
//			
//			if(verificationCheck.getStatus().equals("approved")) {
//		    	User user = repo.findByEmailAndRole(request.getEmail(), request.getRole()).orElseThrow();
//		    	user.setVerified(true);
//		    	repo.save(user);
//		    	return true;
//		    }
//		    
//		    else {
//		    	return false;
//		    }		
//			
//		} catch (ApiException e) {
//			e.printStackTrace();	
//			return false;
//		} 	

	 	User user = repo.findByEmailAndRole(request.getEmail(), request.getRole()).orElse(null);
	 	if(user == null)
	 		user = repo.findByMobileAndRole(request.getMobile(), request.getRole()).orElse(null);
    	user.setVerified(true);
    	repo.save(user);
    	return true;

	}

	
}
