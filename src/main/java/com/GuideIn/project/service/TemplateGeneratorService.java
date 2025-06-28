package com.GuideIn.project.service;

import java.io.ByteArrayOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TemplateGeneratorService {
	
	public ResponseEntity<byte[]> generateTemplate(String type){
		try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Video Template");

            String[] columns =null ;
            if(type.equals("youtube")){
            columns=new String[]	{
            
                    "Topic", "Video Title", "Total Views", "YouTube URL", "Channel Name",
                    "Duration (hh:mm or mins)", "Short Description", "Tags (e.g., Project-based, Updated 2024)"
            };
            }
            else if(type.equals("career")) {
            	columns=new String[]{
                    "Company Name", "Industry", "Company Overview (1-2 lines)", "Career Page URL",
                    "Hiring Status (Hiring Now / Not Hiring / Unknown)", "Total Employees (Approx.)",
                    "Hiring Growth (Last 6 Months, %)", "Median Tenure (Years)",
                    "Rating_Ambitionbox (out of 5)", "Number of Reviews_AB",
                    "Rating_Glassdoor (out of 5)", "Number of Reviews_GS",
                    "Tags (e.g., Fresher Friendly, WFH, etc.)"
                };
            }
            else if(type.equals("courseInfo")) {
            	columns=new String[] {
                        "Course Name", "Institute Name", "Location", "Address", "Mobile Number",
                        "Course Duration", "Mode of Class (Online/Offline/Hybrid)", "Course Batch", "Computer Lab",
                        "Estimated Course Price", "Rating (out of 5)", "Number of Reviews", "Website URL (if any)",
                        "Job Assistance", "Institute Description (Short)", "Map Location", "Tags (e.g., Placement Support, Project-Based)"
                    };
            }
            else if(type.equals("certificate")) {
            	columns=new String[]  {
            			  "Course Category",
            			  "Course Title",
            			  "Provider (Google, IBM, etc.)",
            			  "Platform (Coursera, edX, etc.)",
            			  "Course Duration",
            			  "Course Link (URL to Access)",
            			  "Short Description"
            			};
            }
           

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Autosize columns
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=video_template.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(out.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
		
		
		
	}

}
