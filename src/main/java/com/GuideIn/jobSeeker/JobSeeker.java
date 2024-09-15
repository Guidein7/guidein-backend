package com.GuideIn.jobSeeker;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor 
@Entity
public class JobSeeker {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String name;
	@Column(unique = true)
	String email;
	String mobile;
	String currentStatus;
	String experience;
	String currentRole;
	String currentCompany;
	String preferredRole;
	String linkedInUrl;
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	byte[] resume;
	
}
