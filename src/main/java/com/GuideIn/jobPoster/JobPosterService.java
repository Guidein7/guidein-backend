package com.GuideIn.jobPoster;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GuideIn.jobSeeker.JobSeeker;
import com.GuideIn.jobSeeker.JobSeekerRepo;
import com.GuideIn.jobs.Job;
import com.GuideIn.jobs.JobRepository;
import com.GuideIn.jobs.JobService;
import com.GuideIn.referral.Referral;
import com.GuideIn.referral.ReferralRepository;
import com.GuideIn.referral.ReferralStatus;
import com.GuideIn.referral.ReferralSubmitDTO;
import com.GuideIn.subscription.Subscription;
import com.GuideIn.subscription.SubscriptionRepository;
import com.GuideIn.subscription.SubscriptionService;
import com.GuideIn.wallet.Wallet;
import com.GuideIn.wallet.WalletDTO;
import com.GuideIn.wallet.WalletRepository;
import com.GuideIn.wallet.WalletTransactionDetail;
import com.GuideIn.wallet.WalletTransactionDetailRepo;

import jakarta.transaction.Transactional;

@Service
public class JobPosterService {
	
	@Autowired
	JobService jobService;
	
	@Autowired
	JobPosterRepo repo;
	
	@Autowired
	ReferralRepository referralRepo;
	
	@Autowired
	JobRepository jobRepo;
	
	@Autowired
	JobSeekerRepo jobSeekerRepo;
	
	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	@Autowired
	WalletRepository walletRepo;
	
	@Autowired
	WalletTransactionDetailRepo walletTransactionDetailRepo;
	
	@Transactional
	public boolean saveProfile(JobPoster jobPoster) {
		try {
			var wallet = Wallet.builder()
					.email(jobPoster.getEmail())
					.build();
			walletRepo.save(wallet);
			
			repo.save(jobPoster);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public JobPoster getProfile(String email) {
		JobPoster jobPoster = null;
		jobPoster = repo.findByEmail(email).orElse(null);
		return jobPoster;

	}
	
	@Transactional
	public boolean updateProfile(JobPoster request) {
		try {
			JobPoster jobPoster = repo.findByEmail(request.getEmail()).orElseThrow();
			jobPoster.setCurrentCompany(request.getCurrentCompany());
			jobPoster.setTotalExperience(request.getTotalExperience());
			jobPoster.setLinkedInUrl(request.getLinkedInUrl());
			repo.save(jobPoster);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<ReferralsDTO> getRequestedreferrals(String email) {
		List<ReferralsDTO> requestedReferrals = new ArrayList<>();
		List<Referral> referrals = referralRepo.findAllByJobPostedByAndStatus(email, ReferralStatus.REQUESTED);

		for(Referral referral : referrals) {		
			try {
				JobSeeker jobSeeker = jobSeekerRepo.findByEmail(referral.getRequestedBy()).orElseThrow();
				Job job = jobRepo.findById(referral.getJobId()).orElseThrow();
				
				var requestedreferral = ReferralsDTO.builder()
						.candidateName(jobSeeker.getName())
						.candidateExperience(jobSeeker.getExperience())
						.referralFor(job.getJobTitle())
						.referralId(referral.getReferralId())
						.requestedOn(referral.getRequstedOn())
						.status(referral.getStatus())
						.reason(referral.getReason())
						.build();
				
				requestedReferrals.add(requestedreferral);
				
			} catch (Exception e) {
				e.printStackTrace();
				return requestedReferrals;
			} 
		}		
		return requestedReferrals;
	}
	
	public CandidateAndJobDetailsDTO getReferralRequest(Long referralId) {
		CandidateAndJobDetailsDTO candidateAndJobDetailsDTO = null;
		
		try {
			Referral referral = referralRepo.findById(referralId).orElseThrow();
			JobSeeker jobSeeker = jobSeekerRepo.findByEmail(referral.getRequestedBy()).orElseThrow();
			Job job = jobRepo.findById(referral.getJobId()).orElseThrow();
			
			candidateAndJobDetailsDTO = CandidateAndJobDetailsDTO.builder()
					.candidateName(jobSeeker.getName())
					.candidateEmail(jobSeeker.getEmail())
					.candidateMobile(jobSeeker.getMobile())
					.candidateExperience(jobSeeker.getExperience())
					.candidateLinkedInUrl(jobSeeker.getLinkedInUrl())
					.referralFor(job.getJobTitle())
					.referralId(referral.getReferralId())
					.jobId(job.getJobId())
					.company(job.getCompanyName())
					.jobLocation(job.getJobLocation())
					.workMode(job.getWorkMode())
					.experienceRequired(job.getExperienceRequired())
					.jobPostedOn(jobService.getTimeAgo(job.getPostedOn()))
					.referralStatus(referral.getStatus())
					.dateOfReferral(referral.getDateOfReferral())
					.methodOfReferral(referral.getMethodOfReferral())
					.comments(referral.getComments())
					.proof(referral.getProof())
					.candidateResume(referral.getCandidateResume())
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return candidateAndJobDetailsDTO;
		}
		return candidateAndJobDetailsDTO;
	}
	
	@Transactional
	public boolean rejectReferral(RejectReferralDTO request) {		
		try {
			Referral referral = referralRepo.findById(request.getReferralId()).orElseThrow();
			Subscription subscription = subscriptionRepository.findByEmailAndActive(referral.getRequestedBy(), true).orElseThrow();
			referral.setStatus(ReferralStatus.REJECTED);
			referral.setReason(request.getReason());
			referral.setComments(request.getComments());
			
			subscription.setAvailableReferralCredits(subscription.getAvailableReferralCredits() + 1);
			
			referralRepo.save(referral);
			subscriptionRepository.save(subscription);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Transactional
	public boolean submitReferral(ReferralSubmitDTO request) {
		try {
			Referral referral = referralRepo.findById(request.getReferralId()).orElseThrow();
			referral.setDateOfReferral(request.getDateOfReferral());
			referral.setMethodOfReferral(request.getMethodOfReferral());
			referral.setComments(request.getComments());
			referral.setProof(request.getProof().getBytes());
			referral.setStatus(ReferralStatus.IN_VERIFICATION);
			referralRepo.save(referral);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<ReferralsDTO> getALLReferredStatus(String email) {
		List<ReferralsDTO> referredStatuses = new ArrayList<>();
		List<Referral> referrals = referralRepo.findAllByJobPostedByAndNotRequested(email, ReferralStatus.REQUESTED);

		for(Referral referral : referrals) {		
			try {
				JobSeeker jobSeeker = jobSeekerRepo.findByEmail(referral.getRequestedBy()).orElseThrow();
				Job job = jobRepo.findById(referral.getJobId()).orElseThrow();
				
				var referredReferral = ReferralsDTO.builder()
						.candidateName(jobSeeker.getName())
						.candidateExperience(jobSeeker.getExperience())
						.referralFor(job.getJobTitle())
						.referralId(referral.getReferralId())
						.requestedOn(referral.getRequstedOn())
						.status(referral.getStatus())
						.reason(referral.getReason())
						.build();
				
				referredStatuses.add(referredReferral);
				
			} catch (Exception e) {
				e.printStackTrace();
				return referredStatuses;
			} 
		}		
		return referredStatuses;
	}

	public DashboardDetails getDashboardDetails(String email) {
		DashboardDetails dashboardDetails = new DashboardDetails();
		
		try {
			Long totalJobPosted = jobRepo.countByJobPostedBy(email);
			Long activeJobs = jobRepo.countByJobPostedByAndEnabled(email, true);
			Long jobsExpired = jobRepo.countByJobPostedByAndEnabled(email, false);
			Long totalReferralRequests = referralRepo.countByJobPostedByAndStatus(email, ReferralStatus.REQUESTED);
			Long referralsSuccessful = referralRepo.countByJobPostedByAndStatus(email, ReferralStatus.REFERRED);
			Long referralsRejected = referralRepo.countByJobPostedByAndStatus(email, ReferralStatus.VERIFICATION_FAILED)
					+ referralRepo.countByRequestedByAndStatus(email, ReferralStatus.REJECTED);
			
			dashboardDetails.setTotalJobPosted(totalJobPosted);
			dashboardDetails.setActiveJobs(activeJobs);
			dashboardDetails.setJobsExpired(jobsExpired);
			dashboardDetails.setTotalReferralRequests(totalReferralRequests);
			dashboardDetails.setReferralSuccessful(referralsSuccessful);
			dashboardDetails.setReferralRejected(referralsRejected);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return dashboardDetails;
		}
		return dashboardDetails;
	}
	
	public WalletDTO getWalletDetails(String email) {
		WalletDTO walletDTO = null;
		try {
			Wallet wallet = walletRepo.findByEmail(email).orElseThrow();
			List<WalletTransactionDetail> transactions = walletTransactionDetailRepo.findByEmail(wallet.getEmail());
				
			walletDTO = WalletDTO.builder()
					.email(wallet.getEmail())
					.totalReferrals(wallet.getTotalReferrals())
					.totalEarned(wallet.getTotalEarned())
					.amountWithdrawn(wallet.getAmountWithdrawn())
					.withdrawInProgress(wallet.getWithdrawInProgress())
					.currentBalance(wallet.getCurrentBalance())
					.upiId(wallet.getUpiId())
					.transactionHistory(transactions)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			return walletDTO;
		}
		return walletDTO;
	}
	
	@Transactional
	public boolean saveUpiId(UpiIdDTO request) {
		try {
			Wallet wallet = walletRepo.findByEmail(request.getEmail()).orElseThrow();
			wallet.setUpiId(request.getUpiId());
			walletRepo.save(wallet);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Transactional
	public boolean requestWithdraw(String email) {
		try {
			Wallet wallet = walletRepo.findByEmail(email).orElseThrow();
			if(wallet.getWithdrawInProgress() > 0)
				return false;
			
			wallet.setWithdrawInProgress(wallet.getCurrentBalance());
			wallet.setCurrentBalance(wallet.getCurrentBalance() - wallet.getCurrentBalance());
			walletRepo.save(wallet);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
}
