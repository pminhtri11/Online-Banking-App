package com.green.bank;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.green.bank.database.DatabaseOperations;
import com.green.bank.database.JDBC_Connect;
import com.green.bank.model.AccountModel;
import com.green.bank.model.MailMessage;
import com.green.bank.util.DatabaseException;
import com.green.bank.util.SendMail;

public class TransferServlet extends HttpServlet {
	static ExecutorService service = Executors.newFixedThreadPool(10);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DatabaseOperations operation = new DatabaseOperations();
		String accountNo, username, targetAccountNo, password;
		boolean pass_wrong = false;
		int ownAmount = 0, transferAmount, recipientAmount = 0;
		ReadWriteLock userLock = new ReentrantReadWriteLock();
		ReadWriteLock adminLock = new ReentrantReadWriteLock();
		Lock userWriteLock = userLock.writeLock();
		Lock userReadLock = userLock.readLock();
		Lock adminWriteLock = adminLock.writeLock();
		Lock adminReadLock = adminLock.readLock();

		accountNo = request.getParameter("account_no");
		username = request.getParameter("username");
		targetAccountNo = request.getParameter("target_acc_no");
		password = request.getParameter("password");
		transferAmount = Integer.parseInt(request.getParameter("amount"));
		AccountModel ac = (AccountModel) request.getSession().getAttribute("userDetails");		
		try {
			Connection conn = JDBC_Connect.getConnection();
			AccountModel target = operation.getAccountDetails(targetAccountNo);
			// if the account doesnt exist or target account doesn't exist, return to the
			// page.			
			if (!operation.checkPassword(accountNo, password) && operation.checkAmountTable(targetAccountNo)) {
				String mailTo = ac.getEmail();
				String subject = "Transfer Fail";
				String text = "Unable to access your account or the Target Account doesn't exist";
				MailMessage mail = new MailMessage(mailTo, subject, text);
				service.execute(() -> {
					try {
						SendMail.sendMail(mail);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				});
				request.setAttribute("isPassOK", "No");
				RequestDispatcher rd = request.getRequestDispatcher("transfer.jsp");
				rd.forward(request, response);
			} else {
				// compare username, if diff and session username is not admin, forward
				// permission deny
				// to transfer.jsp
				String username1 = ac.getUsername();
				String username2 = target.getUsername();
				if (!username1.equals(username2) && !username1.equals("admin")) {
					String mailTo = ac.getEmail();
					request.setAttribute("error", "Permission deny: user can only tranfer between own accounts");
					String subject = "Transfer Fail";
					String text = "You can only Transfer between your own account";
					MailMessage mail = new MailMessage(mailTo, subject, text);
					service.execute(() -> {
						try {
							SendMail.sendMail(mail);
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					});
					RequestDispatcher rd = request.getRequestDispatcher("transfer.jsp");
					rd.forward(request, response);
				}

				System.out.println("I am in");
				// readwritelock for admin when normal users write and admin write and read.
				// readwritelock for normal user when normal users read and admin write.
				// Then normal users can always read until admin write.
				// admin read will always wait until no one write.
				try {
					if (username1.equals("admin")) {
						adminReadLock.lock();
						
					} else {
						userReadLock.lock();
					}

					ownAmount = operation.getCurrent(accountNo);
					if (ownAmount >= transferAmount) {
						ownAmount -= transferAmount;
						recipientAmount = operation.getCurrent(targetAccountNo);
						recipientAmount += transferAmount;
						String mailTo = ac.getEmail();
						String subject = "Transfer Success";
						String text = "Your new balance is " + ownAmount;
						MailMessage mail = new MailMessage(mailTo, subject, text);
						service.execute(() -> {
							try {
								SendMail.sendMail(mail);
							} catch (MessagingException e) {
								e.printStackTrace();
							}
						});
					} else {

						String mailTo = ac.getEmail();
						String subject = "Transfer Unsucessful";
						String text = "You don't have enough money";
						MailMessage mail = new MailMessage(mailTo, subject, text);
						service.execute(() -> {
							try {
								SendMail.sendMail(mail);
							} catch (MessagingException e) {
								e.printStackTrace();
							}
						});

						request.setAttribute("EnoughMoney", "No");
						RequestDispatcher rd = request.getRequestDispatcher("transfer.jsp");
						rd.forward(request, response);
					}
				} finally {
					if (username1.equals("admin")) {
						adminReadLock.unlock();
						
					} else {
						userReadLock.unlock();
					}
				}

				try {
					if (ownAmount >= transferAmount) {
						if (username1.equals("admin")) {
							adminWriteLock.lock();
						} else {
							userWriteLock.lock();
						}
						
						PreparedStatement ps = conn.prepareStatement("update amount set amount=? where id= ?");
						ps.setInt(1, ownAmount);
						ps.setString(2, accountNo);
						ps.executeUpdate();

						PreparedStatement ps1 = conn.prepareStatement("update amount set amount=? where id= ?");
						ps1.setInt(1, recipientAmount);
						ps1.setString(2, targetAccountNo);
						ps1.executeUpdate();
					}
				} catch (SQLException e) {
					throw new DatabaseException("Error accessing the Amount Database" + e.getMessage());
				} finally {
					if (username1.equals("admin")) {
						adminWriteLock.unlock();
					} else {
						userWriteLock.unlock();
					}
				}
				if (ownAmount >= transferAmount) {
					RequestDispatcher rd = request.getRequestDispatcher("transfer_process.jsp");
					rd.forward(request, response);
				}
			}

				// execute takes in a runnable object
				/*
				 * service.execute(() -> { try { SendMail.sendMail(new MailMessage()); } catch
				 * (MessagingException e) { e.printStackTrace(); } });
				 */

		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

}
