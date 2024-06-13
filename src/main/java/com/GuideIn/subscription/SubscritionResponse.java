package com.GuideIn.subscription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscritionResponse {
	
	private String key;
	private String orderId;
	private String email;
	private String name;
	private String contact;
	
}
