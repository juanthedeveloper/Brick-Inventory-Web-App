package com.jcpdev.brickinventoryserver;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailService {

    public static Message message;

    public EmailService(){
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Constants.EMAIL, Constants.EMAILPASSWORD);
                    }
                });

        message  = new MimeMessage(session);
    }



    public void sendLowStockEmail(String bodyMessage, String date) throws MessagingException {

        message.setFrom(new InternetAddress("bricktacticalinventory@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse("bricktacticalinventory@gmail.com"));
        message.setSubject("BT Inventory RE-ORDER "+date);


        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(bodyMessage, "text/html; charset=utf-8");


        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }

}
