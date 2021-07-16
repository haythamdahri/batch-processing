package com.check;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBootBatchApplication implements CommandLineRunner {

    private static final String TIME = "time";
    private static final String JOB_NAME = "jobName";
    private static final String PROCESSING_JOB = "PROCESSING_JOB";
    private static final String JOB_ID = "JOB_ID";

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    @Qualifier(PROCESSING_JOB)
    private Job processingJob;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Get Jobs From Database
        List<String> jobIds =  Arrays.asList("GMT1", "GMT2");
        // Loop through jobs
        jobIds.forEach(job -> {
            try {
                JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
                jobParametersBuilder.addLong(TIME, System.currentTimeMillis()).toJobParameters();
                jobParametersBuilder.addString(JOB_NAME, PROCESSING_JOB).toJobParameters();
                jobParametersBuilder.addString(JOB_ID, job).toJobParameters();
                // Run Job
                this.jobLauncher.run(this.processingJob, jobParametersBuilder.toJobParameters());
            } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
                e.printStackTrace();
            }
        });
    }

    @Component
    static class Task implements Tasklet {

        @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
            // Get data from chunk context
            Object data = chunkContext.getStepContext().getJobParameters();
            System.out.println(data.toString());
            return RepeatStatus.FINISHED;
        }
    }

    @Component
    static class EmailsTask implements Tasklet {

        @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
            // Get data from chunk context
            Object data = chunkContext.getStepContext().getJobParameters();
            System.out.println(data.toString());
            return RepeatStatus.FINISHED;
        }
    }

    @Configuration
    static class BatchConfig {

        private final JobBuilderFactory jobBuilderFactory;
        private final StepBuilderFactory stepBuilderFactory;
        private final Task filesProcessingTask;
        private final EmailsTask emailsNotificationsTask;

        public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, Task filesProcessingTask, EmailsTask emailsNotificationsTask) {
            this.jobBuilderFactory = jobBuilderFactory;
            this.stepBuilderFactory = stepBuilderFactory;
            this.filesProcessingTask = filesProcessingTask;
            this.emailsNotificationsTask = emailsNotificationsTask;
        }

        @Bean
        public Step filesProcessingStep() {
            return this.stepBuilderFactory
                    .get("FILES_PROCESSING_STEP")
                    .tasklet(this.filesProcessingTask)
                    .build();
        }

        @Bean
        public Step emailsNotificationsStep() {
            return this.stepBuilderFactory
                    .get("EMAILS_NOTIFICATIONS_STEP")
                    .tasklet(this.emailsNotificationsTask)
                    .build();
        }

        @Bean("PROCESSING_JOB")
        public Job processingJob() {
            return this.jobBuilderFactory
                    .get("PROCESSING_JOB")
                    .incrementer(new RunIdIncrementer())
                    .start(this.filesProcessingStep())
                    .on("COMPLETED")
                    .to(this.emailsNotificationsStep())
                    .end()
                    .build();
        }
    }

}
