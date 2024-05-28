package com.GuideIn.jobPoster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class JobPosterService {
	
	@Autowired
	JobPosterRepo repo;
	
	@Transactional
	public boolean saveProfile(JobPoster jobPoster) {
		try {
			repo.save(jobPoster);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public JobPoster getProfile(String email) {
		JobPoster jobPoster = null;
		jobPoster = repo.findByEmail(email).orElse(null);
		return jobPoster;

	}
	
	@Transactional
	public boolean updateProfile(JobPoster request) {
		try {
			JobPoster jobPoster = repo.findByEmail(request.getEmail()).orElseThrow();
			jobPoster.setCurrentCompany(request.getCurrentCompany());
			jobPoster.setTotalExperience(request.getTotalExperience());
			jobPoster.setLinkedInUrl(request.getLinkedInUrl());
			repo.save(jobPoster);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
}
