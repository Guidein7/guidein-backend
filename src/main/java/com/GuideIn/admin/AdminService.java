package com.GuideIn.admin;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.GuideIn.auth.DuplicateEntityException;
import com.GuideIn.auth.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.GuideIn.jobPoster.JobPoster;
import com.GuideIn.jobPoster.JobPosterRepo;
import com.GuideIn.jobPoster.RejectReferralDTO;
import com.GuideIn.jobSeeker.AppliedReferralDTO;
import com.GuideIn.jobSeeker.JobSeeker;
import com.GuideIn.jobSeeker.JobSeekerRepo;
import com.GuideIn.jobs.Job;
import com.GuideIn.jobs.JobDTO;
import com.GuideIn.jobs.JobRepository;
import com.GuideIn.jobs.JobService;
import com.GuideIn.plivo.DLTdetails;
import com.GuideIn.plivo.PlivoMessageService;
import com.GuideIn.referral.Referral;
import com.GuideIn.referral.ReferralRepository;
import com.GuideIn.referral.ReferralStatus;
import com.GuideIn.subscription.Subscription;
import com.GuideIn.subscription.SubscriptionRepository;
import com.GuideIn.user.Role;
import com.GuideIn.user.User;
import com.GuideIn.user.UserRepository;
import com.GuideIn.wallet.Wallet;
import com.GuideIn.wallet.WalletRepository;
import com.GuideIn.wallet.WalletTransactionDetail;
import com.GuideIn.wallet.WalletTransactionDetailRepo;
import com.plivo.api.Plivo;

import jakarta.transaction.Transactional;

@Service
public class AdminService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	SubscriptionRepository subscriptionRepo;
	
	@Autowired
	JobRepository jobRepo;
	
	@Autowired
	JobPosterRepo jobPosterRepo;
	
	@Autowired
	JobSeekerRepo jobSeekerRepo;

	@Autowired
	LearnerRepo learnerRepo;
	
	@Autowired
	ReferralRepository referralRepo;
	
	@Autowired
	WalletRepository walletRepo;
	
	@Autowired
	WalletTransactionDetailRepo walletTransactionDetailRepo;
	
	@Autowired
	JobService jobService;
	
	@Autowired
	PlivoMessageService messageService;

	public List<JobSeekerRegisterdUsers> getJobSeekerRegisteredUsers() {
		List<JobSeekerRegisterdUsers> jobSeekerRegisterdUsers = new ArrayList<>();
		List<User> registeredUsers = userRepo.findByRole(Role.JOB_SEEKER);
		for(User user : registeredUsers) {
			
			Boolean isSubscribed = false;
			Subscription subscription = subscriptionRepo.findByEmailAndActive(user.getEmail(), true).orElse(null);
			if(subscription != null)
				isSubscribed = true;
			
			var registeredUser = JobSeekerRegisterdUsers.builder()
					.name(user.getName())
					.mobile(user.getMobile())
					.email(user.getEmail())
					.isSubscribed(isSubscribed)
					.build();
			jobSeekerRegisterdUsers.add(registeredUser);
		}	
		return jobSeekerRegisterdUsers;
	}

	public List<JobSeekerSubscribedUsers> getJobSeekerSubscribedUser() {
		List<JobSeekerSubscribedUsers> jobSeekerSubscribedUsers = new ArrayList<>();
		List<Subscription> subscriptions = subscriptionRepo.findByActive(true);
		for(Subscription subscription : subscriptions) {
			var subscribedUser = JobSeekerSubscribedUsers.builder()
					.name(subscription.getName())
					.email(subscription.getEmail())
					.mobile(subscription.getMobile())
					.plan(subscription.getPlan())
					.subscribedOn(subscription.getSubscribedOn())
					.build();
			jobSeekerSubscribedUsers.add(subscribedUser);
		}
		return jobSeekerSubscribedUsers;
	}

	public List<JobPosterRegisteredUsers> getJobPosterRegisteredUsers() {
		List<JobPosterRegisteredUsers> jobPosterRegisteredUsers = new ArrayList<>();
		List<User> registeredUsers = userRepo.findByRole(Role.JOB_POSTER);
		
		for(User user : registeredUsers) {
			var registerdUser = JobPosterRegisteredUsers.builder()
					.name(user.getName())
					.mobile(user.getMobile())
					.email(user.getEmail())
					.totalJobPosted(jobRepo.countByJobPostedBy(user.getEmail()))
					.build();
			jobPosterRegisteredUsers.add(registerdUser);
		}
		return jobPosterRegisteredUsers;
	}

	public List<JobDTO> getPostedJobs(Boolean check) {
		List<JobDTO> postedJobs = new ArrayList<>();
		List<Job> jobs = jobRepo.findByEnabled(check);
		
		for(Job job : jobs) {
			try {
				JobPoster jobPoster = jobPosterRepo.findByEmail(job.getJobPostedBy()).orElseThrow();
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
						.enabled(job.isEnabled())
						.disabledByAdmin(job.isDisabledByAdmin())
						.build();
				postedJobs.add(jobDTO);
			} catch (Exception e) {
				e.printStackTrace();
				return postedJobs;
			}
		}
		return postedJobs;
	}
	
	/////////
	@Transactional
	public boolean disableJob(Long jobId) {
		Job job = null;
		try {
			job = jobRepo.findById(jobId).orElseThrow();
			job.setEnabled(false);
			job.setDisabledByAdmin(true);
			jobRepo.save(job);
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
			job = jobRepo.findById(jobId).orElseThrow();
			job.setEnabled(true);
			job.setDisabledByAdmin(false);
			jobRepo.save(job);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public DashboardDetailsDTO getDashboardDetails() {
		DashboardDetailsDTO dashboardDetailsDTO = new DashboardDetailsDTO();
		
		try {
			Long totalRegisteredJobSeekers = userRepo.countByRole(Role.JOB_SEEKER);
			Long totalSubscribedUsers = subscriptionRepo.countByActive(true);
			Long totalSubscriptions = subscriptionRepo.count();
			Long totalRegisteredJobPosters = userRepo.countByRole(Role.JOB_POSTER);
			Long totalJobPosted = jobRepo.countByEnabled(true);
			Long totalDisabledJobs = jobRepo.countByEnabled(false);
			
			dashboardDetailsDTO = DashboardDetailsDTO.builder()
					.totalRegisteredJobSeekers(totalRegisteredJobSeekers)
					.totalSubscribedUsers(totalSubscribedUsers)
					.totalSubscriptions(totalSubscriptions)
					.totalRegisteredJobPosters(totalRegisteredJobPosters)
					.totalJobPosted(totalJobPosted)
					.totalDisabledJobs(totalDisabledJobs)
					.build();
			
		} catch (Exception e) {
			e.printStackTrace();
			return dashboardDetailsDTO;
		}
		return dashboardDetailsDTO;
	}

	public List<ReferralsDTO> getReferals() {
		List<ReferralsDTO> referralsDTO = new ArrayList<>();
		List<Referral> referrals = referralRepo.findAll();
		
		for(Referral referral : referrals) {			
			try {
				JobSeeker jobSeeker = jobSeekerRepo.findByEmail(referral.getRequestedBy()).orElseThrow();
				JobPoster jobPoster = jobPosterRepo.findByEmail(referral.getJobPostedBy()).orElseThrow();
				Job job = jobRepo.findByJobId(referral.getJobId()).orElseThrow();
				
				var referralDTO = ReferralsDTO.builder()
						.refferalId(referral.getReferralId())
						.requestedBy(jobSeeker.getName())
						.jobPostedBy(jobPoster.getName())
						.requestedOn(referral.getRequstedOn())
						.jobRole(job.getJobTitle())
						.status(referral.getStatus())
						.mobileOfrequestedBy(jobSeeker.getMobile())
						.mobileOfJobPostedBy(jobPoster.getMobile())
						.requestedAgo(getTimeAgo(referral.getRequestedOnWithTime()))
						.build();
				referralsDTO.add(referralDTO);
			} catch (Exception e) {
				e.printStackTrace();
				return referralsDTO;
			}	
		}
		return referralsDTO;
	}
	
	public AppliedReferralDTO getReferralDetails(Long referralId) {
		AppliedReferralDTO appliedReferralDTO = null;
		
		try {
			Referral referral = referralRepo.findById(referralId).orElseThrow();
			Job job = jobRepo.findByJobId(referral.getJobId()).orElseThrow();
			JobPoster jobPoster = jobPosterRepo.findByEmail(referral.getJobPostedBy()).orElseThrow();
			JobSeeker jobSeeker = jobSeekerRepo.findByEmail(referral.getRequestedBy()).orElseThrow();
			
			appliedReferralDTO = AppliedReferralDTO.builder()
				.referralId(referral.getReferralId())
				.candidateName(jobSeeker.getName())
				.candidateEmail(jobSeeker.getEmail())
				.candidateMobile(jobSeeker.getMobile())
				.candidateExperience(jobSeeker.getExperience())
				.jobId(referral.getJobId())
				.jobTitle(job.getJobTitle())
				.companyName(job.getCompanyName())
				.jobLocation(job.getJobLocation())
				.workMode(job.getWorkMode())
				.jobType(job.getJobType())
				.jobDescriptionLink(job.getJobDescriptionLink())
				.experienceRequired(job.getExperienceRequired())
				.jobPostedBy(job.getJobPostedBy())
				.jobPosterName(jobPoster.getName())
				.postedOn(jobService.getTimeAgo(job.getPostedOn()))
				.requstedOn(referral.getRequstedOn())
				.currentStatus(referral.getStatus())
				.reason(referral.getReason())
				.dateOfReferral(referral.getDateOfReferral())
				.methodOfReferral(referral.getMethodOfReferral())
				.comments(referral.getComments())
				.resume(referral.getCandidateResume())
				.proof(referral.getProof())
				.build();
		} catch (Exception e) {
			e.printStackTrace();
			return appliedReferralDTO;
		}
		return appliedReferralDTO;
	}
	
	@Transactional
	public boolean approveReferral(Long referralId) {
		
		try {
			Referral referral = referralRepo.findById(referralId).orElseThrow();
			Job job = jobRepo.findById(referral.getJobId()).orElseThrow();
			Subscription subscription = subscriptionRepo.findByEmailAndActive(referral.getRequestedBy(), true).orElseThrow();
			JobPoster jobPoster = jobPosterRepo.findByEmail(job.getJobPostedBy()).orElseThrow();
			
			subscription.setUsedReferralCredits(subscription.getUsedReferralCredits() + 1);
				
			referral.setStatus(ReferralStatus.REFERRED);
			
			Wallet wallet = walletRepo.findByEmail(referral.getJobPostedBy()).orElseThrow();
			wallet.setTotalReferrals(wallet.getTotalReferrals() + 1);
			wallet.setTotalEarned(wallet.getTotalEarned() + 200);
			wallet.setCurrentBalance(wallet.getCurrentBalance() + 200);
			
			subscriptionRepo.save(subscription);
			referralRepo.save(referral);
			walletRepo.save(wallet);
			
			//message to jobSeeker
			messageService.sendMessage(
					DLTdetails.POST_SUCCESSFULL_REFERRAL,
					subscription.getMobile(),
					job.getJobTitle(),
					job.getCompanyName());
			
			//message to jobPoster
			messageService.sendMessage(
					DLTdetails.REFERRAL_APPROVED,
					jobPoster.getMobile(),
					job.getJobTitle());
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Transactional
	public boolean rejectReferral(RejectReferralDTO request) {
		try {
			Referral referral = referralRepo.findById(request.getReferralId()).orElseThrow();
			Subscription subscription = subscriptionRepo.findByEmailAndActive(referral.getRequestedBy(), true).orElseThrow();
			Job job = jobRepo.findById(referral.getJobId()).orElseThrow();
			JobPoster jobPoster = jobPosterRepo.findByEmail(job.getJobPostedBy()).orElseThrow();
			referral.setStatus(ReferralStatus.VERIFICATION_FAILED);
			referral.setReason(request.getReason());
			referral.setComments(request.getComments());
			
			subscription.setAvailableReferralCredits(subscription.getAvailableReferralCredits() + 1);
			
			referralRepo.save(referral);
			subscriptionRepo.save(subscription);
			
			//message to jobSeeker
			messageService.sendMessage(
					DLTdetails.POST_REFERRAL_REJECTION,
					subscription.getMobile(),
					job.getJobTitle(),
					job.getCompanyName());
			
			//message to jobPoster
			messageService.sendMessage( 
					DLTdetails.REFERRAL_REJECTED,
					jobPoster.getMobile(),
					job.getJobTitle());
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<WalletsDTO> getAllWallets() {
		List<WalletsDTO> walletsDTO = new ArrayList<>();
		List<Wallet> wallets = walletRepo.findAllByOrderByWithdrawInProgressDesc();
		
		for(Wallet wallet : wallets) {
			try {
				JobPoster jobPoster = jobPosterRepo.findByEmail(wallet.getEmail()).orElseThrow();
				var DTO = WalletsDTO.builder()
						.email(wallet.getEmail())
						.name(jobPoster.getName())
						.mobile(jobPoster.getMobile())
						.totalReferrals(wallet.getTotalReferrals())
						.totalEarned(wallet.getTotalEarned())
						.amountWithdrawn(wallet.getAmountWithdrawn())
						.withdrawInProgress(wallet.getWithdrawInProgress())
						.build();
				walletsDTO.add(DTO);
			} catch (Exception e) {
				e.printStackTrace();
				return walletsDTO;
			}
		}
		return walletsDTO;
	}
	
	@Transactional
	public boolean submitDeposit(WalletDepositDTO request) {
		try {
			Wallet wallet = walletRepo.findByEmail(request.getEmail()).orElseThrow();
			wallet.setAmountWithdrawn(wallet.getAmountWithdrawn() + wallet.getWithdrawInProgress());
			wallet.setWithdrawInProgress(wallet.getWithdrawInProgress() - wallet.getWithdrawInProgress());
			
			var walletTransactionDetail = WalletTransactionDetail.builder()
					.email(wallet.getEmail())
					.amount(request.getWithdrawAmount())
					.transactionId(request.getTransactionId())
					.build();
			
			walletRepo.save(wallet);
			walletTransactionDetailRepo.save(walletTransactionDetail);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Transactional
	public boolean rejectReferralRequest(RejectReferralDTO request) {
		try {
			Referral referral = referralRepo.findById(request.getReferralId()).orElseThrow();
			Subscription subscription = subscriptionRepo.findByEmailAndActive(referral.getRequestedBy(), true).orElseThrow();
			Job job = jobRepo.findById(referral.getJobId()).orElseThrow();
			referral.setStatus(ReferralStatus.REJECTED);
			referral.setReason(request.getReason());
			referral.setComments(request.getComments());
			
			subscription.setAvailableReferralCredits(subscription.getAvailableReferralCredits() + 1);
			
			referralRepo.save(referral);
			subscriptionRepo.save(subscription);
			
			////message to jobSeeker
			messageService.sendMessage(
					DLTdetails.POST_REFERRAL_REJECTION,
					subscription.getMobile(),
					job.getJobTitle(),
					job.getCompanyName());
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	public String getTimeAgo(String requestedOn) {
  
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime requestedDateTime = LocalDateTime.parse(requestedOn, formatter);
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(requestedDateTime, now);
        
        long days = duration.toDays();
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60; // Remainder minutes after full hours

        if (days >= 3) {
            return "expired";
        } else if (days >= 1) {
            return days == 1 ? "1 day ago" : days + " days ago";
        } else if (hours >= 1) {
            return hours == 1 ? "1 hr ago" : hours + " hrs ago";
        } else if (minutes >= 1) {
            return minutes == 1 ? "1 min ago" : minutes + " mins ago";
        } else {
            return "just now";
        }
    }

	public Learner saveLearnerDetails(Learner learner) {
		// Validate important fields
		if (learner.getFullName() == null || learner.getFullName().trim().isEmpty()) {
			throw new ValidationException("Learner name cannot be empty");
		}

		// Check for duplicates (assuming email is unique)
//		if (learner.getEmail() != null && !learner.getEmail().trim().isEmpty()) {
//			Learner existingLearner = learnerRepo.findByEmail(learner.getEmail());
//			if (existingLearner != null) {
//				throw new DuplicateEntityException("Your request with this email (" + learner.getEmail() + ") has already been submitted.");
//			}
//		}

		try {
			return learnerRepo.save(learner);
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateEntityException("Learner with provided details already exists");
		} catch (Exception e) {

			throw new RuntimeException("Failed to save learner details");
		}
	}

	public  List<Learner> getAllLearners() {
		return  learnerRepo.findAll();
	}

	
}
