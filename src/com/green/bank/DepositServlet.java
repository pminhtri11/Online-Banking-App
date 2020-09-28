package com.green.bank;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.green.bank.database.DatabaseOperations;
import com.green.bank.util.DatabaseException;

public class DepositServlet extends HttpServlet {

	private static final long serialVersionUID = 3160848446291840070L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String accountNo, password;
		int depositAmount;

		accountNo = request.getParameter("account_no");
		request.getParameter("username");
		password = request.getParameter("password");
		depositAmount = Integer.parseInt(request.getParameter("amount"));

		DatabaseOperations service = new DatabaseOperations();
		try {
			if (!service.checkPassword(accountNo, password)) {
				request.setAttribute("isPassOK", "No");
				RequestDispatcher rd = request.getRequestDispatcher("deposit.jsp");
				rd.forward(request, response);
			} else {
				
				System.out.println("I am in");			
				int currentAmount = service.getCurrent(accountNo);
				currentAmount += depositAmount;			
				
				if (service.updateAmount(accountNo, currentAmount)) {
					RequestDispatcher rd = request.getRequestDispatcher("Deposit_process.jsp");
					rd.forward(request, response);
				}				
				else {
					System.out.println("Unable to access your account");
					RequestDispatcher rd = request.getRequestDispatcher("deposit.jsp");
					rd.forward(request, response);
				}
			}
		} catch (DatabaseException e) {
			System.out.println("Unable to complete your deposit request " + e.getMessage() );
		} 
	}

}
