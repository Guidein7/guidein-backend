package com.GuideIn.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.GuideIn.project.model.StudentData;
import com.GuideIn.project.service.DataViewService;

@RestController
@RequestMapping("api/v1")
@CrossOrigin("*")
public class DataViewController {
	
	@Autowired
	private DataViewService dataViewService;
	
	@GetMapping("view/data")
	public ResponseEntity<Object> getData(@RequestParam Map<String,String> allparams,@DefaultValue(value="0") int page,@DefaultValue(value="5")int size){
		Map<String,Object> map=new HashMap<>();
		try {
			map=dataViewService.getDataWithPagenation(allparams,page,size);
		}
		catch(Exception e) {
			e.printStackTrace();
			map.put("error", true);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	
	@GetMapping("/drop-down")
	public ResponseEntity<Object> getdropdowns(@RequestParam Map<String,String> allparams){
		try {
		Object	 object=dataViewService.getDropdownsBasedOnType(allparams);
		return new ResponseEntity<>(object,HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
		
		
	}
	
	@GetMapping("/unique-record")
	public ResponseEntity<Object> getuniqueRecord(@RequestParam String type,@RequestParam Long id){
		try {
		Object	 object=dataViewService.getRecord(type,id);
		return new ResponseEntity<>(object,HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
		
		
	}
	
	@PostMapping("/edit-data")
	public ResponseEntity<Object> editDate(@RequestPart(value="file",required=false)  MultipartFile file,@RequestPart("object")Object value,@RequestParam String type
			){
		try {
		dataViewService.modifyRecord(file,value,type);
		return new ResponseEntity<>("data modified successfully",HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
		
		
	}
	
	@PostMapping("/student-data")
	public ResponseEntity<String> saveStudentData(@RequestBody StudentData studentdata){
		try {
		dataViewService.saveStudentData(studentdata);
		return ResponseEntity.status(HttpStatus.OK).body("data saved successfully");
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save data");
		}
	}
}
