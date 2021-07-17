package com.batch.processing.services.impl;

import com.batch.processing.services.EmailContentBuilder;
import com.batch.processing.services.MailingService;
import com.batch.processing.utils.MailingUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;

@Service
public class MailingServiceImpl implements MailingService {

    private final EmailContentBuilder mailContentBuilder;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public MailingServiceImpl(EmailContentBuilder mailContentBuilder, JavaMailSender mailSender) {
        this.mailContentBuilder = mailContentBuilder;
        this.mailSender = mailSender;
    }

    /**
     * Send templated message without any attachments
     * @param to: Receiver
     * @param subject: Subject of email
     * @param text: Content
     */
    @Override
    @Async
    public void sendMessage(String to, String subject, String text) {
        try {
            MimeMessage message = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = MailingUtils.buildMimeMessageHelper(this.from, to, subject, text, message, true);
            this.mailSender.send(helper.getMimeMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Send templated message with an attachment
     * @param to: Receiver
     * @param subject: Subject of email
     * @param text: Content
     */
    @Override
    @Async
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
        try {
            MimeMessage message = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = MailingUtils.buildMimeMessageHelper(this.from, to, subject, text, message, true);
            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            // Add file attachment
            helper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            mailSender.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
