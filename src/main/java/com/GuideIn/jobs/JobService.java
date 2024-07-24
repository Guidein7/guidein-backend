package com.GuideIn.jobs;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GuideIn.jobPoster.JobPoster;
import com.GuideIn.jobPoster.JobPosterRepo;
import com.GuideIn.jobSeeker.JobSeeker;
import com.GuideIn.jobSeeker.SavedJob;
import com.GuideIn.jobSeeker.SavedJobRepo;
import com.GuideIn.referral.Referral;
import com.GuideIn.referral.ReferralRepository;
import com.GuideIn.referral.ReferralRequestDTO;
import com.GuideIn.referral.ReferralStatus;

import jakarta.transaction.Transactional;

@Service
public class JobService {
	
	@Autowired
	JobRepository repo;
	
	@Autowired
	ReferralRepository referralRepo;
	
	@Autowired
	JobPosterRepo jobPosterRepo;
	
	@Autowired
	SavedJobRepo savedJobRepo;
	
	@Transactional
	public boolean saveJob(Job job) {	
		job.setEnabled(true);		
		try {
			repo.save(job);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Transactional
	public boolean updateJob(JobUpdateRequest request) {
		Job job = null;
		try {
			job = repo.findById(request.getJobId()).orElseThrow();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		job.setJobTitle(request.getJobTitle());
		job.setCompanyName(request.getCompanyName());
		job.setJobLocation(request.getJobLocation());
		job.setWorkMode(request.getWorkMode());
		job.setJobType(request.getJobType());
		job.setJobDescriptionLink(request.getJobDescriptionLink());
		job.setEducationLevel(request.getEducationLevel());
		job.setExperienceRequired(request.getExperienceRequired());
		repo.save(job);
		return true;
	}
	
	@Transactional
	public boolean disableJob(Long jobId) {
		Job job = null;
		try {
			job = repo.findById(jobId).orElseThrow();
			job.setEnabled(false);
			repo.save(job);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Transactional
	public boolean enableJob(Long jobId) {
		Job job = null;
		try {
			job = repo.findById(jobId).orElseThrow();
			job.setEnabled(true);
			repo.save(job);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<Job> getPostedJobs(String postedBy){ //employee posted jobs		
		List<Job> jobs = repo.findAllByJobPostedBy(postedBy);
		for(Job job : jobs) {
			job.setPostedOn(getTimeAgo(job.getPostedOn()));
		}	
		return jobs;
	}
	
	public List<JobDTO> fetchPostedJobs(String email){ //all posted jobs - for jobSeeker
		List<Job> jobs = repo.findByEnabled(true);
		List<JobDTO> listOfJobDTO = new ArrayList<>(); 
		for(Job job : jobs) {
			String jobPosterName;
			ReferralStatus status = ReferralStatus.UN_REQUESTED;
			Boolean isSaved = false;
			try {
				JobPoster jobPoster = jobPosterRepo.findByEmail(job.getJobPostedBy()).orElseThrow();
				jobPosterName = jobPoster.getName();
				isSaved = savedJobRepo.findByEmailAndJobId(email, job.getJobId()).isPresent();
			} catch (Exception e) {
				e.printStackTrace();
				return listOfJobDTO;
			}
			Referral referral =  referralRepo.findByRequestedByAndJobId(email, job.getJobId()).orElse(null);
			if(referral != null) {
				if(referral.getStatus() == ReferralStatus.VERIFICATION_FAILED)
					status = ReferralStatus.REJECTED;
				if(referral.getStatus() == ReferralStatus.IN_VERIFICATION)
					status = ReferralStatus.IN_PROGRESS;
				else
					status = referral.getStatus();
			}
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
					.jobPosterName(jobPosterName)
					.postedOn(getTimeAgo(job.getPostedOn()))
					.status(status)
					.saved(isSaved)
					.enabled(job.isEnabled())
					.build();
			listOfJobDTO.add(jobDTO);
		}
		return listOfJobDTO;
	}	 
	
	public String getTimeAgo(String postedAt) {
		
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
			LocalDateTime parsedDateAndTime = LocalDateTime.parse(postedAt,formatter);
		
		 	LocalDateTime now = LocalDateTime.now();
	        Duration duration = Duration.between(parsedDateAndTime, now);

	        if (duration.toMinutes() < 1) {
	            return "just now";
	        } else if (duration.toMinutes() == 1) {
	            return "1 min ago";
	        } else if (duration.toMinutes() < 60) {
	            return duration.toMinutes() + " mins ago";
	        } else if (duration.toHours() == 1) {
	            return "1 hr ago";
	        } else if (duration.toHours() < 24) {
	            return duration.toHours() + " hrs ago";
	        } else if (duration.toDays() == 1) {
	            return "1 day ago";
	        } else if (duration.toDays() < 7) {
	            return duration.toDays() + " days ago";
	        } else if (duration.toDays() < 30) {
	            return (duration.toDays() / 7) + " weeks ago";
	        } else {
	            return (duration.toDays() / 30) + " months ago";
	        }
	}

	
}
