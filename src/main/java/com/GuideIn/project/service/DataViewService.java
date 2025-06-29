package com.GuideIn.project.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.GuideIn.project.controller.TemplateGenearationController;
import com.GuideIn.project.model.Certification;
import com.GuideIn.project.model.Company;
import com.GuideIn.project.model.CourseInfo;
import com.GuideIn.project.model.StudentData;
import com.GuideIn.project.model.YouTubeVideo;
import com.GuideIn.project.repository.CertificationRepository;
import com.GuideIn.project.repository.CompanyRepository;
import com.GuideIn.project.repository.CourseInfoRepository;
import com.GuideIn.project.repository.StudentDataRepository;
import com.GuideIn.project.repository.YouTubeVideoRepository;

@Service
public class DataViewService {

    private final TemplateGenearationController templateGenearationController;
	
	@Autowired
	private CourseInfoRepository courseInfoRepository;
	
	@Autowired 
	private YouTubeVideoRepository youTubeVideoRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CertificationRepository certificationRepository;
	
	@Autowired
	private StudentDataRepository studentDataRepository;
	
	@Value("${uploadfile.path}")
	private String uploadDir;

    DataViewService(TemplateGenearationController templateGenearationController) {
        this.templateGenearationController = templateGenearationController;
    }

	public Map<String, Object> getDataWithPagenation(Map<String, String> allparams, int page, int size) {
		if(allparams.get("type").equals("youtube")) {
			return getYoutubeData(allparams,page,size);
		}
		else if(allparams.get("type").equals("certificate")) {
			return getCertificateData(allparams,page,size);
		}
		else if(allparams.get("type").equals("career")) {
			return companyDetails(allparams,page,size);
		}
		else if(allparams.get("type").equals("courseInfo")) {
			return courseDetails(allparams,page,size);
		}
		else if(allparams.get("type").equals("student_info")) {
			return studentInfo(allparams,page,size);
		}
		
		return null;
	}

	private Map<String, Object> studentInfo(Map<String, String> allparams, int page, int size) {
		
//			String courseCategory=allparams.getOrDefault("courseCategory", null);
//			String modeOfClass=allparams.getOrDefault("modeOfClass", null);
		    Pageable pageable = PageRequest.of(page, size);
		    Page<StudentData> data;

		   
		        data=studentDataRepository.findAll(pageable);
		    

		    Map<String, Object> response = new HashMap<>();
		    response.put("content", data.getContent());
		    response.put("currentPage", data.getNumber());
		    response.put("totalItems", data.getTotalElements());
		    response.put("totalPages", data.getTotalPages());
		    response.put("pageSize", data.getSize());
		    response.put("isLastPage", data.isLast());
		    return response;
		

	}

	private Map<String, Object> getCertificateData(Map<String, String> allparams, int page, int size) {
		// TODO Auto-generated method stub
		
		String courseCategory=allparams.getOrDefault("courseCategory", null);
		String duration=allparams.getOrDefault("duration",null);
//		String modeOfClass=allparams.getOrDefault("modeOfClass", null);
	    Pageable pageable = PageRequest.of(page, size);
	    Page<Certification> videoPage;

	   
	        videoPage = certificationRepository.getdataByFilter(courseCategory,duration, pageable);
	    

	    Map<String, Object> response = new HashMap<>();
	    response.put("content", videoPage.getContent());
	    response.put("currentPage", videoPage.getNumber());
	    response.put("totalItems", videoPage.getTotalElements());
	    response.put("totalPages", videoPage.getTotalPages());
	    response.put("pageSize", videoPage.getSize());
	    response.put("isLastPage", videoPage.isLast());
	    return response;
	}

	private Map<String, Object> courseDetails(Map<String, String> allparams, int page, int size) {
		// Location, Price, Course Duration,Mode of class
		String location=allparams.getOrDefault("location", null);
		String modeOfClass=allparams.getOrDefault("modeOfClass", null);
	    Pageable pageable = PageRequest.of(page, size);
	    Page<CourseInfo> videoPage;

	   
	        videoPage = courseInfoRepository.findByFilters(location,modeOfClass, pageable);
	    

	    Map<String, Object> response = new HashMap<>();
	    response.put("content", videoPage.getContent());
	    response.put("currentPage", videoPage.getNumber());
	    response.put("totalItems", videoPage.getTotalElements());
	    response.put("totalPages", videoPage.getTotalPages());
	    response.put("pageSize", videoPage.getSize());
	    response.put("isLastPage", videoPage.isLast());
	    return response;
	}

	private Map<String, Object> companyDetails(Map<String, String> allparams, int page, int size) {
		// TODO Auto-generated method stub
//		Industry , Hiring Status and Search by Company name
		
		System.out.println("i"
				+ "n the career page====>");
		try {
		String industry=allparams.getOrDefault("industry", null);
		String hiringstatus=allparams.getOrDefault("hiringStatus", null);
		String companyName=allparams.getOrDefault("companyName", null);
		 Pageable pageable = PageRequest.of(page, size);
		Page<Company> careerdata;

		   
		careerdata = companyRepository.findByFilter(industry, hiringstatus,companyName,pageable);
    

    Map<String, Object> response = new HashMap<>();
    response.put("content", careerdata.getContent());
    response.put("currentPage", careerdata.getNumber());
    response.put("totalItems", careerdata.getTotalElements());
    response.put("totalPages", careerdata.getTotalPages());
    response.put("pageSize", careerdata.getSize());
    response.put("isLastPage", careerdata.isLast());
    return response;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	private Map<String, Object> getYoutubeData(Map<String, String> allparams, int page, int size) {
		 String topic = allparams.getOrDefault("topic", null);
		    Pageable pageable = PageRequest.of(page, size);
		    Page<YouTubeVideo> videoPage;

		   
		        videoPage = youTubeVideoRepository.findByTopic(topic, pageable);
		    

		    Map<String, Object> response = new HashMap<>();
		    response.put("content", videoPage.getContent());
		    response.put("currentPage", videoPage.getNumber());
		    response.put("totalItems", videoPage.getTotalElements());
		    response.put("totalPages", videoPage.getTotalPages());
		    response.put("pageSize", videoPage.getSize());
		    response.put("isLastPage", videoPage.isLast());
		    return response;
	}

	public Object getDropdownsBasedOnType(Map<String, String> allparams) {
		String type=allparams.get("type");
		
		if(allparams.get("type").equals("youtube")) {
			return getYoutubeDropDown(allparams);
		}
		else if(allparams.get("type").equals("certificate")) {
			return getCertificateDropDown(allparams);
		}
		else if(allparams.get("type").equals("career")) {
			return companyDetailsDropDown(allparams);
		}
		else if(allparams.get("type").equals("courseInfo")) {
			return courseDetailsDropDown(allparams);
		}
		else return null;
		
	}

	private Object courseDetailsDropDown(Map<String, String> allparams) {
		Map<String,Object> data=new HashMap<>();
		List<String> values=	courseInfoRepository.getDistinctdropDowns();
		Set<String> location=new HashSet<>();
		Set<String> price=new HashSet<>();
		Set<String> courseDuration=new HashSet<>();
		Set<String> modeOfClass=new HashSet<>();
		for(String value:values) {
			String arr[]=value.split(",");
			if(arr.length==4){
			location.add(arr[0]);
			price.add(arr[1]);
			courseDuration.add(arr[2]);
			modeOfClass.add(arr[3]);
			}
		}
		data.put("location", location);
		data.put("price", price);
		data.put("courseDuration", courseDuration);
		data.put("modeOfClass", modeOfClass);
		return data;
	}

	private Object companyDetailsDropDown(Map<String, String> allparams) {
		Map<String,Object> data=new HashMap<>();
	List<String> values=	companyRepository.getDistinctdropDowns();
	Set<String> industry=new HashSet<>();
	Set<String> hiringStatus=new HashSet<>();
	Set<String> companyName=new HashSet<>();
	for(String value:values) {
		String arr[]=value.split(",");
		if(arr.length==3){
		industry.add(arr[0]);
		
		hiringStatus.add(arr[1]);
		
		companyName.add(arr[2]);
		}
	}
	data.put("industry", industry);
	data.put("hiringStatus", hiringStatus);
	data.put("companyName", companyName);
	return data;
		
	}

	private Object getCertificateDropDown(Map<String, String> allparams) {
		Map<String,Object> data=new HashMap<>();
	List<String> courseCategory=	certificationRepository.getdistinctCourseCatogory();
	List<String> courseDuration=	certificationRepository.getdistinctDuration();
	data.put("courseCategory",courseCategory);
	data.put("courseDuration", courseDuration);
	return data;
	
	
		
	}

	private Object getYoutubeDropDown(Map<String, String> allparams) {
		Map<String,Object> data=new HashMap<>();
//		String topic=allparams.getOrDefault("topic",null);
//		String duration=allparams.getOrDefault("duration", null);
		List<String> topic=youTubeVideoRepository.getdisinctTopics();
		
		List<String> duration=youTubeVideoRepository.getdistictduration();
		data.put("topic", topic);
		data.put("duration", duration);
		return data;
	}

	public Object getRecord(String type, Long id) {
		Object object=null;
		if(type.equals("youtube")) {
			object=youTubeVideoRepository.findById(id).orElse(null);
			
			
		}
		else if(type.equals("certificate")) {
			
			object=certificationRepository.findById(id).orElse(null);;
			
		}
		else if(type.equals("career")) {
			
			
			object=companyRepository.findById(id).orElse(null);;
			
		}
		else if(type.equals("courseInfo")) {
			object=courseInfoRepository.findById(id).orElse(null);;
		}
		
		return object;
	}
	
	public void saveStudentData(StudentData studentdata) throws Exception{
		studentdata.setLocalDate(LocalDate.now());
		studentDataRepository.save(studentdata);
		
	}

	public void modifyRecord(MultipartFile file, Object value, String type) {
		try {
		if(type.equals("youtube")) {
			YouTubeVideo vedio=(YouTubeVideo)value;
			if(file!=null)
			vedio.setFilePath(saveMultipartFile(file));
			youTubeVideoRepository.save(vedio);
			
			
		}
		else if(type.equals("certificate")) {
			Certification certificate=	(Certification)value;
			if(file!=null)
			certificate.setFilePath(saveMultipartFile(file));
		certificationRepository.save(certificate);
			
		}
		else if(type.equals("career")) {
			
			Company company=(Company)value;
			if(file!=null)
			company.setFilePath(saveMultipartFile(file));
			companyRepository.save(company);
			
		}
		else if(type.equals("courseInfo")) {
			CourseInfo courseInfo=(CourseInfo)value;
			if(file!=null)
			courseInfo.setFilePath(saveMultipartFile(file));
			courseInfoRepository.save(courseInfo);
		}
		else {
			
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public  String saveMultipartFile(MultipartFile multipartFile) throws IOException {
        // Create the directory if it does not exist
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Generate file name with timestamp
        String originalFilename = multipartFile.getOriginalFilename();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileExtension = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }

        String newFileName = "uploaded_" + timestamp + fileExtension;
        Path filePath = Paths.get(uploadDir, newFileName);

        // Save the file to the path
        Files.write(filePath, multipartFile.getBytes());

        // Return the path as a string
        return filePath.toAbsolutePath().toString();
    }
	
	
	
	

}
