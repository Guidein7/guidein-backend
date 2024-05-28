package com.GuideIn.jobPoster;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor 
@Entity
public class JobPoster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String name;
	@Column(unique = true)
	String email;
	String mobile;
	String currentCompany;
	String totalExperience;
	String linkedInUrl;
	
}
