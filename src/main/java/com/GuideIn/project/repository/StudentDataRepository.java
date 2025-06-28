package com.GuideIn.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GuideIn.project.model.StudentData;


@Repository
public interface StudentDataRepository extends JpaRepository<StudentData,Long>{

}
