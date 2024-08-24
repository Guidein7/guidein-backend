package com.GuideIn.auth;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GuideIn.plivo.DLTdetails;
import com.GuideIn.plivo.PlivoMessageService;
import com.GuideIn.user.Role;
import com.GuideIn.user.User;
import com.GuideIn.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OtpService1 {
	
	@Autowired
	OTPRepository otpRepository;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PlivoMessageService messageService;
	
	public boolean sendOTP(String mobile, Role role) {
		
		OTP otp = generateOTP(mobile, role);
		
		if(otp != null) {  //checks OTP details saved in database
			Boolean msgStatus = messageService.sendOTPMessage(DLTdetails.OTP, mobile, otp.getOtp());
			if(msgStatus) // checks message sent successfully
				return true;
			else return false;
		}
		
		else return false;
	}
	
	public boolean sendOTP(OtpRequest request) {
		
		User user = null;
		 
		try {
			if(request.getEmail().isEmpty())
				user = userRepo.findByMobileAndRole(request.getMobile(), request.getRole()).orElseThrow(); 
			else
				user = userRepo.findByEmailAndRole(request.getEmail(), request.getRole()).orElseThrow();
		 }		 
		catch (Exception e) {
			return false;
		}
		
		OTP otp = generateOTP(user.getMobile(), user.getRole());
		
		if(otp != null) {  //checks OTP details saved in database
			Boolean msgStatus = messageService.sendOTPMessage(DLTdetails.OTP, user.getMobile(), otp.getOtp());
			if(msgStatus) // checks message sent successfully
				return true;
			else return false;
		}
		
		else return false;
	}
	
	@Transactional
	public OTP generateOTP(String mobile, Role role) {
		OTP otp = null;
        try {
			otp = otpRepository.findByMobileAndRole(mobile, role).orElse(new OTP());
			otp.setMobile(mobile);
			otp.setOtp(generateRandomOTP());
			otp.setExpiryTime(LocalDateTime.now().plusMinutes(3));
			otp.setRole(role);
     
			otpRepository.save(otp);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        return otp;
    }

    public boolean validateOTP(OtpValidateRequest request) {
    	boolean status = false;
    	User user = null;
		try {
			
			if(request.getEmail().isEmpty())
				user = userRepo.findByMobileAndRole(request.getMobile(), request.getRole()).orElse(null); 
			else
				user = userRepo.findByEmailAndRole(request.getEmail(), request.getRole()).orElse(null);
		 	
		 	if(user == null)
		 		return false;
		 	
		 	status = otpRepository.findByMobileAndOtpAndRoleAndExpiryTimeAfter(
					user.getMobile(), request.getOtp(), user.getRole(), LocalDateTime.now()).isPresent();
		 	
		 	if(status == true) {
		 		user.setVerified(true);
		 		userRepo.save(user);
		 	}	
				
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
        return status;
    }
    
    @Transactional
    public void cleanUpExpiredOTPs() {
        otpRepository.deleteByExpiryTimeBefore(LocalDateTime.now());
    }

    private String generateRandomOTP() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

}
