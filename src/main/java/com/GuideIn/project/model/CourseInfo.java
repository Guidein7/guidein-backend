package com.GuideIn.project.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "course_info")
public class CourseInfo {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "course_name")
	    private String courseName;

	    @Column(name = "institute_name")
	    private String instituteName;

	    @Column(name = "location")
	    private String location;

	    @Column(name = "address", columnDefinition = "TEXT")
	    private String address;

	    @Column(name = "mobile_number")
	    private String mobileNumber;

	    @Column(name = "course_duration")
	    private String courseDuration;

	    @Column(name = "mode_of_class")
	    private String modeOfClass; // Online / Offline / Hybrid

	    @Column(name = "course_batch")
	    private String courseBatch;

	    @Column(name = "computer_lab")
	    private String computerLab;

	    @Column(name = "estimated_course_price")
	    private String estimatedCoursePrice;

	    @Column(name = "rating")
	    private String rating;

	    @Column(name = "number_of_reviews")
	    private String numberOfReviews;

	    @Column(name = "website_url")
	    private String websiteUrl;

	    @Column(name = "job_assistance")
	    private String jobAssistance;

	    @Column(name = "institute_description", columnDefinition = "TEXT")
	    private String instituteDescription;

	    @Column(name = "map_location",columnDefinition = "TEXT")
	    private String mapLocation;

	    @Column(name = "tags")
	    private String tags;
	    
	    private String filePath;
	    
	    private String subLocation;

		public String getSubLocation() {
			return subLocation;
		}

		public void setSubLocation(String subLocation) {
			this.subLocation = subLocation;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getCourseName() {
			return courseName;
		}

		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}

		public String getInstituteName() {
			return instituteName;
		}

		public void setInstituteName(String instituteName) {
			this.instituteName = instituteName;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getMobileNumber() {
			return mobileNumber;
		}

		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}

		public String getCourseDuration() {
			return courseDuration;
		}

		public void setCourseDuration(String courseDuration) {
			this.courseDuration = courseDuration;
		}

		public String getModeOfClass() {
			return modeOfClass;
		}

		public void setModeOfClass(String modeOfClass) {
			this.modeOfClass = modeOfClass;
		}

		public String getCourseBatch() {
			return courseBatch;
		}

		public void setCourseBatch(String courseBatch) {
			this.courseBatch = courseBatch;
		}

		public String getComputerLab() {
			return computerLab;
		}

		public void setComputerLab(String computerLab) {
			this.computerLab = computerLab;
		}

		public String getEstimatedCoursePrice() {
			return estimatedCoursePrice;
		}

		public void setEstimatedCoursePrice(String estimatedCoursePrice) {
			this.estimatedCoursePrice = estimatedCoursePrice;
		}

		public String getRating() {
			return rating;
		}

		public void setRating(String rating) {
			this.rating = rating;
		}

		public String getNumberOfReviews() {
			return numberOfReviews;
		}

		public void setNumberOfReviews(String numberOfReviews) {
			this.numberOfReviews = numberOfReviews;
		}

		public String getWebsiteUrl() {
			return websiteUrl;
		}

		public void setWebsiteUrl(String websiteUrl) {
			this.websiteUrl = websiteUrl;
		}

		public String getJobAssistance() {
			return jobAssistance;
		}

		public void setJobAssistance(String jobAssistance) {
			this.jobAssistance = jobAssistance;
		}

		public String getInstituteDescription() {
			return instituteDescription;
		}

		public void setInstituteDescription(String instituteDescription) {
			this.instituteDescription = instituteDescription;
		}

		public String getMapLocation() {
			return mapLocation;
		}

		public void setMapLocation(String mapLocation) {
			this.mapLocation = mapLocation;
		}

		public String getTags() {
			return tags;
		}

		public void setTags(String tags) {
			this.tags = tags;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

		@Override
		public String toString() {
			return "CourseInfo [id=" + id + ", courseName=" + courseName + ", instituteName=" + instituteName
					+ ", location=" + location + ", address=" + address + ", mobileNumber=" + mobileNumber
					+ ", courseDuration=" + courseDuration + ", modeOfClass=" + modeOfClass + ", courseBatch="
					+ courseBatch + ", computerLab=" + computerLab + ", estimatedCoursePrice=" + estimatedCoursePrice
					+ ", rating=" + rating + ", numberOfReviews=" + numberOfReviews + ", websiteUrl=" + websiteUrl
					+ ", jobAssistance=" + jobAssistance + ", instituteDescription=" + instituteDescription
					+ ", mapLocation=" + mapLocation + ", tags=" + tags + ", filePath=" + filePath + "]";
		}

		@Override
		public int hashCode() {
			return Objects.hash(address, computerLab, courseBatch, courseDuration, courseName, estimatedCoursePrice,
					filePath, id, instituteDescription, instituteName, jobAssistance, location, mapLocation,
					mobileNumber, modeOfClass, numberOfReviews, rating, subLocation, tags, websiteUrl);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CourseInfo other = (CourseInfo) obj;
			return Objects.equals(address, other.address) && Objects.equals(computerLab, other.computerLab)
					&& Objects.equals(courseBatch, other.courseBatch)
					&& Objects.equals(courseDuration, other.courseDuration)
					&& Objects.equals(courseName, other.courseName)
					&& Objects.equals(estimatedCoursePrice, other.estimatedCoursePrice)
					&& Objects.equals(filePath, other.filePath) && Objects.equals(id, other.id)
					&& Objects.equals(instituteDescription, other.instituteDescription)
					&& Objects.equals(instituteName, other.instituteName)
					&& Objects.equals(jobAssistance, other.jobAssistance) && Objects.equals(location, other.location)
					&& Objects.equals(mapLocation, other.mapLocation)
					&& Objects.equals(mobileNumber, other.mobileNumber)
					&& Objects.equals(modeOfClass, other.modeOfClass)
					&& Objects.equals(numberOfReviews, other.numberOfReviews) && Objects.equals(rating, other.rating)
					&& Objects.equals(subLocation, other.subLocation) && Objects.equals(tags, other.tags)
					&& Objects.equals(websiteUrl, other.websiteUrl);
		}

		
	    

}
