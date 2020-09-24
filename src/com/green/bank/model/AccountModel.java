package com.green.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountModel {
	String account_no;
	String first_name;
	String last_name;
	String address;
	String city;
	String branch;
	String zip;
	String username;
	String password;
	String reg_date;
	String phone_number;
	String email;
	String account_type;
	int amount;
}