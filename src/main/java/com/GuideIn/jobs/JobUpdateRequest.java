package com.GuideIn.jobs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobUpdateRequest {
	
	Long jobId;
	String jobTitle;
	String companyName;
	String jobLocation;
	String workMode;
	String jobType;
	String jobDescriptionLink;
	String educationLevel;
	String experienceRequired;
}
