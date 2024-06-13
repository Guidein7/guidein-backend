package com.GuideIn.jobSeeker;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GuideIn.jobPoster.JobPoster;
import com.GuideIn.jobPoster.JobPosterRepo;
import com.GuideIn.jobs.Job;
import com.GuideIn.jobs.JobDTO;
import com.GuideIn.jobs.JobRepository;
import com.GuideIn.jobs.JobService;
import com.GuideIn.referral.Referral;
import com.GuideIn.referral.ReferralRepository;
import com.GuideIn.referral.ReferralRequestDTO;
import com.GuideIn.referral.ReferralStatus;
import com.GuideIn.subscription.Subscription;
import com.GuideIn.subscription.SubscriptionRepository;
import com.GuideIn.subscription.SubscriptionService;

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
	
	@Autowired
	JobPosterRepo jobPosterRepo;
	
	@Autowired
	JobService jobService;
	
	@Autowired
	SubscriptionRepository subscriptionRepo;
	
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

	public List<JobDTO> getSavedJobs(String email) {
		List<JobDTO> savedJobs = new ArrayList<>();
		List<Long> jobIds = savedJobRepo.findJobIdsByEmail(email);
		for(Long jobId : jobIds) {	
			try {
				ReferralStatus status = ReferralStatus.UN_REQUESTED;
				Job job = jobRepo.findByJobIdAndEnabled(jobId, true).orElse(null);
				if(job != null) {
					JobPoster jobPoster = jobPosterRepo.findByEmail(job.getJobPostedBy()).orElseThrow();
					Referral referral = referralRepo.findByRequestedByAndJobId(email, job.getJobId()).orElse(null);
					
					if(referral != null)
						status = referral.getStatus();
				
					var jobDTO = JobDTO.builder()
							.jobId(job.getJobId())
							.jobTitle(job.getJobTitle())
							.companyName(job.getCompanyName())
							.jobLocation(job.getJobLocation())
							.workMode(job.getWorkMode())
							.jobType(job.getJobType())
							.jobDescriptionLink(job.getJobDescriptionLink())
							.educationLevel(job.getEducationLevel())
							.experienceRequired(job.getExperienceRequired())
							.jobPostedBy(job.getJobPostedBy())
							.jobPosterName(jobPoster.getName())
							.postedOn(jobService.getTimeAgo(job.getPostedOn()))
							.status(status)
							.build();
					
						savedJobs.add(jobDTO);	
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
				return savedJobs;
			}
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

	@Transactional
	public boolean requestReferral(ReferralRequestDTO request) {
		try {
			Subscription subscription = subscriptionRepo.findByEmailAndActive(request.getRequestedBy(), true).orElseThrow();
			int availableCredits = subscription.getAvailableReferralCredits();
			if(availableCredits > 0) {
			
				Referral referral = referralRepo.findByRequestedByAndJobId(request.getRequestedBy(), request.getJobId())
						.orElse(new Referral());
				referral.setJobId(request.getJobId());
				referral.setRequestedBy(request.getRequestedBy());
				referral.setCandidateResume(request.getResume().getBytes());
				referral.setJobPostedBy(request.getJobPostedBy());
				referral.setStatus(ReferralStatus.REQUESTED);
				
				subscription.setAvailableReferralCredits(availableCredits - 1);
				
				referralRepo.save(referral);
				subscriptionRepo.save(subscription);
			}
			else return false;
			
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
				JobPoster jobPoster = jobPosterRepo.findByEmail(job.getJobPostedBy()).orElseThrow();
				if(referral.getStatus() == ReferralStatus.IN_VERIFICATION)
					referral.setStatus(ReferralStatus.IN_PROGRESS);
				if(referral.getStatus() == ReferralStatus.VERIFICATION_FAILED)
					referral.setStatus(ReferralStatus.REJECTED);
				AppliedReferralStatusDTO appliedReferral = AppliedReferralStatusDTO.builder()
						.referralId(referral.getReferralId())
						.jobTitle(job.getJobTitle())
						.companyName(job.getCompanyName())
						.jobLocation(job.getJobLocation())
						.workMode(job.getWorkMode())
						.jobType(job.getJobType())
						.jobDescriptionLink(job.getJobDescriptionLink())
						.experienceRequired(job.getExperienceRequired())
						.jobPostedBy(job.getJobPostedBy())
						.jobPosterName(jobPoster.getName())
						.postedOn(job.getPostedOn())
						.requstedOn(referral.getRequstedOn())
						.currentStatus(referral.getStatus())
						.reason(referral.getReason())
						.comments(referral.getComments())
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
