package com.GuideIn.admin;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GuideIn.jobPoster.JobPosterService;
import com.GuideIn.jobPoster.RejectReferralDTO;
import com.GuideIn.jobSeeker.AppliedReferralDTO;
import com.GuideIn.jobs.JobDTO;
import com.GuideIn.jobs.JobService;
import com.GuideIn.wallet.WalletDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guidein/v1/admin")
@RequiredArgsConstructor
public class AdminController {
	
	@Autowired
	AdminService service;
	
	@Autowired
	JobService jobService;
	
	@Autowired
	JobPosterService jobPosterService;
	
	@GetMapping("/getDashboardDetails")
	public ResponseEntity<DashboardDetailsDTO> getDashboardDetails(){
		return ResponseEntity.ok(service.getDashboardDetails());
	}
	
	@GetMapping("/jobSeekerRegisteredUsers")
	public ResponseEntity<List<JobSeekerRegisterdUsers>> getJobSeekerRegisteredUsers(){
		List<JobSeekerRegisterdUsers> response = service.getJobSeekerRegisteredUsers();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/jobSeekerSubscribedUsers")
	public ResponseEntity<List<JobSeekerSubscribedUsers>> getJobSeekerSubscribedUsers(){
		List<JobSeekerSubscribedUsers> response = service.getJobSeekerSubscribedUser();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/jobPosterRegisteredUsers")
	public ResponseEntity<List<JobPosterRegisteredUsers>> getJobPosterRegisteredUsers(){
		List<JobPosterRegisteredUsers> response = service.getJobPosterRegisteredUsers();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getPostedJobs")
	public ResponseEntity<List<JobDTO>> getPostedJobs(){
		List<JobDTO> response = service.getPostedJobs(true);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getDisabledJobs")
	public ResponseEntity<List<JobDTO>> getDisabledJobs(){
		List<JobDTO> response = service.getPostedJobs(false);
		return ResponseEntity.ok(response);
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
	
	@GetMapping("/getReferrals")
	public ResponseEntity<List<ReferralsDTO>> getReferrals(){
		return ResponseEntity.ok(service.getReferals());
	}
	
	@GetMapping("/getReferralDetails/{referralId}")
	public ResponseEntity<AppliedReferralDTO> getReferralDetails(@PathVariable Long referralId){
		AppliedReferralDTO response = service.getReferralDetails(referralId);
		if(response != null)
			return ResponseEntity.ok(response);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	}
	
	@PutMapping("/rejectReferralRequest")
	public ResponseEntity<String> rejectReferralRequest(@RequestBody RejectReferralDTO request){
		if(service.rejectReferralRequest(request))
			return ResponseEntity.ok("Referral request rejected successfully");
		return new ResponseEntity<>("Unable to reject the referral request",HttpStatus.FORBIDDEN);
	}
	
	@PutMapping("/approveReferral/{referralId}")
	public ResponseEntity<String> approveReferral(@PathVariable Long referralId){
		if(service.approvereferral(referralId))
			return ResponseEntity.ok("Referral approved successfully");
		return new ResponseEntity<>("Unable to approve referral",HttpStatus.FORBIDDEN);
	}
	
	@PutMapping("/rejectReferral")
	public ResponseEntity<String> rejectReferral(@RequestBody RejectReferralDTO request){
		if(service.rejectReferral(request))
			return ResponseEntity.ok("Referral rejected successfully");
		return new ResponseEntity<>("Unable to reject the referral",HttpStatus.FORBIDDEN);
	}
	
	@GetMapping("/getAllWallets")
	public ResponseEntity<List<WalletsDTO>> getAllWallets(){	
		return ResponseEntity.ok(service.getAllWallets());
	}
	
	@GetMapping("/getWalletDetails/{email}")
	public ResponseEntity<WalletDTO> getWalletDetails(@PathVariable String email){
		WalletDTO wallet = jobPosterService.getWalletDetails(email);
		if(wallet != null)
			return ResponseEntity.ok(wallet);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	}
	
	@PutMapping("/submitDeposit")
	public ResponseEntity<String> submitDeposit(@RequestBody WalletDepositDTO request){
		if(service.submitDeposit(request))
			return ResponseEntity.ok("Deposit submitted successfully");
		return new ResponseEntity<>("Unable to submit the deposit",HttpStatus.FORBIDDEN);
	}

}
