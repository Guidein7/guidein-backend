package com.GuideIn.project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.GuideIn.project.service.ExcelReadService;
import com.GuideIn.project.service.TemplateGeneratorService;


@RestController
@RequestMapping("api/v1")
@CrossOrigin("*")
public class TemplateGenearationController {

	@Autowired
	private TemplateGeneratorService templateGeneratorService;
	
	@Autowired
	private ExcelReadService excelReadService;
	
    @GetMapping("/video-excel-template")
    public ResponseEntity<byte[]> downloadExcelTemplate(@RequestParam Map<String,String> params) {
        
    	return templateGeneratorService.generateTemplate(params.get("type"));

    }
    
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestPart("file") MultipartFile file,@RequestParam String type) {
    	try {
    	if(type.equals("courseInfo")) {
    	excelReadService.importCoursesFromExcel(file);
    	}
    	else if(type.equals("career")) {
    		excelReadService.importCompanyFromExcel(file);
    	}
    	else if(type.equals("youtube")) {
    		excelReadService.importYoutubeFromExcel(file);
    	}
    	else if(type.equals("certificate")) {
    		excelReadService.importcertificate(file);
    	}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    		
    	}
    	
    	return ResponseEntity.status(HttpStatus.OK).body( "File processed successfully.");
         
    }
    
    
}
