package com.batch.processing.services;

public interface MailingService {

    void sendMessage(String to, String subject, String text);

    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment);

}
