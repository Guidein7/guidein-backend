package com.GuideIn.jobPoster;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.GuideIn.jobs.JobService;
import com.GuideIn.jobs.JobUpdateRequest;
import com.GuideIn.referral.ReferralSubmitDTO;

@RestController
@RequestMapping("/api/guidein/v1/job_poster")
public class JobPosterController {
	
	@Autowired
	JobService service;
	
	@Autowired
	JobPosterService jobPosterService;
	
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
	
	@GetMapping("/postedjobs/{postedBy}")
	public ResponseEntity<List<Job>> getPostedJobs(@PathVariable String postedBy){
		return new ResponseEntity<>(service.getPostedJobs(postedBy),HttpStatus.OK);
	}
	
	@PutMapping("/disablejob/{jobId}")
	public ResponseEntity<String> disableJob(@PathVariable Long jobId){
		if(service.disableJob(jobId))
			return ResponseEntity.ok("job disabled successfully");
		return new ResponseEntity<>("unable to disable job", HttpStatus.FORBIDDEN);
	}
	
	@PutMapping("/enablejob/{jobId}")
	public ResponseEntity<String> enableJob(@PathVariable Long jobId){
		if(service.enableJob(jobId))
			return ResponseEntity.ok("job enabled successfully");
		return new ResponseEntity<>("unable to enable job", HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/saveProfile")
	public ResponseEntity<String> saveProfile(@RequestBody JobPoster request){	
		if(jobPosterService.saveProfile(request))
			return ResponseEntity.ok("JobPoster profile saved successfully");
		else return new ResponseEntity<>("unable to save JobPoster profile", HttpStatus.FORBIDDEN);
	}
	
	@GetMapping("/getProfile/{email}")
	public ResponseEntity<JobPoster> getProfile(@PathVariable String email){
		JobPoster jobPoster = jobPosterService.getProfile(email);
		if(jobPoster != null)
			return ResponseEntity.ok(jobPoster);
		else return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); //204
	}
	
	@PutMapping("/updateProfile")
	public ResponseEntity<String> updateProfile(@RequestBody JobPoster request){
		if(jobPosterService.updateProfile(request))
			return ResponseEntity.ok("JobPoster profile updated successfully");
		else return new ResponseEntity<>("unable to update JobPoster profile", HttpStatus.FORBIDDEN);
	}
	
	@GetMapping("/getRequestedReferrals/{email}")
	public ResponseEntity<List<ReferralsDTO>> getRequestedReferrals(@PathVariable String email){
		return new ResponseEntity<>(jobPosterService.getRequestedreferrals(email),HttpStatus.OK);
	}
	
	@GetMapping("/getReferralRequest/{referralId}")
	public ResponseEntity<CandidateAndJobDetailsDTO> getReferralRequest(@PathVariable Long referralId){
		CandidateAndJobDetailsDTO candidateAndjobDetails =  jobPosterService.getReferralRequest(referralId);
		if(candidateAndjobDetails != null)
			return ResponseEntity.ok(candidateAndjobDetails);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	}
	
	@PostMapping("/rejectReferral")
	public ResponseEntity<String> rejectReferral(@RequestBody RejectReferralDTO request){
		if(jobPosterService.rejectReferral(request))
			return ResponseEntity.ok("Referral rejected successfully");
		return new ResponseEntity<>("unable to reject referral", HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/submitReferral")
	public ResponseEntity<String> submitReferral(@ModelAttribute ReferralSubmitDTO request) throws IOException {
		if(jobPosterService.submitReferral(request))
			return ResponseEntity.ok("Referral submitted successfully for verification");
		return new ResponseEntity<>("unable to submit referral for verification", HttpStatus.FORBIDDEN);
	}
	
	@GetMapping("/getAllReferredStatus/{email}")
	public ResponseEntity<List<ReferralsDTO>> getALLReferredStatus(@PathVariable String email){
		return new ResponseEntity<>(jobPosterService.getALLReferredStatus(email),HttpStatus.OK);
	}
	
	@GetMapping("/getReferredStatus/{referralId}")
	public ResponseEntity<CandidateAndJobDetailsDTO> getReferredStatus(@PathVariable Long referralId){
		CandidateAndJobDetailsDTO candidateAndjobDetails =  jobPosterService.getReferralRequest(referralId);
		if(candidateAndjobDetails != null)
			return ResponseEntity.ok(candidateAndjobDetails);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	}
}
