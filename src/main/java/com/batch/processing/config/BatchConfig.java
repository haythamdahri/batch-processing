package com.batch.processing.config;

import com.batch.processing.tasks.DirectoryProcessingTasks;
import com.batch.processing.tasks.FilesProcessingTasks;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final FilesProcessingTasks filesProcessingTasks;
    private final DirectoryProcessingTasks directoryProcessingTasks;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, FilesProcessingTasks filesProcessingTasks, DirectoryProcessingTasks directoryProcessingTasks) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.filesProcessingTasks = filesProcessingTasks;
        this.directoryProcessingTasks = directoryProcessingTasks;
    }

    @Bean
    public Step filesProcessingStep() {
        return this.stepBuilderFactory
                .get("FILES_PROCESSING_STEP")
                .tasklet(this.filesProcessingTasks)
                .build();
    }

    @Bean
    public Step directoryProcessingStep() {
        return this.stepBuilderFactory
                .get("DIRECTORY_PROCESSING_STEP")
                .tasklet(this.directoryProcessingTasks)
                .build();
    }

    @Bean("PROCESSING_JOB")
    public Job processingJob() {
        return this.jobBuilderFactory
                .get("PROCESSING_JOB")
                .incrementer(new RunIdIncrementer())
                .start(this.filesProcessingStep())
                .next(this.directoryProcessingStep())
                .build();
    }

}