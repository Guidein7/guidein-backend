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
	
	@Query(value="select * from course_info where (?1 is null or location =?1) and (?2 is null or mode_of_class=?2) and (?3 is null or course_duration=?3) and ((?4 IS NULL OR course_name LIKE CONCAT('%', ?4, '%'))  or (?5 IS NULL OR institute_name LIKE CONCAT('%', ?5, '%')))",nativeQuery=true)
	Page<CourseInfo> findByFilters(String location, String modeOfClass, String duration, String courseName, String instituteName, Pageable pageable);
	
	@Query(value="select location,estimated_course_price,course_duration,mode_of_class from course_info",nativeQuery=true)
	List<String> getDistinctdropDowns();

}
