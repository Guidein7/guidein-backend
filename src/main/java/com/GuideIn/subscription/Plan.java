package com.GuideIn.subscription;

public enum Plan {
	
    STANDARD(2, 1),
    PREMIUM(5, 2899),
    ULTIMATE(10, 5399);

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
