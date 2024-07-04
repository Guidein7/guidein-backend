package com.GuideIn.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReferralApproveRequestDTO {
	private Long referralId;
	private String requestedBy;
	private String postedBy;
}
