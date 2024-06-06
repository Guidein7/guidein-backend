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
public class CandidateAndJobDetailsDTO {
	
	private String candidateName;
	private String candidateEmail;
	private String candidateMobile;
	private String candidateExperience;
	private String candidateLinkedInUrl;
	private String referralFor;
	private Long referralId;
	private Long jobId;
	private String company;
	String jobLocation;
	String workMode;
	private String experienceRequired;
	private String jobPostedOn;
	private ReferralStatus referralStatus;
	private byte[] candidateResume;
	
}
