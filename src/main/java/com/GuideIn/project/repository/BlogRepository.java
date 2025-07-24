package com.GuideIn.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GuideIn.project.model.Blogs;

@Repository
public interface BlogRepository extends JpaRepository<Blogs,Long>{

}
