package com.green.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanModel {
	String accountNo;
	int loanAmount;
	String status;
	String fName;
	String lName;
	String address;
	String email;
}
