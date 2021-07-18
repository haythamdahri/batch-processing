package com.batch.processing.config;

import com.batch.processing.bo.TBConfig;
import com.batch.processing.commands.ICommand;
import com.batch.processing.constants.BatchConstants;
import com.batch.processing.services.TBConfigService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.config.Task;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TBConfigService tbConfigService;
    private final BeanFactory beanFactory;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, TBConfigService tbConfigService, BeanFactory beanFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.tbConfigService = tbConfigService;
        this.beanFactory = beanFactory;
    }

    @Bean(BatchConstants.GCU0)
    public Job jobGCU0() {
        SimpleJobBuilder jobBuilder = this.jobBuilderFactory
                .get(BatchConstants.GCU0)
                .incrementer(new RunIdIncrementer())
                .start(this.stepBuilderFactory
                                .get(BatchConstants.START_COMMAND)
                                .tasklet(this.beanFactory.getBean(BatchConstants.START_COMMAND, Tasklet.class))
                                .build()
                );
        // Get Steps Config
        List<TBConfig> configList = this.tbConfigService.getOrderedTbConfig(BatchConstants.GCU0);
        // Loop through config list and execute commands by provided order
        for( TBConfig tbConfig : configList ) {
            // Get command from db
            Tasklet tasklet = this.beanFactory.getBean(tbConfig.getCommand(), Tasklet.class);
            // Check if command exists then execute it
            jobBuilder.next(this.stepBuilderFactory
                    .get(tbConfig.getCommand())
                    .tasklet(tasklet)
                    .build()
            );
        }
        // Return built job
        return jobBuilder.build();
    }


    @Bean(BatchConstants.GCU1)
    public Job JobGCU1() {
        SimpleJobBuilder jobBuilder = this.jobBuilderFactory
                .get(BatchConstants.GCU1)
                .incrementer(new RunIdIncrementer())
                .start(this.stepBuilderFactory
                        .get(BatchConstants.START_COMMAND)
                        .tasklet(this.beanFactory.getBean(BatchConstants.START_COMMAND, Tasklet.class))
                        .build()
                );
        // Get Steps Config
        List<TBConfig> configList = this.tbConfigService.getOrderedTbConfig(BatchConstants.GCU1);
        // Loop through config list and execute commands by provided order
        for( TBConfig tbConfig : configList ) {
            // Get command from db
            Tasklet tasklet = this.beanFactory.getBean(tbConfig.getCommand(), Tasklet.class);
            // Check if command exists then execute it
            jobBuilder.next(this.stepBuilderFactory
                    .get(tbConfig.getCommand())
                    .tasklet(tasklet)
                    .build()
            );
        }
        // Return built job
        return jobBuilder.build();
    }

}