package com.batch.processing.commands;

import com.batch.processing.bo.TBConfig;
import com.batch.processing.constants.BatchConstants;
import com.batch.processing.services.EmailContentBuilder;
import com.batch.processing.services.MailingService;
import com.batch.processing.services.TBConfigService;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@Service("NOTIFY")
@Log4j2
public class NotifyCommand implements ICommand, Tasklet {

    private final MailingService mailingService;
    private final EmailContentBuilder emailContentBuilder;
    private final TBConfigService tbConfigService;

    public NotifyCommand(MailingService mailingService, EmailContentBuilder emailContentBuilder, TBConfigService tbConfigService) {
        this.mailingService = mailingService;
        this.emailContentBuilder = emailContentBuilder;
        this.tbConfigService = tbConfigService;
    }

    /**
     * Custom command to notify users by finished task
     *
     * @param tbConfig: Database Object
     */
    @Override
    public void execute(TBConfig tbConfig) {
        // Send email notification
        this.mailingService.sendMessage("haytham.dahri@gmail.com", "BATCH PROCESSING", this.emailContentBuilder.buildBatchNotificationMessage());
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        // Get TBConfig from execution context
        String jobId = (String) chunkContext.getStepContext().getJobParameters().get(BatchConstants.BATCH_NAME);
        TBConfig tbConfig = this.tbConfigService.getTbConfig(jobId, "NOTIFY");
        // Execute
        this.execute(tbConfig);
        // Return Status
        return RepeatStatus.FINISHED;
    }

}
