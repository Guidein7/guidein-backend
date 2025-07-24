package com.GuideIn.project.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.GuideIn.project.model.Blogs;
import com.GuideIn.project.repository.BlogRepository;

@Service
public class BlogService {
	
	@Autowired
	private BlogRepository blogRepository;
	
	private final String uploadDir = "uploads/";

	public void createNewBlog(Blogs blog, MultipartFile file){
		blog.setCreatedAt(LocalDateTime.now());
		try {
		blog.setThumbnail(uploadFile(file));
		if(file!=null) {
		blog.setFileType(file.getContentType());
		}
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
			blog.setThumbnail(null);
			blog.setFileType(null);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		blogRepository.save(blog);
		
	}
	
	
	public Map<String, Object> getBlogsSaved(Map<String, String> allparams, int page, int size){
		 Pageable pageable = PageRequest.of(page, size);
		    Page<Blogs> data;

		   
		        data=blogRepository.findAll(pageable);
		    

		    Map<String, Object> response = new HashMap<>();
		    response.put("content", getDateWithContent(data.getContent()));
		    response.put("currentPage", data.getNumber());
		    response.put("totalItems", data.getTotalElements());
		    response.put("totalPages", data.getTotalPages());
		    response.put("pageSize", data.getSize());
		    response.put("isLastPage", data.isLast());
		    return response;
	}
	
	
	
	
	
	private Object getDateWithContent(List<Blogs> content) {
		// TODO Auto-generated method stub
		List<Blogs> blogs=new ArrayList<>(content);
		
		for(Blogs blog:blogs) {
			blog.setThumbnail(returnB64(blog.getThumbnail()));
		}
		return blogs;
		
	}
	
	public String returnB64(String savedfilePath) {
	try {
        // Construct file path
        Path filePath = Paths.get(savedfilePath);
        Resource resource = new UrlResource(filePath.toUri());

        // Check if file exists and is readable
        if (!resource.exists() || !resource.isReadable()) {
            return null;
        }

        // Read file bytes and encode to Base64
        byte[] fileBytes = Files.readAllBytes(filePath);
        String base64String = Base64.getEncoder().encodeToString(fileBytes);

        // Return Base64 string with file metadata
        return base64String;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}


	public String getTimeAgo(LocalDateTime createdAt) {
	    Duration duration = Duration.between(createdAt, LocalDateTime.now());

	    if (duration.toMinutes() < 1) return "Just now";
	    else if (duration.toMinutes() < 60) return duration.toMinutes() + " minutes ago";
	    else if (duration.toHours() < 24) return duration.toHours() + " hours ago";
	    else return duration.toDays() + " days ago";
	}
	
	public String uploadFile(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Validate file type (PNG, JPG, GIF)
        String contentType = file.getContentType();
        if (!contentType.equals("image/png") && !contentType.equals("image/jpeg") && !contentType.equals("image/gif")) {
            throw new IllegalArgumentException("Only PNG, JPG, or GIF files are allowed");
        }

        // Generate unique filename
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.write(filePath, file.getBytes());

        // Return file URL (adjust based on your server setup)
        return filePath.toAbsolutePath().toString(); // Example: use S3 URL for cloud storage
    }


	public void modifyBlog(Blogs blog, MultipartFile file) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
