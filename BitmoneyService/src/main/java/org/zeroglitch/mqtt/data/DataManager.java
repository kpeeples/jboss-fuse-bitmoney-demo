package org.zeroglitch.mqtt.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

public class DataManager {
	Logger lgr = Logger.getLogger(DataManager.class.getName());

	public DataManager() {

	}

	public User getUser(String username) {

		ApplicationContext context = new ClassPathXmlApplicationContext("datasource.xml");

		DataSource dataSource = (DataSource) context.getBean("myDataSource");

		User user = new User();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		// String url = "jdbc:mysql://localhost:3306/bitmoneydemo";
		// String user = "admin";
		// String password = "password";
		lgr.info("getting user");
		try {
			lgr.info("Create Data source");
			con = dataSource.getConnection();
			lgr.info("Create Statement");
			st = con.createStatement();
			lgr.info("select version");
			rs = st.executeQuery("select * from user where username='"+ username + "'");
			
			lgr.info("select * from user where username='"+ username + "'");

			lgr.info("display results");
			if (rs.next()) {

				user.setId(rs.getInt(1));
				user.setUserId(rs.getString(2));
				user.setDomain(rs.getString(3));
				user.setBalance(rs.getInt(4));

				lgr.info(rs.getString(1));
			}
			lgr.info("finished display");

		} catch (SQLException ex) {

			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
		lgr.info(user.toString());

		if (user.getId() == -1)
			return null;
		return user;

	}

	public void insertUser(String username, String region, int balance)
			throws SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"datasource.xml");

		DataSource dataSource = (DataSource) context.getBean("myDataSource");

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		lgr.info("getting user");

		lgr.info("Create Data source");
		con = dataSource.getConnection();
		lgr.info("Create Statement");
		st = con.createStatement();
		lgr.info("select version");
		String sql = "insert into user (username, region, balance) values ('"
				+ username + "', '" + region + "', '" + balance + "')";

		lgr.info("Excecuting: " + sql);
		st.executeUpdate(sql);

		lgr.info("finished update");

		if (st != null) {
			st.close();
		}
		if (con != null) {
			con.close();
		}

	}

	public ArrayList<Transaction> getTransactions(String username) throws SQLException {

		ApplicationContext context = new ClassPathXmlApplicationContext("datasource.xml");

		DataSource dataSource = (DataSource) context.getBean("myDataSource");

		Transaction element = new Transaction();
		ArrayList<Transaction> elementList = new ArrayList<Transaction>();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		lgr.info("Create Data source");
		con = dataSource.getConnection();
		lgr.info("Create Statement");
		st = con.createStatement();
		
		String sql = ""; 
		

		sql += " select t.userid, u.username, debit, credit, trandate ";
		sql += " from transaction t, user u ";
		sql += " where t.userid = u.id and username='" + username+ "'";
		lgr.info("executing:...  " + sql);
		rs = st.executeQuery(sql);

		lgr.info("display results");
		while (rs.next()) {
			element = new Transaction();
			element.setId(rs.getInt(1));
			element.setUserId(rs.getString(2));
			element.setDebit(rs.getInt(3));
			element.setCredit(rs.getInt(4));
			element.setTrandate(rs.getString(5));

			lgr.info(element.toString());
			
			elementList.add(element);	
		}
		
		lgr.info("finished display");

		if (rs != null) {
			rs.close();
		}
		if (st != null) {
			st.close();
		}
		if (con != null) {
			con.close();
		}

		return elementList;

	}

	public void insertTransaction(String userId, String credit, String debit) throws SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("datasource.xml");

		DataSource dataSource = (DataSource) context.getBean("myDataSource");

		User user = new User();

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		lgr.info("getting user");

		lgr.info("Create Data source");
		con = dataSource.getConnection();
		lgr.info("Create Statement");
		st = con.createStatement();
		lgr.info("select version");
		String sql = "insert into transaction (userid, credit, debit) values ('"
				+ userId + "', '" + credit + "', '" + debit + "')";

		lgr.info("Excecuting: " + sql);
		st.executeUpdate(sql);
		
		sql = "update user set balance=balance + " + credit + "-" + debit + " where id=" + userId;
		st.executeUpdate(sql);
		
		lgr.info("finished update");

		if (st != null) {
			st.close();
		}
		if (con != null) {
			con.close();
		}

	}
	
}

