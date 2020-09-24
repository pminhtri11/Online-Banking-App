package com.green.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositSchemeModel {
	String account_no, deposit_date,value;
	int year, interest_rate, amount;
}
