package com.green.bank.util;

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

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", m.getHost());
		Session session = Session.getDefaultInstance(properties);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(m.getFrom()));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(m.getTo()));
		message.setSubject(m.getSubject());
		message.setText(m.getText());
		Transport.send(message);
	}
}
