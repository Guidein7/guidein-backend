package com.GuideIn.project.controller;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Value("${file.upload-dir}")
	private String uploadDir;
	
//	    @Override
//	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//	    	String uploadPath = Paths.get("uploads").toAbsolutePath().toUri().toString();
//	    	System.out.println("Configuring static resource handler for uploads: " + uploadPath);
//
//	        registry.addResourceHandler("/uploads/**")
//	        .addResourceLocations("file:///" + uploadDir);
//	    }
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    String uploadPath = Paths.get(System.getProperty("user.dir"), "uploads").toUri().toString();
	    System.out.println("Registering static file path: " + uploadPath);  // Should print file:/C:/blogadding/guidein-backend/uploads/
	    registry.addResourceHandler("/uploads/**")
	            .addResourceLocations(uploadPath);
	}
}
