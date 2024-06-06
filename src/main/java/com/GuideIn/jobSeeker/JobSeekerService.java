package com.GuideIn.jobSeeker;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GuideIn.jobs.Job;
import com.GuideIn.jobs.JobRepository;
import com.GuideIn.referral.Referral;
import com.GuideIn.referral.ReferralRepository;
import com.GuideIn.referral.ReferralRequestDTO;
import com.GuideIn.referral.ReferralStatus;

import jakarta.transaction.Transactional;

@Service
public class JobSeekerService {
	
	@Autowired
	JobSeekerRepo repo;
	
	@Autowired
	SavedJobRepo savedJobRepo;
	
	@Autowired
	JobRepository jobRepo;

	@Autowired
	ReferralRepository referralRepo;
	
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
	
	public boolean checkProfile(String email) {
		try {
			repo.findByEmail(email).orElseThrow();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
	
//	public ReferralStatusDTO getReferralStatus(String requestedBy, Long jobId) {
//		ReferralStatusDTO referralStatusDTO = new ReferralStatusDTO(ReferralStatus.UN_REQUESTED);
//		try {
//			Referral referral =	referralRepo.findByRequestedByAndJobId(requestedBy, jobId).orElseThrow();
//			referralStatusDTO.setReferralStatus(referral.getStatus());
//		} catch (Exception e) {
//			return referralStatusDTO;
//		}
//		return referralStatusDTO;
//	}

	@Transactional
	public boolean requestReferral(ReferralRequestDTO request) {
		Referral referral = null;
		try {
			referral = referralRepo.findByRequestedByAndJobId(request.getRequestedBy(), request.getJobId())
					.orElse(new Referral());
			referral.setJobId(request.getJobId());
			referral.setRequestedBy(request.getRequestedBy());
			referral.setCandidateResume(request.getResume().getBytes());
			referral.setJobPostedBy(request.getJobPostedBy());
			referral.setStatus(ReferralStatus.REQUESTED);
	
			referralRepo.save(referral);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<AppliedReferralStatusDTO> getAppliedReferralStatus(String email) {
		List<AppliedReferralStatusDTO> appliedReferralStatusDTO = new ArrayList<>();
		
		try {
			List<Referral> referrals = referralRepo.findAllByRequestedBy(email);		
			for(Referral referral : referrals) {
				Job job = jobRepo.findById(referral.getJobId()).orElseThrow();
				if(referral.getStatus() == ReferralStatus.IN_VERIFICATION)
					referral.setStatus(ReferralStatus.IN_PROGRESS);
				if(referral.getStatus() == ReferralStatus.VERIFICATION_FAILED)
					referral.setStatus(ReferralStatus.REJECTED);
				AppliedReferralStatusDTO appliedReferral = AppliedReferralStatusDTO.builder()
						.jobTitle(job.getJobTitle())
						.companyName(job.getCompanyName())
						.jobLocation(job.getJobLocation())
						.workMode(job.getWorkMode())
						.experienceRequired(job.getExperienceRequired())
						.requstedOn(referral.getRequstedOn())
						.currentStatus(referral.getStatus())
						.reason(referral.getReason())
						.build();
				appliedReferralStatusDTO.add(appliedReferral);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return appliedReferralStatusDTO;
		}	
		return appliedReferralStatusDTO;	
	}
	
}
