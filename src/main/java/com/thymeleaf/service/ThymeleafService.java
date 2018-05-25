package com.thymeleaf.service;

import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@Service
public class ThymeleafService {

    private final SendGrid sendGrid;
    private TemplateEngine templateEngine;
    private final String MY_WORK_EMAIL = "work@gmail.com";
    private final String MY_PERSONAL_EMAIL = "personal@gmail.com";
    private final String MY_NAME = "Name";
    private static final String REQUEST_ENDPOINT = "mail/send";

    @Autowired
    public ThymeleafService(SendGrid sendGrid, TemplateEngine templateEngine) {
        this.sendGrid = sendGrid;
        this.templateEngine = templateEngine;
    }

    public void sendEmail() {
        Context context = new Context();
        context.setVariable("contenttitle", "Thymeleaf Goal");
        context.setVariable("contenttext", "It works. I am so happy.");
        context.setVariable("loginurl", "www.google.com");
        context.setVariable("currentyear", new Date().getYear());

        final Content htmlContent = new Content(MimeTypeUtils.TEXT_HTML_VALUE, this.templateEngine.process("email-base.html", context));

        Mail mail = new Mail();
        Personalization personalization = new Personalization();

        mail.setFrom(new Email(MY_PERSONAL_EMAIL, MY_NAME));

        personalization.addTo(new Email(MY_WORK_EMAIL, MY_NAME));
        personalization.setSubject("Testing basic email");
        mail.addContent(htmlContent);
        mail.addPersonalization(personalization);

        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = REQUEST_ENDPOINT;
            request.body = mail.build();
            sendGrid.api(request);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
