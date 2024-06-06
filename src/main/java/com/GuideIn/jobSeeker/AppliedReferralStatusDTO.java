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
	private String jobTitle;
	private String companyName;
	private String jobLocation;
	private String workMode;
	private String experienceRequired;
	private String requstedOn;
	private ReferralStatus currentStatus;
	private String reason;
}
