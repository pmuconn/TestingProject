package com.boot.batch;

import java.util.Date;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatchLogTesterTasklet implements Tasklet, InitializingBean {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	BatchLogTester BatchLogger;

	private static int count = 0;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		return this.executeProcess(chunkContext);

//		return RepeatStatus.FINISHED;
	}

    private RepeatStatus executeProcess(ChunkContext chunkContext) throws InterruptedException {
        boolean result = false;
        
		Date rundate = new Date();
		
		if (ThreadContext.isEmpty()) {
			ThreadContext.put("logFileName", "TestBatch-run_" + rundate.getTime());
		}
		
		
		logger.info("Logging hello world " + count);
		System.out.println(count + ". Hello, World!");
		
		BatchLogger.getHelloworld();

		count++;

		if (count < 5) {
			return RepeatStatus.CONTINUABLE;
		} else {
			count = 0;
			ThreadContext.remove("logFileName");
			ThreadContext.clearAll();
			return RepeatStatus.FINISHED;
		}
    }
	
}
