package com.GuideIn.auth;

import com.GuideIn.user.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpValidateRequest {
	
	private String email;
	private String mobile;
	private Role role;
	private String otp;

}
