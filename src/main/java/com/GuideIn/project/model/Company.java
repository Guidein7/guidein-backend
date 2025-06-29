package com.GuideIn.project.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "companies")
public class Company {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	
	    private String companyName;
	@Column(columnDefinition = "TEXT")
	    private String industry;

	    @Column(columnDefinition = "TEXT")
	    private String companyOverview;
		@Column(columnDefinition = "TEXT")
	    private String careerPageUrl;

	   
	    private String hiringStatus;

	    private String totalEmployees;

	    private String hiringGrowth; // percentage (e.g., 12.5%)

	    private String medianTenure; // in years

	    private String ratingAmbitionbox;

	    private String numberOfReviewsAB;

	    private String ratingGlassdoor;

	    private String numberOfReviewsGS;

	    private String tags;
	    
	    private String filePath;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getIndustry() {
			return industry;
		}

		public void setIndustry(String industry) {
			this.industry = industry;
		}

		public String getCompanyOverview() {
			return companyOverview;
		}

		public void setCompanyOverview(String companyOverview) {
			this.companyOverview = companyOverview;
		}

		public String getCareerPageUrl() {
			return careerPageUrl;
		}

		public void setCareerPageUrl(String careerPageUrl) {
			this.careerPageUrl = careerPageUrl;
		}

		public String getHiringStatus() {
			return hiringStatus;
		}

		public void setHiringStatus(String hiringStatus) {
			this.hiringStatus = hiringStatus;
		}

		public String getTotalEmployees() {
			return totalEmployees;
		}

		public void setTotalEmployees(String totalEmployees) {
			this.totalEmployees = totalEmployees;
		}

		public String getHiringGrowth() {
			return hiringGrowth;
		}

		public void setHiringGrowth(String hiringGrowth) {
			this.hiringGrowth = hiringGrowth;
		}

		public String getMedianTenure() {
			return medianTenure;
		}

		public void setMedianTenure(String medianTenure) {
			this.medianTenure = medianTenure;
		}

		public String getRatingAmbitionbox() {
			return ratingAmbitionbox;
		}

		public void setRatingAmbitionbox(String ratingAmbitionbox) {
			this.ratingAmbitionbox = ratingAmbitionbox;
		}

		public String getNumberOfReviewsAB() {
			return numberOfReviewsAB;
		}

		public void setNumberOfReviewsAB(String numberOfReviewsAB) {
			this.numberOfReviewsAB = numberOfReviewsAB;
		}

		public String getRatingGlassdoor() {
			return ratingGlassdoor;
		}

		public void setRatingGlassdoor(String ratingGlassdoor) {
			this.ratingGlassdoor = ratingGlassdoor;
		}

		public String getNumberOfReviewsGS() {
			return numberOfReviewsGS;
		}

		public void setNumberOfReviewsGS(String numberOfReviewsGS) {
			this.numberOfReviewsGS = numberOfReviewsGS;
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
		public int hashCode() {
			return Objects.hash(careerPageUrl, companyName, companyOverview, filePath, hiringGrowth, hiringStatus, id,
					industry, medianTenure, numberOfReviewsAB, numberOfReviewsGS, ratingAmbitionbox, ratingGlassdoor,
					tags, totalEmployees);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Company other = (Company) obj;
			return Objects.equals(careerPageUrl, other.careerPageUrl) && Objects.equals(companyName, other.companyName)
					&& Objects.equals(companyOverview, other.companyOverview)
					&& Objects.equals(filePath, other.filePath) && Objects.equals(hiringGrowth, other.hiringGrowth)
					&& Objects.equals(hiringStatus, other.hiringStatus) && Objects.equals(id, other.id)
					&& Objects.equals(industry, other.industry) && Objects.equals(medianTenure, other.medianTenure)
					&& Objects.equals(numberOfReviewsAB, other.numberOfReviewsAB)
					&& Objects.equals(numberOfReviewsGS, other.numberOfReviewsGS)
					&& Objects.equals(ratingAmbitionbox, other.ratingAmbitionbox)
					&& Objects.equals(ratingGlassdoor, other.ratingGlassdoor) && Objects.equals(tags, other.tags)
					&& Objects.equals(totalEmployees, other.totalEmployees);
		}

		@Override
		public String toString() {
			return "Company [id=" + id + ", companyName=" + companyName + ", industry=" + industry
					+ ", companyOverview=" + companyOverview + ", careerPageUrl=" + careerPageUrl + ", hiringStatus="
					+ hiringStatus + ", totalEmployees=" + totalEmployees + ", hiringGrowth=" + hiringGrowth
					+ ", medianTenure=" + medianTenure + ", ratingAmbitionbox=" + ratingAmbitionbox
					+ ", numberOfReviewsAB=" + numberOfReviewsAB + ", ratingGlassdoor=" + ratingGlassdoor
					+ ", numberOfReviewsGS=" + numberOfReviewsGS + ", tags=" + tags + ", filePath=" + filePath + "]";
		}

		

}
