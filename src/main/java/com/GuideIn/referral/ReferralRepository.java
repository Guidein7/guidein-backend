package com.GuideIn.referral;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.GuideIn.referral.ReferralStatus;


@Repository
public interface ReferralRepository extends JpaRepository<Referral, Long> {
	List<Referral> findAllByJobPostedBy(String jobPostedBy);
	List<Referral> findAllByJobPostedByAndStatus(String jobPostedBy, ReferralStatus status);
	List<Referral> findAllByRequestedBy(String requestedBy);

	Optional<Referral> findByRequestedByAndJobId(String requestedBy ,Long jobId);
	
	Long countByRequestedByAndStatus(String requestedBy, ReferralStatus status);
	
	@Query("SELECT o FROM Referral o WHERE o.jobPostedBy = :jobPostedBy AND o.status <> :status")
	List<Referral> findAllByJobPostedByAndNotRequested(@Param("jobPostedBy") String jobPostedBy,
			@Param("status") ReferralStatus status);
	
	Long countByJobPostedByAndStatus(String jobPostedBy, ReferralStatus status);
}
