package com.GuideIn.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OTPCleanupScheduler {
	
	@Autowired
	OtpService1 otpService;
	
	// Runs every 5 minutes to delete expired OTPs
	@Scheduled(fixedRate = 600000)  // 600,000 ms = 10 minutes
    public void scheduleOTPDeletion() {
        otpService.cleanUpExpiredOTPs();
    }
	
}
