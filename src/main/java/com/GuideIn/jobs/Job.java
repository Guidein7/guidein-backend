package com.GuideIn.jobs;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@SequenceGenerator(name = "job_seq", sequenceName = "job_sequence", initialValue = 10000000, allocationSize = 1)
public class Job {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_seq")
	Long jobId;
	String jobTitle;
	String companyName;
	String jobLocation;
	String workMode;
	String jobType;
	String jobDescriptionLink;
	String educationLevel;
	String experienceRequired;
	String jobPostedBy;
	@Column(nullable = false)
	String postedOn;
	boolean enabled;
	
	@PrePersist
	protected void onCreate() {
		postedOn = LocalDateTime.now().withNano(0).toString();
	}

}
