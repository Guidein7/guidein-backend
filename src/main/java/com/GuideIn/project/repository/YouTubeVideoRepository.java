package com.GuideIn.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.GuideIn.project.model.YouTubeVideo;

@Repository
public interface YouTubeVideoRepository extends JpaRepository<YouTubeVideo,Long>{
	
	@Query(value="select * from youtube_vedio where (?1 is null or topic=?1) and (?2 is null or duration=?2) and (?3 IS NULL OR video_title LIKE CONCAT('%', ?3, '%'))",nativeQuery=true)
	Page<YouTubeVideo> findByTopic(String topic, String duration, String vedioTitle, Pageable pageable);
	
	
	

	
	@Query(value="select distinct topic  from youtube_vedio",nativeQuery=true)
	List<String> getdisinctTopics();




	@Query(value="select distinct duration  from youtube_vedio",nativeQuery=true)
	List<String> getdistictduration();




	@Query(value="select distinct tags from youtube_vedio",nativeQuery=true)
	List<String> getdistinctTags();

}
