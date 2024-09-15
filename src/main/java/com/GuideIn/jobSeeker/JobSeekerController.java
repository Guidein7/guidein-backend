	package com.GuideIn.jobSeeker;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GuideIn.jobs.Job;
import com.GuideIn.jobs.JobDTO;
import com.GuideIn.jobs.JobService;
import com.GuideIn.referral.ReferralRequestDTO;
import com.GuideIn.subscription.SubmitSubscriptionDTO;
import com.GuideIn.subscription.SubscriptionRequest;
import com.GuideIn.subscription.SubscriptionService;
import com.GuideIn.subscription.SubscritionResponse;

@RestController
@RequestMapping("/api/guidein/v1/job_seeker")
public class JobSeekerController {
	
	@Autowired
	JobService service;
	
	@Autowired
	JobSeekerService jobSeekerService;
	
	@Autowired
	SubscriptionService subscriptionService;
	
	@GetMapping("/postedjobs/{email}")
	public ResponseEntity<List<JobDTO>> getPostedJobs(@PathVariable String email ){
		return new ResponseEntity<>(service.fetchPostedJobs(email),HttpStatus.OK);
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
	
	@GetMapping("/checkProfile/{email}")
	public ResponseEntity<String> checkProfile(@PathVariable String email){
		if(jobSeekerService.checkProfile(email))
			return ResponseEntity.ok("profile available");
		else return new ResponseEntity<>("profile Un-available", HttpStatus.NO_CONTENT); //204
	}
	
	@PostMapping("/saveJob")
	public ResponseEntity<String> saveJob(@RequestParam("email") String email, @RequestParam("jobId") Long jobId){
		if(jobSeekerService.saveJob(email, jobId))
			return ResponseEntity.ok("job saved successfully");
		else return new ResponseEntity<>("unable to save job", HttpStatus.FORBIDDEN);
	}
	
	@GetMapping("/savedJobs/{email}")
	public ResponseEntity<List<JobDTO>> getSavedJobs(@PathVariable String email){
		return new ResponseEntity<>(jobSeekerService.getSavedJobs(email),HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteJob")
	public ResponseEntity<String> deleteSavedJob(@RequestParam("email") String email, @RequestParam("jobId") Long jobId){
		if(jobSeekerService.deleteSavedJob(email, jobId))
			return ResponseEntity.ok("job deleted successfully");
		else return new ResponseEntity<>("unable to delete savedjob", HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/requestReferral")
	public ResponseEntity<String> requestReferral(@ModelAttribute ReferralRequestDTO request) throws IOException{
		if(jobSeekerService.requestReferral(request))
			return ResponseEntity.ok("Referral requested successfully");
		else return new ResponseEntity<>("you already used all the referral credits", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getAppliedReferralStatus/{email}")
	public ResponseEntity<List<AppliedReferralStatusDTO>> getAppliedReferralStatus(@PathVariable String email){	
		 return ResponseEntity.ok(jobSeekerService.getAppliedReferralStatus(email));
		
	}
	
	@GetMapping("/getAppliedReferral/{referralId}")
	public ResponseEntity<AppliedReferralDTO> getAppliedReferral(@PathVariable Long referralId){
		AppliedReferralDTO response = jobSeekerService.getAppliedReferral(referralId);
		if(response != null)
			return ResponseEntity.ok(response);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	}
	
	@PostMapping("/subscribe")
	public ResponseEntity<SubscritionResponse> subscribe(@RequestBody SubscriptionRequest request){
		SubscritionResponse response = subscriptionService.subscribe(request);
		if(response.getKey() == "Your current plan is already active")
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		else if(response.getKey() != null)
			return ResponseEntity.ok(response);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	}
	
	@PostMapping("/submitSubscription")
	public ResponseEntity<String> submitSubscription(@RequestBody SubmitSubscriptionDTO request){
		if(subscriptionService.submitSubscription(request))
			return ResponseEntity.ok("Subscription successfully verified and submitted");
		return new ResponseEntity<>("Unable to add the subscription",HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/checkActiveSubscription/{email}")
	public ResponseEntity<String> checkActiveSubscription(@PathVariable String email){
		if(subscriptionService.checkActiveSubscription(email))
			return ResponseEntity.ok("you currently have an active subscription");
		else return new ResponseEntity<>("you currently dont have an active subscription",HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/getDashboardDetails/{email}")
	public ResponseEntity<DashboardDTO> getDashboardDetails(@PathVariable String email){
		return ResponseEntity.ok(jobSeekerService.getDashboardDetails(email));
	}
	
}
