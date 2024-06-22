package com.GuideIn.wallet;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO {
	
	private String email;
	private int totalReferrals;
	private int totalEarned;
	private int amountWithdrawn;
	private int withdrawInProgress;
	private int currentBalance;
	private String upiId;
	
	private List<WalletTransactionDetail> transactionHistory;
}
