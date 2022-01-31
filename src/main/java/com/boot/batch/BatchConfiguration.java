package com.boot.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfigurer {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	BatchLogTester BatchLogger;
	
	@Autowired
	BatchLogTesterTasklet batchLogTesterTasklet;	
	
	private static int count = 0;
	
	@Bean
	public Step teststep() {
		return stepBuilderFactory.get("accountExtractStep").tasklet(batchLogTesterTasklet)
				.allowStartIfComplete(true).build();
	}
	
	@Bean
	public Job testjob() {
		return this.jobBuilderFactory.get("testjob").start(teststep()).build();
	}

	
}
