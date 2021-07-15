package com.batch.processing.tasks;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Log4j2
public class DirectoryProcessingTasks implements Tasklet {

    @Value("${batch.processing.input-file}")
    private String inputFile;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info("========== Directory processing task is running ==========");
        return RepeatStatus.FINISHED;
    }
}
