package com.GuideIn.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.GuideIn.project.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long>{
	
	@Query(value="select * from companies where (?1 is null or industry=?1) and (?2 is null or hiring_status=?2) and (?3 is null or  company_name LIKE CONCAT('%', ?3, '%'))",nativeQuery=true)
	Page<Company> findByFilter(String industry, String hiringstatus, String companyName, Pageable pageable);
	
	@Query(value="select distinct industry,hiring_status,company_name from companies",nativeQuery=true)
	List<String> getDistinctdropDowns();

}
