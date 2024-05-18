package com.GuideIn.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);
	Optional<User> findByEmailAndRole(String email, Role role);
	Optional<User> findByMobileAndRole(String mobile, Role role);
	void deleteByEmailAndRole(String email, Role role);
	  
}
