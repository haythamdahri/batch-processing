package com.batch.processing.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class ApplicationRunningConfiguration {

    private static final String TIME = "time";
    private static final String JOB_NAME = "jobName";
    private static final String PROCESSING_JOB = "PROCESSING_JOB";

    private final JobLauncher jobLauncher;
    private final Job processingJob;

    public ApplicationRunningConfiguration(JobLauncher jobLauncher,
                                           @Qualifier(PROCESSING_JOB) Job processingJob) {
        this.jobLauncher = jobLauncher;
        this.processingJob = processingJob;
    }

    @Scheduled(cron = "0 58 20 * * *", zone = "Europe/Lisbon")
    public void runBatchProcessing() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addLong(TIME, System.currentTimeMillis()).toJobParameters();
        jobParametersBuilder.addString(JOB_NAME, PROCESSING_JOB).toJobParameters();
        // Run Job
        this.jobLauncher.run(this.processingJob, jobParametersBuilder.toJobParameters());
    }

}
