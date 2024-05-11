package com.GuideIn.auth;

import com.GuideIn.user.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	private Integer id;
	private String username;
	private String email;
	private String mobile;
	private String password;
	private Role role;
}
