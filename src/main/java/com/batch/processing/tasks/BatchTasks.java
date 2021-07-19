package com.batch.processing.tasks;

import com.batch.processing.constants.BatchConstants;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
public class BatchTasks {

    private final JobLauncher jobLauncher;

    private final Job jobGCU0;
    private final Job jobGCU1;

    public BatchTasks(JobLauncher jobLauncher,
                      @Qualifier(BatchConstants.GCU0) Job jobGCU0,
                      @Qualifier(BatchConstants.GCU1) Job jobGCU1) {
        this.jobLauncher = jobLauncher;
        this.jobGCU0 = jobGCU0;
        this.jobGCU1 = jobGCU1;
    }

    @Scheduled(cron = "${com.batch.processing.GCU0.scheduling}")
    public void gcu0Task() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addLong(BatchConstants.TIME, System.currentTimeMillis()).toJobParameters();
        jobParametersBuilder.addString(BatchConstants.BATCH_NAME, BatchConstants.GCU0).toJobParameters();
        // Run Job
        this.jobLauncher.run(this.jobGCU0, jobParametersBuilder.toJobParameters());
    }


    @Scheduled(cron = "${com.batch.processing.GCU1.scheduling}")
    public void gcu1Task() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addLong(BatchConstants.TIME, System.currentTimeMillis()).toJobParameters();
        jobParametersBuilder.addString(BatchConstants.BATCH_NAME, BatchConstants.GCU1).toJobParameters();
        // Run Job
        this.jobLauncher.run(this.jobGCU1, jobParametersBuilder.toJobParameters());
    }

}
