package com.green.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MailMessage {
	String to;
	String subject;
	String text;	
	
	public MailMessage(String to, String subject, String text) {
		this.to = to;
		this.subject = subject;
		this.text = text;
	}
}

// bankingtest135@gmail.com
// bankingProject12