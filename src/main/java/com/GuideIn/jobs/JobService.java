package com.GuideIn.jobs;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class JobService {
	
	@Autowired
	JobRepository repo;
	
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
	
	public List<Job> getPostedJobs(String postedBy){
		
		List<Job> jobs = repo.findAllByJobPostedBy(postedBy);
		for(Job job : jobs) {
			job.setPostedOn(getTimeAgo(job.getPostedOn()));
		}
		
		return jobs;
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
