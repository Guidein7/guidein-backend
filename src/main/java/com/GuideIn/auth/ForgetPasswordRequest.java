package com.GuideIn.auth;

import com.GuideIn.user.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordRequest {
	String email;
	String mobile;
	String newPassword;
	Role role;
}
