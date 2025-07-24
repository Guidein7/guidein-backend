package com.GuideIn.project.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.GuideIn.project.model.Blogs;
import com.GuideIn.project.service.BlogService;

@RestController
@RequestMapping("api/v1/admin")
@CrossOrigin("*")
public class AdminController {
	
	@Autowired
	private BlogService blogService;
	
	@GetMapping("/get-blogs")
    public ResponseEntity<Map<String, Object>> getAllBlogs(
            @RequestParam Map<String, String> allParams,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        try {
            Map<String, Object> response = blogService.getBlogsSaved(allParams, page, size);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch blogs"));
        }
    }

	
	@PostMapping(value="/create-blog",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> createBlog(@RequestPart("blog") Blogs blog,@RequestPart("file") MultipartFile file){
		try {
			if(file==null) {
				System.out.println("file is null");
			}
			blogService.createNewBlog(blog,file);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/upload/image")
	public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {

	    String fileWithoutSpace = file.getOriginalFilename()
	                                  .replaceAll("\\s+", "_")
	                                  .replaceAll("[()]", "");
	    String fileName = UUID.randomUUID() + "_" + fileWithoutSpace;

	    Path uploadDir = Paths.get("uploads");
	    if (!Files.exists(uploadDir)) {
	        Files.createDirectories(uploadDir);
	    }
	    System.out.println("ROOT DIR: " + System.getProperty("user.dir"));
	    // ✅ CORRECT way to build full path
	    Path path = uploadDir.resolve(fileName);
	    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

	    System.out.println("Saved at: " + path.toAbsolutePath());

	    String imageUrl = "http://localhost:8080/uploads/" + fileName;
	    return ResponseEntity.ok(Map.of("url", imageUrl));
	}
	
	@PostMapping(value="/modify-blog",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> modifyBlog(@RequestPart("blog") Blogs blog,@RequestPart("file") MultipartFile file){
		try {
			if(file==null) {
				System.out.println("file is null");
			}
			blogService.modifyBlog(blog,file);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	 

}
