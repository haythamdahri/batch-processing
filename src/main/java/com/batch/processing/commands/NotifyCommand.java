package com.batch.processing.commands;

import com.batch.processing.bo.TBConfig;
import com.batch.processing.services.EmailContentBuilder;
import com.batch.processing.services.MailingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service("NOTIFY")
@Log4j2
public class NotifyCommand implements ICommand {

    private final MailingService mailingService;
    private final EmailContentBuilder emailContentBuilder;

    public NotifyCommand(MailingService mailingService, EmailContentBuilder emailContentBuilder) {
        this.mailingService = mailingService;
        this.emailContentBuilder = emailContentBuilder;
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

}
