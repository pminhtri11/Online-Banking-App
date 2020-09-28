package com.green.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositSchemeModel {
	String accountNo;
	String depositDate;
	String value;
	int year;
	int interestRate;
	int amount;
}
