package com.GuideIn.subscription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmitSubscriptionDTO {
	
	private String order_id;
	private String payment_id;
	private String razorPaySignature;
	private Plan plan;
	private String email;
	private String name;
	private String contact;
	
}
