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
import entity.Person;
import entity.Seat;
import entity.Show;
import entity.ShowInTheater;
import entity.Theater;
import entity.TicketBuyers;
import util.Consts;

public class ShibutzControl 
{
	private static ShibutzControl instance;
	public static ShibutzControl getInstance() 
	{
		if (instance == null)
			instance = new ShibutzControl();
		return instance;
	}
	
	
	
	//This method returns all events for 'shibutz' (scheduling)
	public ArrayList<ShowInTheater> getAllEventsToShibutz()
	{
		Date today = java.sql.Date.valueOf(LocalDate.now());
		ArrayList<entity.ShowInTheater> allEvents = new ArrayList<ShowInTheater>();
		allEvents.addAll(orderControl.getInstance().getAllEvents());
		ArrayList<entity.ShowInTheater> toReturn = new ArrayList<ShowInTheater>();
		
		for(ShowInTheater shI: allEvents)
		{
			if(shI.getDate().after(today))
			{
				toReturn.add(shI);
			}
		}
		
		return toReturn;
	}
	
	//This method gets Event and returns all of the buyers for the 'shibutz'
	public ArrayList<TicketBuyers> getAllTicketBuyers(ShowInTheater shI)
	{
		return ImportControl.getAllTicketByIDS(shI);
	}
	
	public ArrayList<Seat> allAvalibleSeats(Theater th)
	{
		ArrayList<Seat> seatsList = new ArrayList<Seat>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_SEL_SEATS_BY_THEATER))
			{
				int k=1;
				callst.setInt(k++, th.getTheaterID());
				ResultSet rs = callst.executeQuery();
				int i;
				while (rs.next()) 
				{
					i=1;
					int row = rs.getInt(i++);
					int col = rs.getInt(i++);
					Boolean flag = rs.getBoolean(i++);
					Seat s = new Seat(row, col, th, flag);
					if(flag)	//the seat is avaliable
						seatsList.add(s);
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return seatsList;
		
	}
	
	
	//This method gets theater, row and seat and return true if can do the shibutz
	public boolean doShibutz(Theater th,int row, int seat ) throws ClassNotFoundException, SQLException
	{
		ArrayList<Seat> allAv = allAvalibleSeats(th);
		for(Seat s: allAv)
		{
			if(s.getRow()==row && s.getCol() == seat)
			{
				if(s.getIsAvalible())
				{					
					changeAvaliableSeat(th,row,seat); // method changing the avaliable 
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		return false;

	}
	
	//This method updates the available status
	private boolean changeAvaliableSeat(Theater th,int row, int seat ) throws ClassNotFoundException, SQLException
	{
		Boolean flag = false;
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_UPD_SEAT_AVALIABLE)){
			int i = 1;
			stmt.setBoolean(i++, flag);
			stmt.setInt(i++, row);
			stmt.setInt(i++, seat);
			stmt.setInt(i++, th.getTheaterID());
			stmt.executeUpdate();		
		}
		return true;

	}
	
	//This method returns all persons 
	public ArrayList<Person> getAllPersons()
	{
		ArrayList<Person> personList = new ArrayList<Person>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_person.*\r\n"+ "FROM tbl_person");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					personList.add(new Person(rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return personList;
	}

	//This method returns all Theaters 
	public ArrayList<Theater> getAllTheaters()
	{
		ArrayList<Theater> theaterList = new ArrayList<Theater>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_theater.*\r\n"+ "FROM tbl_theater");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					theaterList.add(new Theater(rs.getInt(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getInt(i++), rs.getInt(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return theaterList;
	}
	
	
	//This method add Entrance of a person to the system
	public boolean addEnterance(java.sql.Date tim,Person p, Theater t, int row, int seat, java.sql.Date testDate) throws SQLException, ClassNotFoundException
	{
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_ENTERNCE)){
			int i = 1;
			stmt.setDate(i++, tim);
			stmt.setDate(i++, testDate);
			stmt.setString(i++, p.getPhoneNum());
			stmt.setInt(i++, t.getTheaterID());
			stmt.setInt(i++, row);
			stmt.setInt(i++, seat);
			stmt.executeUpdate();		
		}
		return true;
		
	}
	
	public ArrayList<String> getAllCities(){
		ArrayList<String> theaterList = new ArrayList<String>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement("SELECT tbl_theater.city FROM tbl_theater GROUP BY tbl_theater.city;");
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					theaterList.add(rs.getString(i++));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return theaterList;
	}
	

}
