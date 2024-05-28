package com.GuideIn.jobs;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
	List<Job> findAllByJobPostedBy(String postedBy);
	List<Job> findByEnabled(boolean check);
	Optional<Job> findByJobIdAndEnabled(Long jobId, boolean check);
}

