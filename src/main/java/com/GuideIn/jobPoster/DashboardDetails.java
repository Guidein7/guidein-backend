package com.GuideIn.jobPoster;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDetails {
	
	private Long totalJobPosted;
	private Long activeJobs;
	private Long jobsExpired;
	private Long totalReferralRequests;
	private Long referralSuccessful;
	private Long referralRejected;
	
}
