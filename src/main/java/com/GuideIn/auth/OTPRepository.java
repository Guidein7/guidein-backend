package com.GuideIn.auth;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GuideIn.user.Role;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
	
    void deleteByExpiryTimeBefore(LocalDateTime currentTime);

    // Find OTP by mobile, otp, role, and ensure it's not expired
    Optional<OTP> findByMobileAndOtpAndRoleAndExpiryTimeAfter(
    		String mobile, String otp, Role role, LocalDateTime currentTime);
    
    Optional<OTP> findByMobileAndRole(String mobile, Role role);

}
