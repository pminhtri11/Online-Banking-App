package com.green.bank.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.green.bank.model.AccountModel;
import com.green.bank.model.DepositSchemeModel;
import com.green.bank.model.LoanModel;
import com.green.bank.util.DatabaseException;

public class DatabaseOperations{
	static Logger logger = Logger.getLogger(DatabaseOperations.class);

	public boolean insertLoanDetails(LoanModel model) throws Exception {
		int count1 = 0;
		try {
			Connection conn = JDBC_Connect.getConnection();
			PreparedStatement ps1 = conn
					.prepareStatement("insert into loan(id,amount,status,first_name,last_name,address,email) values('"
							+ model.getAccount_no() + "','" + model.getLoan_amount() + "','" + model.getStatus() + "','"
							+ model.getFirst_name() + "','" + model.getLast_name() + "','" + model.getAddress() + "','"
							+ model.getEmail() + "')");
			count1 = ps1.executeUpdate();

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return (count1 > 0);
	}
	
	public AccountModel getAccountDetails(String accountNo) throws DatabaseException {
		AccountModel am = new AccountModel();
		try {
			Connection conn = JDBC_Connect.getConnection();
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select * from account where id ='" + accountNo + "'");
			while (rs.next()) {

				// Setting all variables to model class
				am = new AccountModel();
				am.setAccount_no(rs.getString(1));
				am.setFirst_name(rs.getString(2));
				am.setLast_name(rs.getString(3));
				am.setAddress(rs.getString(4));
				am.setCity(rs.getString(5));
				am.setBranch(rs.getString(6));
				am.setZip(rs.getString(7));
				am.setUsername(rs.getString(8));
				am.setPassword(rs.getString(9));
				am.setPhone_number(rs.getString(10));
				am.setEmail(rs.getString(11));
				am.setAccount_type(rs.getString(12));
				am.setReg_date(rs.getString(13));

			}
			ResultSet rs1 = stmt.executeQuery("select * from amount where id ='" + am.getAccount_no() + "'");
			while (rs1.next()) {
				am.setAmount(rs1.getInt(2));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Error saving Account details into the db" + e.getMessage());
		}
		return am;
	}

	public boolean insertDepositScheme(DepositSchemeModel model) throws Exception {
		
		try {
			Connection conn = JDBC_Connect.getConnection();
			PreparedStatement ps1 = conn
					.prepareStatement("insert into deposit(id,year,interest,amount,deposit_date,value) values(?,?,?,?,?,?)");
			ps1.setString(1, model.getAccount_no());
			ps1.setLong(2, model.getYear());
			ps1.setLong(3, model.getInterest_rate());
			ps1.setLong(4, model.getAmount());
			ps1.setString(5, model.getDeposit_date());
			ps1.setString(6, model.getValue());
			int row = ps1.executeUpdate();
			return row ==1;
		} catch (SQLException e) {
			throw new DatabaseException("Error saving Account details into the db" + e.getMessage());
		}
	}

	public ArrayList<LoanModel> getLoanList(Connection conn) throws Exception {
		ArrayList<LoanModel> loanList = new ArrayList<>();
		LoanModel loanModel;

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from loan where status='pending'");
		while (rs.next()) {
			loanModel = new LoanModel();
			loanModel.setAccount_no(rs.getString(1));
			loanModel.setLoan_amount(rs.getInt(2));
			loanModel.setStatus(rs.getString(3));
			loanModel.setFirst_name(rs.getString(4));
			loanModel.setLast_name(rs.getString(5));
			loanModel.setAddress(rs.getString(6));
			loanModel.setEmail(rs.getString(7));
			loanList.add(loanModel);
		}
		return loanList;
	}
	
	public boolean UpdateDepositeSchemeAmount (String account_no, int main_amount) throws SQLException, DatabaseException {
		try {
			Connection conn = JDBC_Connect.getConnection();
			Statement s = conn.createStatement();
			s.executeQuery("update amount set amount ='"+ main_amount + "' where id ='" + account_no + "'");
		}
		catch (DatabaseException e) {
			throw new DatabaseException("Error updating the amount in the db! "+ e.getMessage());
		}
		return false;		
	}
	
	public void UpdateAmount(String account_no, int loan_amount) throws SQLException, DatabaseException {
		int current_amount = 0;
		Connection conn = JDBC_Connect.getConnection();

		Statement stmt = conn.createStatement();
		ResultSet rs1 = stmt.executeQuery("select * from amount where id ='" + account_no + "'");

		while (rs1.next()) {
			current_amount = rs1.getInt(2);
		}

		current_amount += loan_amount;

		// Updating Loan amount
		PreparedStatement ps = conn.prepareStatement("update amount set amount=? where id= ?");
		ps.setInt(1, current_amount);
		ps.setString(2, account_no);
		ps.executeUpdate();

		PreparedStatement ps1 = conn.prepareStatement("update loan set status=? where id= ?");
		ps1.setString(1, "success");
		ps1.setString(2, account_no);
		ps1.executeUpdate();

		conn.close();
	}
	
	public String newAccountNumber() throws DatabaseException {
		String id = "0";
		try {
			Connection conn = JDBC_Connect.getConnection();
			Statement s = conn.createStatement();
			ResultSet set = s.executeQuery("select account_id.nextval from dual");
			while(set.next()) {
				id = set.getString(1);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Error fetching user details from the db! "+ e.getMessage());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;		
	}

	public boolean addNewAccount(AccountModel user) throws DatabaseException {
		try {
			Connection conn = JDBC_Connect.getConnection();
			PreparedStatement p = conn.prepareStatement("select * from account where username = ? or email = ?" );
			
			p.setString(1, user.getUsername());
			p.setString(2, user.getEmail());
			ResultSet rs = p.executeQuery();
			
			if (rs.next()) {
				throw new DatabaseException("Account with this username or emails already exists! ");
			}
			rs.close();
			
		}catch (SQLException e) {
			throw new DatabaseException("Error fetching user details from the db! "+ e.getMessage());
		}
		
		// If No user Found
		try (Connection conn = JDBC_Connect.getConnection();
				PreparedStatement p = conn.prepareStatement(
						"insert into account (id, f_name, l_name, address, city, branch, zip, username, password, phone,email, account_type, reg_date) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");) {
			p.setString(1, user.getAccount_no());
			p.setString(2, user.getFirst_name());
			p.setString(3, user.getLast_name());
			p.setString(4, user.getAddress());
			p.setString(5, user.getCity());
			p.setString(6, user.getBranch());
			p.setString(7, user.getZip());
			p.setString(8, user.getUsername());
			p.setString(9, user.getPassword());
			p.setString(10, user.getPhone_number());
			p.setString(11, user.getEmail());
			p.setString(12, user.getAccount_type());
			p.setString(13, user.getReg_date());
			
			int row = p.executeUpdate();
			if (row !=1) {
				return false;
			}
			
		} catch (SQLException e) {
			throw new DatabaseException("Error saving Account details into the db" + e.getMessage());
		}
		return true;
	}


	public boolean addDeposite(DepositSchemeModel deposit) throws DatabaseException {
		// TODO Auto-generated method stub
		return false;
	}
}
