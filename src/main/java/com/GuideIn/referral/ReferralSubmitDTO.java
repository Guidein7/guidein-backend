package com.GuideIn.referral;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor 
public class ReferralSubmitDTO implements Serializable {
	
	private long referralId;
	private String dateOfReferral;
	private String methodOfReferral;
	private String comments;
	private MultipartFile proof;
}
