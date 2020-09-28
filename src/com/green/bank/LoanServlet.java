package com.green.bank;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.green.bank.database.DatabaseOperations;
import com.green.bank.model.LoanModel;
import com.green.bank.util.DatabaseException;

public class LoanServlet extends HttpServlet {


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int loanAmount;
		String accountNo;
		String fName;
		String lName;
		String address;
		String email;		
		
		accountNo = request.getParameter("account_no");
		String status = "pending";
		loanAmount = Integer.parseInt(request.getParameter("loan_amount"));
		fName = request.getParameter("first_name");
		lName = request.getParameter("last_name");
		address = request.getParameter("address");
		email = request.getParameter("email");
		
		LoanModel lModel = new LoanModel(accountNo, loanAmount, status, fName, lName, address, email );		

		try {
			DatabaseOperations operations = new DatabaseOperations();
			boolean check =  operations.insertLoanDetails(lModel);
			
			if(check){
				RequestDispatcher rd = request.getRequestDispatcher("loan_process.jsp");
				request.setAttribute("loan_details", lModel);
				rd.forward(request, response);
			}
			else{
				RequestDispatcher rd = request.getRequestDispatcher("loan_process.jsp");
				request.setAttribute("error", "yes");
				rd.forward(request, response);
			}
		} catch (DatabaseException e) {
			System.out.println("Unable to loan " + e.getMessage());
		}
	}

}
