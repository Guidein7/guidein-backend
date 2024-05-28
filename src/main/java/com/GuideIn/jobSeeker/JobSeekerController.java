package com.GuideIn.jobSeeker;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.GuideIn.jobPoster.JobPoster;
import com.GuideIn.jobs.Job;
import com.GuideIn.jobs.JobService;

@RestController
@RequestMapping("/api/guidein/v1/job_seeker")
public class JobSeekerController {
	
	@Autowired
	JobService service;
	
	@Autowired
	JobSeekerService jobSeekerService;
	
	@GetMapping("/postedjobs")
	public ResponseEntity<List<Job>> getPostedJobs(){
		return new ResponseEntity<>(service.fetchPostedJobs(),HttpStatus.OK);
	} 
	
	@PostMapping("/saveProfile")
	public ResponseEntity<String> saveProfile(@ModelAttribute JobSeekerProfileDTO request) throws IOException{
		if(jobSeekerService.saveProfile(request))
			return ResponseEntity.ok("JobSeeker profile saved successfully");
		else return new ResponseEntity<>("unable to save JobSeeker profile", HttpStatus.FORBIDDEN);
	}
	
	@PutMapping("/updateProfile")
	public ResponseEntity<String> updateProfile(@ModelAttribute JobSeekerProfileDTO request) throws IOException{
		if(jobSeekerService.updateProfile(request))
			return ResponseEntity.ok("JobSeeker profile updated successfully");
		else return new ResponseEntity<>("unable to update JobSeeker profile", HttpStatus.FORBIDDEN);
	}
	
	@GetMapping("/getProfile/{email}")
	public ResponseEntity<JobSeeker> getProfile(@PathVariable String email){
		JobSeeker jobSeeker = jobSeekerService.getProfile(email);
		if(jobSeeker != null)
			return ResponseEntity.ok(jobSeeker);
		else return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); //204
	}
	
	@PostMapping("/saveJob")
	public ResponseEntity<String> saveJob(@RequestParam("email") String email, @RequestParam("jobId") Long jobId){
		if(jobSeekerService.saveJob(email, jobId))
			return ResponseEntity.ok("job saved successfully");
		else return new ResponseEntity<>("unable to save job", HttpStatus.FORBIDDEN);
	}
	
	@GetMapping("/savedJobs/{email}")
	public ResponseEntity<List<Job>> getSavedJobs(@PathVariable String email){
		return new ResponseEntity<>(jobSeekerService.getSavedJobs(email),HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteJob")
	public ResponseEntity<String> deleteSavedJob(@RequestParam("email") String email, @RequestParam("jobId") Long jobId){
		if(jobSeekerService.deleteSavedJob(email, jobId))
			return ResponseEntity.ok("job deleted successfully");
		else return new ResponseEntity<>("unable to delete savedjob", HttpStatus.FORBIDDEN);
	}
}
