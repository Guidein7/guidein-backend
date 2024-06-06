package com.GuideIn.jobPoster;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RejectReferralDTO {
	private Long referralId;
	private String reason;
	private String comments;
}
