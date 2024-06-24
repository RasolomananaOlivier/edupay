package com.edupay.util;

import jakarta.mail.Address;

import java.util.List;
import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Mail {
    public static void send(String body, String receiver) {
        send(body, new String[] { receiver });
    }

    public static void send(String body, String[] receivers) {
        try {
            Session session = getSession();
            session.setDebug(true);

            Message message = new MimeMessage(session);
            message.setSubject("Bourse d'étude");
            message.setContent(body, "text/html");

            Address sender = new InternetAddress("herimanitraolivierr@gmail.com", "EDUPAY");
            message.setFrom(sender);

            for (String receiver : receivers) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            }

            try (Transport tt = session.getTransport()) {
                tt.connect("herimanitraolivierr@gmail.com", "edlitrqurovbfhin");
                tt.sendMessage(message, message.getAllRecipients());
                System.out.println("Email sent");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void send(String body, List<String> receivers) {
        send(body, receivers.toArray(new String[0]));
    }

    private static Properties getProperties() {
        Properties pros = new Properties();

        pros.put("mail.transport.protocol", "smtps");
        pros.put("mail.smtps.host", "smtp.gmail.com");
        pros.put("mail.smtps.port", 465);
        pros.put("mail.smtps.auth", "true");
        pros.put("mail.smtps.quitwait", "false");

        return pros;
    }

    private static Session getSession() {
        return Session.getDefaultInstance(getProperties());
    }
}
