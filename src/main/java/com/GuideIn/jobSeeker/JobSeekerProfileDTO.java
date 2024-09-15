package com.GuideIn.jobSeeker;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor 
public class JobSeekerProfileDTO implements Serializable{
	
	private String name;
	private String email;
	private String mobile;
	private String currentStatus;
	private String experience;
	private String currentRole;
	private String currentCompany;
	private String preferredRole;
	private String linkedInUrl;
	private MultipartFile resume;

}
