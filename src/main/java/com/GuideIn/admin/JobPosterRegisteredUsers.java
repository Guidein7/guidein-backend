package com.GuideIn.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPosterRegisteredUsers {
	private String name;
	private String mobile;
	private String email;
	private Long totalJobPosted;
}
