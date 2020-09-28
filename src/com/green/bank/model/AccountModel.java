package com.green.bank.model;

import com.green.bank.util.AccountInvalidException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountModel {
	String accountNo;
	String fName;
	String lName;
	String address;
	String city;
	String branch;
	String zip;
	String username;
	String password;
	String registerDate;
	String pNumber;
	String email;
	String accountType;
	int amount;
	
}