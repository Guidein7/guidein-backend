package com.GuideIn.project.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="certifications")
@Entity
public class Certification {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_category", nullable = false)
    private String courseCategory;

    @Column(name = "course_title", nullable = false,columnDefinition = "TEXT")
    private String courseTitle;

    private String provider;

    private String platform;

    @Column(name = "course_duration")
    private String courseDuration;

    @Column(name = "course_link", columnDefinition = "TEXT")
    private String courseLink;

    @Column(name = "short_description", columnDefinition = "TEXT")
    private String shortDescription;
    
    @Column(name="file_path")
    private String filePath;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourseCategory() {
		return courseCategory;
	}

	public void setCourseCategory(String courseCategory) {
		this.courseCategory = courseCategory;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getCourseDuration() {
		return courseDuration;
	}

	public void setCourseDuration(String courseDuration) {
		this.courseDuration = courseDuration;
	}

	public String getCourseLink() {
		return courseLink;
	}

	public void setCourseLink(String courseLink) {
		this.courseLink = courseLink;
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

	@Override
	public String toString() {
		return "Certification [id=" + id + ", courseCategory=" + courseCategory + ", courseTitle=" + courseTitle
				+ ", provider=" + provider + ", platform=" + platform + ", courseDuration=" + courseDuration
				+ ", courseLink=" + courseLink + ", shortDescription=" + shortDescription + ", filePath=" + filePath
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseCategory, courseDuration, courseLink, courseTitle, filePath, id, platform, provider,
				shortDescription);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Certification other = (Certification) obj;
		return Objects.equals(courseCategory, other.courseCategory)
				&& Objects.equals(courseDuration, other.courseDuration) && Objects.equals(courseLink, other.courseLink)
				&& Objects.equals(courseTitle, other.courseTitle) && Objects.equals(filePath, other.filePath)
				&& Objects.equals(id, other.id) && Objects.equals(platform, other.platform)
				&& Objects.equals(provider, other.provider) && Objects.equals(shortDescription, other.shortDescription);
	}
    
	
    
}
