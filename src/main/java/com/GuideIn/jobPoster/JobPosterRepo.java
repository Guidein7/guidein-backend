package com.GuideIn.jobPoster;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPosterRepo extends JpaRepository<JobPoster, Long> {
	Optional<JobPoster> findByEmail(String email);
}
