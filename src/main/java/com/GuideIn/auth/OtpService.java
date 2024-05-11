package com.GuideIn.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GuideIn.user.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

@Service
public class OtpService {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TwilioConfig config;

	public void sendOTP(String username, String mobile) {
		
		Twilio.init(config.getAccountSid(), config.getAuthToken());
		
		Verification verification = Verification.creator(
                config.getServiceSid(),
                mobile,
                "sms")
            .create();

//		verification.getStatus();		
	}
	
	
	public boolean validateOTP(OtpRequest request) {
		
		Twilio.init(config.getAccountSid(), config.getAuthToken());

	    VerificationCheck verificationCheck = VerificationCheck.creator(
	            config.getServiceSid())
	            .setTo(request.getMobile())
	            .setCode(request.getOtp())
	            .create();
	    
	    if(verificationCheck.getStatus().equals("approved"))
	    	return true;
	    
	    else{
	    	repo.deleteByEmail(request.getEmail());
	    	return false;
	    }
		  	
	}
	

	
	

}
