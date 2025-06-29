package com.GuideIn.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.GuideIn.project.model.Certification;

@Repository
public interface CertificationRepository extends JpaRepository<Certification,Long>{
	
	@Query(value="select * from certifications where ?1 is null or course_category=?1 and ?2 is null or course_duration=?2",nativeQuery=true)
	Page<Certification> getdataByFilter(String courseCategory, String duration,Pageable pageable);
	
	@Query(value="select distinct course_category from certifications",nativeQuery=true)
	List<String> getdistinctCourseCatogory();
	@Query(value="select distinct course_duration from certifications",nativeQuery=true)
	List<String> getdistinctDuration();

}
