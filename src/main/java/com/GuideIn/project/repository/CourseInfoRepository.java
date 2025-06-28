package com.GuideIn.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.GuideIn.project.model.CourseInfo;

@Repository
public interface CourseInfoRepository extends JpaRepository<CourseInfo,Long>{
	
	@Query(value="select * from course_info where ?1 is null or location =?1 and ?2 is null or mode_of_class=?2",nativeQuery=true)
	Page<CourseInfo> findByFilters(String location, String modeOfClass, Pageable pageable);
	
	@Query(value="select location,estimated_course_price,course_duration,mode_of_class from course_info",nativeQuery=true)
	List<String> getDistinctdropDowns();

}
