package com.green.bank;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.green.bank.database.DatabaseOperations;
import com.green.bank.database.JDBC_Connect;
import com.green.bank.model.AccountModel;
import com.green.bank.util.DatabaseException;

public class WithdrawServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accountNo, password;
		boolean pass_wrong = false;
		int currentAmount, withdrawAmount;
		accountNo = request.getParameter("account_no");
		password = request.getParameter("password");
		withdrawAmount = Integer.parseInt(request.getParameter("amount"));		
		DatabaseOperations service = new DatabaseOperations();		
		try {
			if (!service.checkPassword(accountNo, password)) {
				request.setAttribute("isPassOK", "No");
				RequestDispatcher rd = request.getRequestDispatcher("withdraw.jsp");
				rd.forward(request, response);
			} 
			else {
				System.out.println("I am in");
				currentAmount  = service.getCurrent(accountNo);

				if (currentAmount >= withdrawAmount) 
				{
					currentAmount -= withdrawAmount;
					if (service.updateAmount(accountNo, currentAmount)) {
						RequestDispatcher rd = request.getRequestDispatcher("Withdraw_process.jsp");
						rd.forward(request, response);
					}
				} else {
					request.setAttribute("EnoughMoney", "No");
					RequestDispatcher rd = request.getRequestDispatcher("withdraw.jsp");
					rd.forward(request, response);
				}
			}
		} catch (DatabaseException e) {
			System.out.println("Unable to complete your request " + e.getMessage());
		}
	}
}
