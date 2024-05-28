package com.GuideIn.jobSeeker;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface SavedJobRepo extends JpaRepository<SavedJob, Long>{
	Optional<SavedJob> findByEmail(String email);
	Optional<SavedJob> findByEmailAndJobId(String email, Long jobId);
	
	@Query("SELECT u.jobId FROM SavedJob u WHERE u.email = :email")
	List<Long> findJobIdsByEmail(@Param("email") String email);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM SavedJob u WHERE u.email = :email AND u.jobId = :jobId")
	void deleteByEmailAndJobId(@Param("email") String email, @Param("jobId") Long jobId);
	
}
