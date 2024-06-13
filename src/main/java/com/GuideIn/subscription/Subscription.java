package com.GuideIn.subscription;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor 
@Entity
public class Subscription {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String email;
	private String name;
	private String mobile;
	@Column(unique = true)
	private String paymentId;
	private String orderId;
	private String razorPaySignature;
	@Enumerated(EnumType.STRING)
	private Plan plan;
	private int totalReferralCredits;
	private int availableReferralCredits;
	private int usedReferralCredits;
	private Boolean active;
	@Column(nullable = false)
	private String subscribedOn;
	
	@PreUpdate
	protected void deActivatePlan() {
		if(this.usedReferralCredits == this.totalReferralCredits)
			this.active = false;
	}
	
	@PrePersist
	protected void onCreate() {
		subscribedOn = LocalDateTime.now().withNano(0).toString();
	}
}
