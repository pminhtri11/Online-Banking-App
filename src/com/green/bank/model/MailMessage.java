package com.green.bank.model;

import lombok.Data;

@Data
public class MailMessage {
	String host;
	String to;
	String from, recipient, subject, text;
	
}