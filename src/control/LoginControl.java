package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import entity.Admin;
import entity.Custumer;
import entity.Person;
import entity.Show;
import entity.ShowInTheater;
import entity.Theater;
import util.Consts;

public class LoginControl 
{
	public static LoginControl instance;
	public static LoginControl getInstance() 
	{
		if (instance == null)
			instance = new LoginControl();
		return instance;
	}
	
	private Custumer loginCustumer;
	private Person human;
	
	public Custumer getLoginCustumer() {
		return loginCustumer;
	}


	public void setLoginCustumer(Custumer loginCustumer) {
		this.loginCustumer = loginCustumer;
	}
	
	public Person getHuman() {
		return human;
	}


	public void setHuman(Person human) {
		this.human = human;
	}


	//This method get all custumers from DB
	public ArrayList<Custumer> getAllCustumers()
	{
		ArrayList<Custumer> myCutstumers = new ArrayList<>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_ALL_CUSTUMERS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					myCutstumers.add(new Custumer(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return myCutstumers;	
		
	}
	
	//This method return true if the mail is exist, else false
	public boolean checkMail(Custumer c)
	{
		ArrayList<Custumer> allCustumers = getAllCustumers();
		for(Custumer cus: allCustumers)
		{
			if(c.getMail()== cus.getMail())
			{
				return true;
			}
		}
		return false;
	}
	
	//this method gets Custumer and return true if sucssed to add to DB
	public boolean addNewCutsumerToDB(Custumer c) throws ClassNotFoundException, SQLException
	{
		addNewPersonToDB(c);
		//inserting to showInTheater
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_CUSTUMER)){
			int i = 1;
			stmt.setString(i++,c.getPhoneNum());
			stmt.setString(i++, c.getMail());
			stmt.setString(i++,c.getPassword());
			stmt.executeUpdate();
		}
		return true;	
	}
		

	//this method gets Custumer and return true if sucssed to add to DB
	public boolean addNewPersonToDB(Person p) throws ClassNotFoundException, SQLException
	{
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_PERSON)){
			int i = 1;
			stmt.setString(i++, p.getfName());
			stmt.setString(i++, p.getLname());
			stmt.setString(i++, p.getPhoneNum());
			stmt.setString(i++, p.getCity());
			stmt.executeUpdate();
		}
		return true;	
		
	}
	
	
	public ArrayList<Admin> getAllAdminsUser()
	{
		ArrayList<Admin> myAdmins = new ArrayList<>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_ALL_ADMIN);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					myAdmins.add(new Admin(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return myAdmins;	
		
	}
	

}
