package com.green.bank;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.green.bank.database.DatabaseOperations;
import com.green.bank.database.JDBC_Connect;
import com.green.bank.model.AccountModel;
import com.green.bank.model.DepositSchemeModel;
import com.green.bank.util.DatabaseException;

public class DepositSchemeServlet extends HttpServlet {

	private static final long serialVersionUID = 7904967626139428741L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accountNo;
		String depositAmount;
		String value;
		int year;
		int interestRate;
		int amount = 0;
		boolean wrongPass = false;
		
		accountNo = request.getParameter("account_no");
		year = Integer.parseInt(request.getParameter("year"));
		interestRate = Integer.parseInt(request.getParameter("interest_rate"));
		depositAmount = request.getParameter("deposit_amount");
		value = request.getParameter("value");

		if (depositAmount.equals("1,00,000&#2547;")) {
			amount = 100000;
		} else if (depositAmount.equals("3,00,000&#2547;")) {
			amount = 300000;
		} else if (depositAmount.equals("5,00,000&#2547;")) {
			amount = 500000;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();			
		String depositDate = dateFormat.format(date);
		
		DepositSchemeModel dpModel = new DepositSchemeModel();

		try {
			DatabaseOperations operations = new DatabaseOperations();
			AccountModel am = operations.getAccountDetails(accountNo);

			if (am.getAmount() >= amount) {
				int main_amount  = am.getAmount() - amount;				
				operations.UpdateDepositeSchemeAmount(accountNo, main_amount);				
				boolean allRight = operations.insertDepositScheme(dpModel);
				request.setAttribute("DepositScheme", dpModel);
				request.setAttribute("allRight", allRight);				
				RequestDispatcher rd = request.getRequestDispatcher("deposit_scheme_progress.jsp");
				rd.forward(request, response);

			} else {
				request.setAttribute("Not_Enough", "Yes");
				RequestDispatcher rd = request.getRequestDispatcher("single_deposit_scheme.jsp?value=" + value);
				rd.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}



