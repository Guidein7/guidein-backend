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
public class AppliedReferralStatusDTO {
	private Long referralId;
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
	private String comments;
}
