package com.GuideIn.project.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="blogs")
public class Blogs {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String title;

	    @Column(columnDefinition="text")
	    private String content;

	    private String category;

	    private String slug; // for SEO friendly URLs

	    private String thumbnail;

	    private boolean published = false;

	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
	    @Column(columnDefinition="text")
	    private String description;
	    private String fileType;
	    
	    
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getFileType() {
			return fileType;
		}
		public void setFileType(String fileType) {
			this.fileType = fileType;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getSlug() {
			return slug;
		}
		public void setSlug(String slug) {
			this.slug = slug;
		}
		
		public String getThumbnail() {
			return thumbnail;
		}
		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}
		public boolean isPublished() {
			return published;
		}
		public void setPublished(boolean published) {
			this.published = published;
		}
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}
		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}
		@Override
		public int hashCode() {
			return Objects.hash(category, content, createdAt, description, fileType, id, published, slug, thumbnail,
					title, updatedAt);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Blogs other = (Blogs) obj;
			return Objects.equals(category, other.category) && Objects.equals(content, other.content)
					&& Objects.equals(createdAt, other.createdAt) && Objects.equals(description, other.description)
					&& Objects.equals(fileType, other.fileType) && Objects.equals(id, other.id)
					&& published == other.published && Objects.equals(slug, other.slug)
					&& Objects.equals(thumbnail, other.thumbnail) && Objects.equals(title, other.title)
					&& Objects.equals(updatedAt, other.updatedAt);
		}
		@Override
		public String toString() {
			return "Blogs [id=" + id + ", title=" + title + ", content=" + content + ", category=" + category
					+ ", slug=" + slug + ", thumbnail=" + thumbnail + ", published=" + published + ", createdAt="
					+ createdAt + ", updatedAt=" + updatedAt + ", description=" + description + ", fileType=" + fileType
					+ "]";
		}
		
		
	    
	    

}
