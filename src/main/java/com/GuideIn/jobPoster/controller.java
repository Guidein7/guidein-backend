package com.GuideIn.jobPoster;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GuideIn.jobs.Job;
import com.GuideIn.jobs.JobService;
import com.GuideIn.jobs.JobUpdateRequest;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/guidein/v1/job_poster")
public class controller {
	
	@Autowired
	JobService service;
	
	@PostMapping("/savejob")
	public ResponseEntity<String> saveJob(@RequestBody Job job){
		
		if (service.saveJob(job))
			return ResponseEntity.ok("job saved successfully");
		return new ResponseEntity<>("unable to store job", HttpStatus.FORBIDDEN);
	}
	
	@PutMapping("/updatejob")
	public ResponseEntity<String> updateJob(@RequestBody JobUpdateRequest request){
		if(service.updateJob(request))
			return ResponseEntity.ok("job updated successfully");
		return new ResponseEntity<>("unable to update job", HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/postedjobs")
	public ResponseEntity<List<Job>> getPostedJobs(@RequestBody PostedJobRequest postedBy){
		return new ResponseEntity<>(service.getPostedJobs(postedBy.getPostedBy()),HttpStatus.OK);
	}
}
