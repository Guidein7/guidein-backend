package com.GuideIn.project.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="youtube_vedio")
public class YouTubeVideo {
	
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    @Column(name = "video_title", nullable = false)
    private String videoTitle;

    @Column(name = "total_views")
    private String totalViews;

    @Column(name = "youtube_url", unique = true)
    private String youtubeUrl;

    @Column(name = "channel_name")
    private String channelName;

    private String duration;

    @Column(length = 2048)
    private String shortDescription;
    
    private String filePath;
   
    private String tags;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public String getTotalViews() {
		return totalViews;
	}

	public void setTotalViews(String totalViews) {
		this.totalViews = totalViews;
	}

	public String getYoutubeUrl() {
		return youtubeUrl;
	}

	public void setYoutubeUrl(String youtubeUrl) {
		this.youtubeUrl = youtubeUrl;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@Override
	public int hashCode() {
		return Objects.hash(channelName, duration, filePath, id, shortDescription, tags, topic, totalViews, videoTitle,
				youtubeUrl);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		YouTubeVideo other = (YouTubeVideo) obj;
		return Objects.equals(channelName, other.channelName) && Objects.equals(duration, other.duration)
				&& Objects.equals(filePath, other.filePath) && Objects.equals(id, other.id)
				&& Objects.equals(shortDescription, other.shortDescription) && Objects.equals(tags, other.tags)
				&& Objects.equals(topic, other.topic) && Objects.equals(totalViews, other.totalViews)
				&& Objects.equals(videoTitle, other.videoTitle) && Objects.equals(youtubeUrl, other.youtubeUrl);
	}

	@Override
	public String toString() {
		return "YouTubeVideo [id=" + id + ", topic=" + topic + ", videoTitle=" + videoTitle + ", totalViews="
				+ totalViews + ", youtubeUrl=" + youtubeUrl + ", channelName=" + channelName + ", duration=" + duration
				+ ", shortDescription=" + shortDescription + ", filePath=" + filePath + ", tags=" + tags + "]";
	}

    
	

}
