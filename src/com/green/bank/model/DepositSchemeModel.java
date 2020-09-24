package com.green.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositSchemeModel {
	String account_no;
	String deposit_date;
	String value;
	int year;
	int interest_rate;
	int amount;
}
