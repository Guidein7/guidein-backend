package com.GuideIn.admin;

import com.GuideIn.referral.ReferralStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReferralsDTO {
	private Long refferalId;
	private String jobRole;
	private String requestedBy;
	private String mobileOfrequestedBy;
	private String jobPostedBy;
	private String mobileOfJobPostedBy;
	private String requestedOn;
	private ReferralStatus status;
}
