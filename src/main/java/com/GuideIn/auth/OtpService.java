package com.GuideIn.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.GuideIn.plivo.PlivoConfig;
import com.GuideIn.user.User;
import com.GuideIn.user.UserRepository;
import com.plivo.api.Plivo;
import com.plivo.api.models.verify_session.SessionCreateResponse;
import com.plivo.api.models.verify_session.VerifySession;

import jakarta.transaction.Transactional;

@Service
public class OtpService {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PlivoConfig config;
	
	////// TEST //////
	public boolean sendOTP(String email, String mobile) {
		
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
		return true;	
	}
	
	@Transactional
	public boolean validateOTP(OtpValidateRequest request) throws DataAccessException {	

	 	User user = repo.findByEmailAndRole(request.getEmail(), request.getRole()).orElse(null);
	 	if(user == null)
	 		user = repo.findByMobileAndRole(request.getMobile(), request.getRole()).orElse(null);
    	user.setVerified(true);
    	repo.save(user);
    	return true;

	}
	
	/////// TEST /////////
	
//	public RegisterResponse sendOTP(String email, String mobile) {
//		
//		RegisterResponse response = null;
//		
//		Plivo.init(config.getAuthId(), config.getAuthToken());
//		
//        try {
//        	SessionCreateResponse sessionResponse = VerifySession.creator(
//                    config.getVerifyAppUuid(), 
//                    mobile, "sms", "", "POST") 
//                    .create();
//        
//            response = RegisterResponse.builder()
//            		.mobile(mobile)
//            		.sessionUUID(sessionResponse.getSessionUuid())
//            		.build();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            response = RegisterResponse.builder()
//            		.mobile(mobile)
//            		.sessionUUID("Unable to send OTP")
//            		.build();
//            return response;
//        }		
//		return response;	
//	}
//	
//
//	public RegisterResponse sendOTP(OtpRequest request) { 	
//		
//		RegisterResponse response = null;
//		User user = null;
//		
//		Plivo.init(config.getAuthId(), config.getAuthToken());
//		
//		try {
//			if(request.getEmail().isEmpty())
//				user = repo.findByMobileAndRole(request.getMobile(), request.getRole()).orElseThrow(); 
//			else
//				user = repo.findByEmailAndRole(request.getEmail(), request.getRole()).orElseThrow();
//			
//			SessionCreateResponse sessionResponse = VerifySession.creator(
//                    config.getVerifyAppUuid(), 
//                    user.getMobile(), "sms", "", "POST") 
//                    .create();
//        
//            response = RegisterResponse.builder()
//            		.mobile(user.getMobile())
//            		.sessionUUID(sessionResponse.getSessionUuid())
//            		.build();
//            return response;
//		 }		 
//		catch (Exception e) {
//			return response;
//		}	
//	}
//	
//	@Transactional
//	public boolean validateOTP(OtpValidateRequest request) throws DataAccessException {
//	
//		Plivo.init(config.getAuthId(), config.getAuthToken());
//		
//		try{
//			SessionCreateResponse response = VerifySession.validation(
//					request.getSessionUUID(), request.getOtp()) // Validation
//					.create();
//				
//			if(response.getMessage().equals("session validated successfully")) {
//				User user = repo.findByEmailAndRole(request.getEmail(), request.getRole()).orElse(null);
//				if(user == null)
//					user = repo.findByMobileAndRole(request.getMobile(), request.getRole()).orElseThrow();
//				user.setVerified(true);
//				repo.save(user);
//				return true;
//			}
//			else
//				return false;
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		    return false;
//		} 	
//	}

	
}
