package com.batch.processing.utils;

import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MailingUtils {

    private MailingUtils() {}

    /**
     * Mime message builder
     *
     * @param from:        Email Sender
     * @param to:          Email Receiver
     * @param subject:     Email Subject
     * @param text:        Email Text Content
     * @param message:     Email Message
     * @param isMultipart: Boolean to check if Multipart Exists
     * @return MimeMessageHelper
     * @throws MessagingException: Thrown on unhandled error
     */
    public static MimeMessageHelper buildMimeMessageHelper(String from, String to, String subject, String text, MimeMessage message, boolean isMultipart) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, isMultipart);
        // return built helper
        return helper;
    }

}
