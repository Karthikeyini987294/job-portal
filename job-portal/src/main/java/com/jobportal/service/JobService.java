package com.jobportal.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.jobportal.entity.Job;
import com.jobportal.exception.JobNotFoundException;
import com.jobportal.repository.JobRepository;
import java.util.Optional;
import org.springframework.data.domain.Sort;
@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
//    public Optional<Job> getJobById(Long id) {
//        return jobRepository.findById(id);
//    }
    public Job getJobById(Long id) {

        return jobRepository.findById(id)
                .orElseThrow(() ->
                    new JobNotFoundException("Job not found with ID: " + id)
                );
    }
    public Job updateJob(Long id, Job updatedJob) {
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        existingJob.setTitle(updatedJob.getTitle());
        existingJob.setCompanyName(updatedJob.getCompanyName());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setSalary(updatedJob.getSalary());
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setEmploymentType(updatedJob.getEmploymentType());

        return jobRepository.save(existingJob);
    }
    public void deleteJob(Long id) {
        Job existingJob = jobRepository.findById(id)
//        		.orElseThrow(() -> new JobNotFoundException("Job not found with ID: " + id));
        		.orElseThrow(() -> new JobNotFoundException("Job not found with ID: " + id));
        jobRepository.delete(existingJob);
    }
    public List<Job> searchJobsByTitle(String title) {
        return jobRepository.findByTitle(title);
    }
    public List<Job> searchJobsByLocation(String location) {
        return jobRepository.findByLocation(location);
    }
    public List<Job> filterJobsByEmploymentType(String employmentType) {
        return jobRepository.findByEmploymentType(employmentType);
    }
    public Page<Job> getJobsWithPagination(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }
    public List<Job> getJobsSortedBySalary() {
        return jobRepository.findAll(Sort.by("salary").descending());
    }
    public List<Job> getJobsSorted(String field, String direction) {

        if (!List.of("salary", "title", "location").contains(field)) {
            throw new IllegalArgumentException("Invalid sorting field: " + field);
        }

        Sort sort;

        if (direction.equalsIgnoreCase("asc")) {
            sort = Sort.by(field).ascending();
        } else if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(field).descending();
        } else {
            throw new IllegalArgumentException("Invalid sorting direction: " + direction);
        }

        return jobRepository.findAll(sort);
    }
   
}