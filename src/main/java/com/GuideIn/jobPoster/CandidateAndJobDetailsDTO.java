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
	private String requestedOn;
	private Long referralId;
	private Long jobId;
	private String company;
	private String jobDescriptionLink;
	private String jobLocation;
	private String workMode;
	private String experienceRequired;
	private String jobPostedOn;
	private ReferralStatus referralStatus;
	private String dateOfReferral;
	private String methodOfReferral;
	private String comments;
	private byte[] proof;
	private byte[] candidateResume;
	
}
