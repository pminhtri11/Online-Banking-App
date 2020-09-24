package com.green.bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.green.bank.database.DatabaseOperations;
import com.green.bank.model.AccountModel;
import com.green.bank.util.DatabaseException;

public class CreateAccountServlet extends HttpServlet {

	private static final long serialVersionUID = -9117765301101502035L;
	static Logger logger = Logger.getLogger(CreateAccountServlet.class);


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = null;
		String first_name;
		String last_name;
		String address;
		String city;
		String branch;
		String zip;
		String username;
		String password;
		String rePassword;
		String phone_number;
		String email;
		String account_type;
		int amount;
		DatabaseOperations service = new DatabaseOperations();
		PrintWriter out = response.getWriter();
		first_name = request.getParameter("first_name");
		last_name = request.getParameter("last_name");
		address = request.getParameter("address");
		city = request.getParameter("city");
		branch = request.getParameter("branch");
		zip = request.getParameter("zip");
		username = request.getParameter("username");
		password = request.getParameter("password");
		rePassword = request.getParameter("re_password");
		phone_number = request.getParameter("phone");
		email = request.getParameter("email");
		account_type = request.getParameter("account_type");
		amount = Integer.parseInt(request.getParameter("amount"));
		
		// Getting Current date
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String reg_date = df.format(new Date());
		
		try {
			id = service.newAccountNumber();
		} catch (DatabaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (first_name == null || last_name == null) {
			logger.error("first_name and last_name cannot be null");
		}
		
		if (username == "" || password == "" || rePassword == "" || email== "" || address == "") {
			out.println("Request argument is missing");
			return;
		}
		// Setting all variables to model class
		
		if (password.equals(rePassword)) {
			AccountModel am = new AccountModel(id,first_name, last_name, address, city,
					 branch,  zip,  username,  password,  phone_number,  email,
					 account_type,  reg_date,  amount);
			request.setAttribute("Account_details", am);
			
			try {
				if (service.addNewAccount(am)) {
					RequestDispatcher rd = request.getRequestDispatcher("create_account_progress.jsp");
					rd.forward(request, response);
				}
				else {
					request.getRequestDispatcher("create_account.jsp").forward(request, response);
					out.println("Error");
				}
			} catch (DatabaseException | ServletException | IOException e) {
				out.println("Register fail. Please try again later, " + e.getMessage());
				request.getRequestDispatcher("create_account.jsp").forward(request, response);
				return;
			}
		} else {
			request.setAttribute("not_match", "yes");
			RequestDispatcher rd = request.getRequestDispatcher("create_account.jsp");
			rd.forward(request, response);
		}
	}

}
