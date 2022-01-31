package com.boot.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {
	
	private final Logger logger = LoggerFactory.getLogger(BatchController.class.getName());
	

	@Autowired
    JobLauncher jobLauncher;

    @Autowired
	@Qualifier("testjob")
    Job job;

    @RequestMapping("/test/jobLauncher.html")
    public void handle() throws Exception{
        jobLauncher.run(job, new JobParameters());
    }
	
}
