package com.GuideIn.jobs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
	List<Job> findAllByJobPostedBy(String postedBy);
}

