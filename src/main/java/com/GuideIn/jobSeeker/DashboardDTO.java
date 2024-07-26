package com.GuideIn.jobSeeker;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {
	
	private int totalReferrals;
	private int availableReferrals;
	private int referralsRequested;
	private int referralsInProgress;
	private int referralsSuccessful;
	private int totalReferralSuccessful;
	
	private List<PlanDetails> planHistory;
}
