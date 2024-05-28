package com.GuideIn.jobSeeker;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GuideIn.jobs.Job;
import com.GuideIn.jobs.JobRepository;

import jakarta.transaction.Transactional;

@Service
public class JobSeekerService {
	
	@Autowired
	JobSeekerRepo repo;
	
	@Autowired
	SavedJobRepo savedJobRepo;
	
	@Autowired
	JobRepository jobRepo;
	
	@Transactional
	public boolean saveProfile(JobSeekerProfileDTO request) {
		
		JobSeeker jobSeeker = new JobSeeker();
		try {
			jobSeeker.setName(request.getName());
			jobSeeker.setEmail(request.getEmail());
			jobSeeker.setMobile(request.getMobile());
			jobSeeker.setCurrentStatus(request.getCurrentStatus());
			jobSeeker.setExperience(request.getExperience());
			jobSeeker.setLinkedInUrl(request.getLinkedInUrl());
			jobSeeker.setResume(request.getResume().getBytes());
			repo.save(jobSeeker);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Transactional
	public boolean updateProfile(JobSeekerProfileDTO request) {
		try {
			JobSeeker jobSeeker = repo.findByEmail(request.getEmail()).orElseThrow();
			jobSeeker.setCurrentStatus(request.getCurrentStatus());
			jobSeeker.setExperience(request.getExperience());
			jobSeeker.setLinkedInUrl(request.getLinkedInUrl());
			jobSeeker.setResume(request.getResume().getBytes());
			repo.save(jobSeeker);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public JobSeeker getProfile(String email) {
		JobSeeker jobSeeker = null;
		jobSeeker = repo.findByEmail(email).orElse(null);
		return jobSeeker;
	}
	
	@Transactional
	public boolean saveJob(String email, Long jobId) {
		SavedJob job = null;
		try {
			job = savedJobRepo.findByEmailAndJobId(email, jobId).orElse(new SavedJob());
			job.setEmail(email);
			job.setJobId(jobId);
			savedJobRepo.save(job);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Job> getSavedJobs(String email) {
		List<Job> savedJobs = new ArrayList<>();
		List<Long> jobIds = savedJobRepo.findJobIdsByEmail(email);
		for(Long jobId : jobIds) {	
			Job job = jobRepo.findByJobIdAndEnabled(jobId, true).orElse(null);
			if(job != null)
				savedJobs.add(job);
		}
		return savedJobs;
	}
	
	@Transactional
	public boolean deleteSavedJob(String email, Long jobId) {
		try {
			savedJobRepo.deleteByEmailAndJobId(email, jobId);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
