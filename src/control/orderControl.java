package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.Custumer;
import entity.Item;
import entity.Show;
import entity.ShowInTheater;
import entity.Theater;
import exceptions.ticketNumberException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.Consts;

public class orderControl 
{
	
	public static orderControl instance;
	public static orderControl getInstance() 
	{
		if (instance == null)
			instance = new orderControl();
		return instance;
	}
	
	//This var is to save the event which the custumer chose
	private ShowInTheater choose;
	
	public ShowInTheater getChoose() {
		return choose;
	}

	public void setChoose(ShowInTheater choose) {
		this.choose = choose;
	}


	/**
	 * Gets all the shows from the database
	 * @return ArrayList of Shows
	 */
	
	public ArrayList<Show> getShows() {
		ArrayList<Show> showList = new ArrayList<Show>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_show.*\r\n" + "FROM tbl_show");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					showList.add(new Show(rs.getInt(i++),rs.getString(i++),rs.getInt(i++),rs.getBoolean(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return showList;	
	}
	
	
	/**
	 * Gets all the Theater from the database
	 * @return ArrayList of theaters
	 */
	
	public ArrayList<Theater> getTheaters() {
		ArrayList<Theater> theaterList = new ArrayList<Theater>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_ALL_THEATERS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					theaterList.add(new Theater(rs.getInt(i++), rs.getString(i++), rs.getString(i++),rs.getString(i++), rs.getInt(i++), rs.getInt(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return theaterList;	
	}
	
	
	/**
	 * Gets all the events from the database
	 * @return ArrayList of events
	 */
	
	public ArrayList<ShowInTheater> getAllEvents() {
		ArrayList<ShowInTheater> eventsList = new ArrayList<ShowInTheater>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_ALL_SHOWINTHEATERS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					Show sh = new Show(rs.getInt(i++),rs.getString(i++),rs.getInt(i++),rs.getBoolean(i++));
					Theater th = new Theater(rs.getInt(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++),rs.getInt(i++), rs.getInt(i++));
					eventsList.add(new ShowInTheater(sh, th, rs.getInt(i++), rs.getDate(i++), rs.getTime(i++), rs.getString(i++), rs.getDate(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return eventsList;	
	}
	
	
	//This method gets showChoose and ticketNumver and do the invite
	public boolean orderShow(int ticketNumber, ShowInTheater showChoose) throws ClassNotFoundException, SQLException, ticketNumberException
	{
		Boolean isCanBe = ImportControl.canBeThere(showChoose, ticketNumber);
		if(!isCanBe)
		{
			throw new ticketNumberException();
		}
		
		return insertOrder(ticketNumber, showChoose);

	}
	
	//This method do the insertOrder to the DB
	private boolean insertOrder(int ticketNumber, ShowInTheater showChoose) throws ClassNotFoundException, SQLException
	{
		Date today = java.sql.Date.valueOf(LocalDate.now());
		Custumer loginCustumer= LoginControl.getInstance().getLoginCustumer();
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_NEW_ORDER)){
			int i = 1;	
			stmt.setDate(i++, today);
			stmt.setString(i++, loginCustumer.getPhoneNum());
			stmt.executeUpdate();	
		}
		return insertTicketsToOrder(ticketNumber,showChoose, getOrderId());
	}
	
	private boolean insertTicketsToOrder(int ticketNumber, ShowInTheater showChoose, int orderId) throws ClassNotFoundException, SQLException
	{		
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_NEW_Ticket)){
			int i = 1;	
			stmt.setInt(i++, showChoose.getTheater().getTheaterID());
			stmt.setInt(i++, showChoose.getShow().getId());
			stmt.setInt(i++, orderId);
			stmt.setInt(i++, ticketNumber);
			stmt.executeUpdate();	
		}
		return true;				
	}
	
	//This method returb the orderId Value
	private int getOrderId()
	{
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_ALL_ORDERIDS);
					ResultSet rs = stmt.executeQuery()) {
				int i=1;
				while (rs.next()) 
				{
					i++;
				}
				return i-2;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
		return -1;
			
	}
	
	//This method gets all the items for sell from DB
	public ArrayList<Item> getAllItems()
	{
		ArrayList<Item> allItems = new ArrayList<>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_ALL_ITEMS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) 
				{
					int i = 1;
					int id = rs.getInt(i++);
					String name = rs.getString(i++);
					int price = rs.getInt(i++);
					String pic = rs.getString(i++);
					allItems.add(new Item(id, name, price, pic));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
		
		return allItems;
	}

	//This method gets item and amount and insert the order to DB
	public boolean inviteTheItem(Item chooseItem , int amount) throws SQLException, ClassNotFoundException
	{
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_ITEM_ORDER)){
			int i = 1;	
			stmt.setInt(i++, amount);
			stmt.setInt(i++, getOrderId());
			stmt.setInt(i++, chooseItem.getItemId());
			stmt.executeUpdate();	
		}
		return true;					
	}
	
	
}
