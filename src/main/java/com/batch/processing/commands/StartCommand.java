package com.batch.processing.commands;

import com.batch.processing.bo.TBConfig;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements ICommand, Tasklet {

    public void execute(TBConfig tbConfig) {
        System.out.println("BATCH PROCESSING START");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        this.execute(null);
        return RepeatStatus.FINISHED;
    }
}
