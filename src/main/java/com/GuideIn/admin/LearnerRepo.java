package com.GuideIn.admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LearnerRepo extends JpaRepository<Learner,Long> {

    Learner findByEmail(String email);
}
