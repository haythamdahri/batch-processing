package com.batch.processing.services.impl;

import com.batch.processing.services.EmailContentBuilder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class EmailContentBuilderImpl implements EmailContentBuilder {

    private final SpringTemplateEngine templateEngine;

    public EmailContentBuilderImpl(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public String buildBatchNotificationMessage() {
        Context context = new Context();
        return templateEngine.process("mailing/batch-notification", context);
    }

}
