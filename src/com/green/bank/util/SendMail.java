package com.green.bank.util;

import javax.mail.*;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.green.bank.model.MailMessage;

public class SendMail {

	public static void sendMail(MailMessage m) throws AddressException, MessagingException {

		
		String host = "smtp.gmail.com";
		String username = "bankingtest135@gmail.com";
		String password = "bankingProject12";
		
		Properties props = System.getProperties();
		props.put("mail.smtp.host",host);
	    props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");
		
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(username, password);

            }

        });        
        session.setDebug(true);
        
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(m.getTo()));
			message.setSubject(m.getSubject());
			message.setText(m.getText());
			
			Transport transport = session.getTransport("smtp");
	        transport.connect(host, username, password);
            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");

			
		}catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
