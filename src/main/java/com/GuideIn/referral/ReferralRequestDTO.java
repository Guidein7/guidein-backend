package com.GuideIn.referral;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReferralRequestDTO implements Serializable {
	private Long jobId;
	private String jobPostedBy;
	private String requestedBy;
	private MultipartFile resume;
}
