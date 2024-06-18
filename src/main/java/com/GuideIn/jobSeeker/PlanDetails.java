package com.GuideIn.jobSeeker;

import com.GuideIn.subscription.Plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanDetails {
	
	private Plan plan;
	private String subscrideOn;
	private String transactionId;
	private Boolean isActive;
	
}
