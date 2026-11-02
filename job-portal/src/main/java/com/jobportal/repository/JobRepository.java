package com.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.jobportal.entity.Job;
public interface JobRepository  extends JpaRepository<Job, Long>{
	List<Job> findByTitle(String title);
	List<Job> findByLocation(String location);
	List<Job> findByEmploymentType(String employmentType);
}
