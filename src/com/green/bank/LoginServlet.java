package com.green.bank;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.green.bank.database.DatabaseOperations;
import com.green.bank.database.UserRepository;
import com.green.bank.model.AccountModel;
import com.green.bank.util.DatabaseException;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 2178865317863049636L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username, password;
		DatabaseOperations service = new DatabaseOperations();
		
		username = request.getParameter("UserName");
		password = request.getParameter("password");

		System.out.println(username);
		System.out.println(password);

		try {
			if (!service.checkAccount(username, password)) {
				request.setAttribute("isPassOK", "No");
				System.out.println("Not able to login");
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
			} else {
				AccountModel am = service.checkUserExist(username, password).get();
				HttpSession session = request.getSession();
				session.setAttribute("userDetails", am);
				RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
				rd.forward(request, response);
				System.out.println("Able to log pass LoginServlet");
			}
		} catch (DatabaseException e) {
			System.out.println("Unable to access your login information " + e.getMessage());
		}
	}
}
