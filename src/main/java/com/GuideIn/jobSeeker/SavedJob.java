package com.GuideIn.jobSeeker;

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
public class SavedJob {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String email;
	Long jobId;

}
