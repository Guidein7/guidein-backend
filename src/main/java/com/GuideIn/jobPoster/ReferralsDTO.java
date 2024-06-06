package com.GuideIn.jobPoster;

import org.springframework.stereotype.Component;

import com.GuideIn.referral.ReferralStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReferralsDTO {
	
	private String candidateName;
	private String candidateExperience;
	private String referralFor;
	private Long referralId;
	private String requestedOn;
	private ReferralStatus status;
	private String reason;
	
}
