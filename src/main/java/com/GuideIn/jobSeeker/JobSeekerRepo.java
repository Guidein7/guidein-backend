package com.GuideIn.jobSeeker;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSeekerRepo extends JpaRepository<JobSeeker, Long> {
	Optional<JobSeeker> findByEmail(String email);
}
