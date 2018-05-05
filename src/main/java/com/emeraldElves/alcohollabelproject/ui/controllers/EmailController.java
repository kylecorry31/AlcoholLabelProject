package com.emeraldElves.alcohollabelproject.ui.controllers;

import com.emeraldElves.alcohollabelproject.Data.EmailAddress;
import com.emeraldElves.alcohollabelproject.LogManager;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailController {

    private Properties props;
    private Session session;

    public EmailController(){
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("cs3733teame@gmail.com", "EmeraldElvesD17");
                    }
                });
    }

    public void sendEmail(EmailAddress emailAddress, String subject, String body){
        if (emailAddress == null || !emailAddress.isValid()){
            return;
        }
        Runnable runnable = () -> {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("cs3733teame@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(emailAddress.getEmailAddress()));
                message.setSubject(subject);
                message.setText(body);
                Transport.send(message);
                LogManager.getInstance().log(getClass().getSimpleName(), "Email sent to " + emailAddress.getEmailAddress());
            } catch (MessagingException e) {
                System.err.println("Error sending email to " + emailAddress.getEmailAddress());
            }
        };

        Thread t = new Thread(runnable);
        t.start();
    }

}
