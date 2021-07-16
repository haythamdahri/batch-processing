package com.batch.processing;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "com.batch.processing")
public class BatchProcessingProperties {

    private BatchSingleConfig[] jobs;

    public BatchProcessingProperties() {
    }

    public BatchProcessingProperties(BatchSingleConfig[] jobs) {
        this.jobs = jobs;
    }

    public BatchSingleConfig[] getJobs() {
        return jobs;
    }

    public void setJobs(BatchSingleConfig[] jobs) {
        this.jobs = jobs;
    }
}
