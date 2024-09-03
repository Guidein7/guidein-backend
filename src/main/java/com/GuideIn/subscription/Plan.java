package com.GuideIn.subscription;

public enum Plan {
	
    STANDARD(1, 399),
    PREMIUM(2, 599),
    ULTIMATE(3, 899);

	private final int totalReferralCredits;
    private final double price;
    
    Plan(int totalCredits, double price) {
        this.totalReferralCredits = totalCredits;
        this.price = price;
    }

    public int getTotalCredits() {
        return totalReferralCredits;
    }

    public double getPrice() {
        return price;
    }

}
