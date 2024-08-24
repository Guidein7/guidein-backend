package com.GuideIn.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDetailsDTO {
	
	private Long totalRegisteredJobSeekers;
	private Long totalSubscribedUsers;
	private Long totalSubscriptions;
	private Long totalRegisteredJobPosters;
	private Long totalJobPosted;
	private Long totalDisabledJobs;

}
