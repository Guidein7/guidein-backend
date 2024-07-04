package com.GuideIn.admin;

import com.GuideIn.subscription.Plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobSeekerSubscribedUsers {
	private String name;
	private String mobile;
	private String email;
	private Plan plan;
	private String subscribedOn;
}
