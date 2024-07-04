package com.GuideIn.jobs;

import com.GuideIn.referral.ReferralStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {
	Long jobId;
	String jobTitle;
	String companyName;
	String jobLocation;
	String workMode;
	String jobType;
	String jobDescriptionLink;
	String educationLevel;
	String experienceRequired;
	String jobPostedBy;
	String jobPosterName;
	String postedOn;
	ReferralStatus status;
	Boolean enabled;
	Boolean disabledByAdmin;
	Boolean saved;
}
