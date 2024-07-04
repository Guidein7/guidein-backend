package com.GuideIn.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletsDTO {
	
	private String email;
	private String name;
	private String mobile;
	private int totalReferrals;
	private int totalEarned;
	private int amountWithdrawn;
	private int withdrawInProgress;
	
}
