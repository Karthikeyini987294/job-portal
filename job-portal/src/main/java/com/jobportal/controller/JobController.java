package com.jobportal.controller;


	import java.util.List;

	import org.springframework.web.bind.annotation.RequestParam;
	
	import com.jobportal.entity.Job;
	import org.springframework.web.bind.annotation.PathVariable;

	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;
	import com.jobportal.service.JobService;
	import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.DeleteMapping;
	import org.springframework.data.domain.Page;
	import org.springframework.data.domain.Pageable;
	import org.springframework.data.web.PageableDefault;
	import jakarta.validation.Valid;


	@RestController
	
	@RequestMapping("/api/jobs")
	public class JobController {

	    private final JobService jobService;

	    public JobController(JobService jobService) {
	        this.jobService = jobService;
	    }

//	    @PostMapping
//	    public Job createJob(@RequestBody Job job) {
//	        return jobService.createJob(job);
//	    }
	    @PostMapping
	    public Job createJob(@Valid @RequestBody Job job) {
	        return jobService.createJob(job);
	    }

	    @GetMapping
	    public List<Job> getAllJobs() {
	        return jobService.getAllJobs();
	    }
	    @GetMapping("/{id}")
	    public Job getJobById(@PathVariable Long id) {
	        return jobService.getJobById(id);
	    }
	    @PutMapping("/{id}")
	    public Job updateJob(@PathVariable Long id, @RequestBody Job job) {
	        return jobService.updateJob(id, job);
	    }
	    @DeleteMapping("/{id}")
	    public String deleteJob(@PathVariable Long id) {
	        jobService.deleteJob(id);
	        return "Job deleted successfully";
	    }
	    @GetMapping("/search")
	    public List<Job> searchJobsByTitle(@RequestParam String title) {
	        return jobService.searchJobsByTitle(title);
	    }
	    @GetMapping("/location")
	    public List<Job> searchJobsByLocation(@RequestParam String location) {
	        return jobService.searchJobsByLocation(location);
	    }
	    @GetMapping("/type")
	    public List<Job> filterJobsByEmploymentType(
	            @RequestParam String employmentType) {
	        return jobService.filterJobsByEmploymentType(employmentType);
	    }
	    @GetMapping("/page")
	    public Page<Job> getJobsWithPagination(
	            @PageableDefault(size = 5) Pageable pageable) {
	        return jobService.getJobsWithPagination(pageable);
	    }
//	    @GetMapping("/sort")
//	    public List<Job> getJobsSortedBySalary() {
//	        return jobService.getJobsSortedBySalary();
//	    }
	    @GetMapping("/sort")
	    public List<Job> getJobsSorted(
	            @RequestParam(defaultValue = "salary") String field,
	            @RequestParam(defaultValue = "desc") String direction) {

	        return jobService.getJobsSorted(field, direction);
	    }
	   
	}

