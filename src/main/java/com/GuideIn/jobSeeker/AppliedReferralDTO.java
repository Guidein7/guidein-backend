package com.GuideIn.jobSeeker;

import com.GuideIn.referral.ReferralStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppliedReferralDTO {
	
	private Long referralId;
	private String candidateName;
	private String candidateEmail;
	private String candidateMobile;
	private String candidateExperience;
	private Long jobId;
	private String jobTitle;
	private String companyName;
	private String jobLocation;
	private String workMode;
	private String jobType;
	private String jobDescriptionLink;
	private String experienceRequired;
	private String jobPostedBy;
	private String jobPosterName;
	private String postedOn;
	private String requstedOn;
	private ReferralStatus currentStatus;
	private String reason;
	private String dateOfReferral;
	private String methodOfReferral;
	private String comments;
	private byte[] resume;
	private byte[] proof;
	
}
