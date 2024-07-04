package com.GuideIn.subscription;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
	
	Optional<Subscription> findByEmailAndActive(String email, Boolean active);
    List<Subscription> findByEmail(String email);
    List<Subscription> findByActive(Boolean active);
    long countByActive(Boolean active);
	
}
