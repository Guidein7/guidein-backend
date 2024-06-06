package com.GuideIn.referral;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor 
@Entity
@SequenceGenerator(name = "referral_seq", sequenceName = "referral_sequence", initialValue = 10000000, allocationSize = 1)
public class Referral {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "referral_seq")
	private Long referralId;
	private Long jobId;
	private String jobPostedBy;
	private String requestedBy;
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] candidateResume; 
	
	@Column(nullable = false)
	private String requstedOn;
	
	private String dateOfReferral;
	private String methodOfReferral;
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] proof;
	
	@Enumerated(EnumType.STRING)
	private ReferralStatus status;
	private String reason;
	private String comments;
	
	@PrePersist
	protected void onCreate() {
		requstedOn = LocalDate.now().toString();
	}
	
}
