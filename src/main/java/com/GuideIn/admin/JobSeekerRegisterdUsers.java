package com.GuideIn.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobSeekerRegisterdUsers {
	private String name;
	private String mobile;
	private String email;
	private boolean isSubscribed;
}
