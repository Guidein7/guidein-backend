package com.GuideIn.project.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.GuideIn.project.model.Certification;
import com.GuideIn.project.model.Company;
import com.GuideIn.project.model.CourseInfo;
import com.GuideIn.project.model.YouTubeVideo;
import com.GuideIn.project.repository.CertificationRepository;
import com.GuideIn.project.repository.CompanyRepository;
import com.GuideIn.project.repository.CourseInfoRepository;
import com.GuideIn.project.repository.YouTubeVideoRepository;

@Service
public class ExcelReadService {
	
	@Autowired
	private CourseInfoRepository courseInfoRepository;
	
	@Autowired 
	private YouTubeVideoRepository youTubeVideoRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CertificationRepository certificationRepository;
	
	public void importCoursesFromExcel(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            List<CourseInfo> courseList = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // skip header row
                Row row = sheet.getRow(i);
                if (row == null) continue;

                CourseInfo course =new CourseInfo();
                course.setCourseName(getCellValue(row, 0));
                 course.setInstituteName(getCellValue(row, 1));       
                course.setLocation(getCellValue(row, 2));       
                 course.setAddress(getCellValue(row, 3));
                 course.setMobileNumber(getCellValue(row, 4));
                 course.setCourseDuration(getCellValue(row, 5));
                 course.setModeOfClass(getCellValue(row, 6));
                 course.setCourseBatch(getCellValue(row, 7));
                 course.setComputerLab(getCellValue(row, 8));
                 course.setEstimatedCoursePrice(getCellValue(row, 9));
                 course.setRating( getCellValue(row, 10));
                   course.setNumberOfReviews(getCellValue(row, 11));
                       course.setWebsiteUrl(getCellValue(row, 12));
                       course.setJobAssistance(getCellValue(row, 13));
                       course.setInstituteDescription(getCellValue(row, 14));
                       course.setMapLocation(getCellValue(row, 15));
                        course.setTags(getCellValue(row, 16));
                       
                        

                courseList.add(course);
            }

            courseInfoRepository.saveAll(courseList);

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Excel file: " + e.getMessage(), e);
        }
    }

    private String getCellValue(Row row, int index) {
        Cell cell = row.getCell(index);
        return (cell == null) ? "" : cell.toString().trim();
    }
    
	public void importYoutubeFromExcel(MultipartFile file) {
		// TODO Auto-generated method stub
		try (Workbook wb = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = wb.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();

            List<YouTubeVideo> list = new ArrayList<>();
            // Assuming first row is header
            for (int i = 1; i < rows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                YouTubeVideo v = new YouTubeVideo();
                v.setTopic(getString(row.getCell(0)));
                v.setVideoTitle(getString(row.getCell(1)));
                v.setTotalViews( getString(row.getCell(2)));
                v.setYoutubeUrl(getString(row.getCell(3)));
                v.setChannelName(getString(row.getCell(4)));
                v.setDuration(getString(row.getCell(5))); 
                v.setShortDescription(getString(row.getCell(6)));
                v.setTags(getString(row.getCell(7)));

                list.add(v);
            }
            youTubeVideoRepository.saveAll(list);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("failed to save data");
        }
    

   
	}
	
	 private String getString(Cell cell) {
	        if (cell == null) return null;
	        if (cell.getCellType() == CellType.STRING) return cell.getStringCellValue();
	        else if (cell.getCellType() == CellType.NUMERIC)
	            return DateUtil.isCellDateFormatted(cell)
	                ? cell.getLocalDateTimeCellValue().toString()
	                : Double.toString(cell.getNumericCellValue());
	        else return cell.toString();
	    }

	 public void importCompanyFromExcel(MultipartFile file) throws Exception{
		 try (Workbook wb = WorkbookFactory.create(file.getInputStream())) {
	            Sheet sheet = wb.getSheetAt(0);
	            List<Company> list = new ArrayList<>();

	            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	                Row row = sheet.getRow(i);
	                if (row == null) continue;

	                Company c = new Company();
	                c.setCompanyName(getString(row.getCell(0)));
	                c.setIndustry(getString(row.getCell(1)));
	                c.setCompanyOverview(getString(row.getCell(2)));
	                c.setCareerPageUrl(getString(row.getCell(3)));
	                c.setHiringStatus(getString(row.getCell(4)));
	                c.setTotalEmployees( getString(row.getCell(5)));
	                c.setHiringGrowth(getString(row.getCell(6)));
	                c.setMedianTenure(getString(row.getCell(7)));
	                c.setRatingAmbitionbox(getString(row.getCell(8)));
	                c.setNumberOfReviewsAB( getString(row.getCell(9)));
	                c.setRatingGlassdoor(getString(row.getCell(10)));
	                c.setNumberOfReviewsGS( getString(row.getCell(11)));
	                c.setTags(getString(row.getCell(12)));

	                list.add(c);
	            }

	            companyRepository.saveAll(list);
	          
	        } catch (Exception e) {
	           throw new Exception("failed to save data");
	        }
		
	 }

	 public void importcertificate(MultipartFile file) throws Exception{
		 

		 try (Workbook wb = WorkbookFactory.create(file.getInputStream())) {
	            Sheet sheet = wb.getSheetAt(0);
	            
	            
	            
	      
	            List<Certification> list = new ArrayList<>();

	            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	                Row row = sheet.getRow(i);
	                if (row == null) continue;

	                Certification c = new Certification();
	                c.setCourseCategory(getString(row.getCell(0)));
	                c.setCourseTitle(getString(row.getCell(1)));
	               c.setProvider(getString(row.getCell(2)));
	               c.setPlatform(getString(row.getCell(3)));
	               c.setCourseDuration(getString(row.getCell(4)));
	               c.setCourseLink(getString(row.getCell(5)));
	               c.setShortDescription(getString(row.getCell(6)));
	               

	                list.add(c);
	            }
	            System.out.println("list size is "+list.size());
	            
	            certificationRepository.saveAll(list);
	          
	        } catch (Exception e) {
	           throw new Exception("failed to save data");
	        }
		
	 
	 }

}
