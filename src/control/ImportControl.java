package control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import entity.Show;
import entity.ShowInTheater;
import entity.Theater;
import entity.TicketBuyers;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.Consts;

public class ImportControl 
{

	private final static String JsonPath = "json/shows.json";
	private static final String LOCAL_HOST = "127.0.0.1";
	private static final int CLIENT_PORT = 9100;
	public static ArrayList<TicketBuyers> custmersCantSeat = new ArrayList<TicketBuyers>();
	public static orderControl instance;
	public static orderControl getInstance() 
	{
		if (instance == null)
			instance = new orderControl();
		return instance;
	}
	
	
	public static  ArrayList<TicketBuyers> getCustmersCantSeat()
	{
		return custmersCantSeat;
	}
	
	//this method will import from json file to our java - help method
	private static ArrayList<ShowInTheater> importShowInTheaterFromJson()
	{
		ArrayList<ShowInTheater> ourJsonResult = new ArrayList<ShowInTheater>();

		try 
		{
			JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader("json/shows.json"));
			JSONArray jo = (JSONArray) obj.get("shows") ; 
			Iterator<JSONObject> iterator = jo.iterator();
			while(iterator.hasNext())
			{
				JSONObject item = iterator.next();
				
				//Theater
				String a = (String) item.get("THEATERID");	
				int theaterID = Integer.parseInt(a);;
				String theaterName = (String)item.get("THEATERNAME");
				String c = (String)item.get("MAXCAPACITY");	
				int maxCapacity = Integer.parseInt(c);; 			
				String manager = (String)item.get("MANAGERNAME");
				String city = (String)item.get("CITYNAME");
				String d = (String)item.get("maxInCapsule");
				int maxInCapsule = Integer.parseInt(d);
				
				
				//Show
				String e = (String)item.get("SHOWID");
				int showId = Integer.parseInt(e);;
				String showName = (String)item.get("SHOWNAME");
				String f = (String)item.get("SHOWLENGTH");
				int showLeangth = Integer.parseInt(f);
				String g = (String)item.get("HASBREAK");
				Boolean hasBreak = Boolean.parseBoolean(g);
				
				
				
				
				//ShowInTheater
				String m =  (String)item.get("DATEOFSHOW");
				java.sql.Date dateOfShow = saveTheDate(m);
				String ho = (String) item.get("STARTTIME"); 
				/*SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				long ms = sdf.parse(ho).getTime();
				Time startHour = new Time(ms);*/
				java.sql.Time startHour = saveTheTime(ho);
				//We in our System, we have only natural numbers as prices
				String h = (String)item.get("PRICE");
				double price = Double.parseDouble(h);	
				int basicTicketPrice = (int) price;
				String ud = (String) item.get("UPDATESTATUS");
				java.sql.Date updateDate =saveTheDate(ud);
				String status = (String)item.get("STATUS");
				
				
				Show s = new Show(showId, showName, showLeangth, hasBreak);
				Theater tr = new Theater(theaterID, theaterName, maxCapacity, manager, city, maxInCapsule);
				
				ShowInTheater shtr = new ShowInTheater(tr, s, dateOfShow, startHour, basicTicketPrice, updateDate, status);
				ourJsonResult.add(shtr);		
			}
			
			return ourJsonResult;
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println(e.getMessage());
		} 
		return ourJsonResult;		
	}
	
	public static java.sql.Date saveTheDate(String date)
	{
		String year="";
		String month="";
		String day="";
		
		char[] myStr = date.toCharArray();

		
		for(int i=0; i<4; i++)
		{
			year += myStr[i];
		}
		
		for(int i=5; i<7; i++)
		{
			month += myStr[i];
		}
		
		for(int i=8; i<10; i++)
		{
			day += myStr[i];
		}
		
		int yearDate = Integer.parseInt(year);
		int monthDate = Integer.parseInt(month);
		int dayDate = Integer.parseInt(day);
	//	System.out.println(year);
		//System.out.println(month);
	//	System.out.println(day);
		LocalDate date2 = LocalDate.of(yearDate, monthDate, dayDate);
	//	System.out.println("date 222 " + date2);
		java.sql.Date myDate =java.sql.Date.valueOf(date2);
	//	System.out.println(myDate);
		return myDate;
		
	}
	
	public static java.sql.Time saveTheTime(String date)
	{
		String hour="";
		String minute="";
		
		
		char[] myStr = date.toCharArray();

		
		for(int i=11; i<13; i++)
		{
			hour += myStr[i];
		}
		
		for(int i=14; i<16; i++)
		{
			minute += myStr[i];
		}
		
		
		int hourTime = Integer.parseInt(hour);
		int minuteTime = Integer.parseInt(minute);
		java.sql.Time myDate=new java.sql.Time(hourTime, minuteTime, 0);
		return myDate;
		
	}
	
	//This method will give us all people we need to call them because of updates
	public static ArrayList<TicketBuyers> getAllNeedToCall()
	{
		ArrayList<ShowInTheater> ourJsonResult = importShowInTheaterFromJson();
		ArrayList<ShowInTheater> toUpdate = new ArrayList<>();
		ArrayList<ShowInTheater> toInsert = new ArrayList<>();
		HashSet<HashMap<Integer,Integer>> showInTheaterIDs = getAllIDShowInTheater(); //the ids that exist
		int counterUpdate = 0;
		int counterInsert = 0;

		for(ShowInTheater value : ourJsonResult)
		{
			int idTheater = value.getTheater().getTheaterID();
			int idShow = value.getShow().getId();
			//System.out.println(value);
			
			HashMap<Integer, Integer> myKey = new HashMap<Integer, Integer>();
			myKey.put(idTheater, idShow);
			
			Boolean isExist = showInTheaterIDs.contains(myKey);

		try {	
			if(isExist) //update
			{
				toUpdate.add(value);
				value.setOldDate(saveMyOldDate(value.getTheater().getTheaterID(), value.getShow().getId())); //update the old date
				updateShowInTheater(value);
				++counterUpdate;
				//System.out.println("update "+ value);
				custmersCantSeat.addAll(isProblemWithUpdateSeats(value)); // this will save all the problematic customers 
				
			}
			else // insert
			{
				toInsert.add(value);
				insertNewShowInTheater(value);
				++counterInsert;
			}
		}
		catch(ClassNotFoundException| SQLException e)
		{
			//System.out.println(e.getMessage());
			e.printStackTrace();
		}

		}
		Alert alert = new Alert(AlertType.INFORMATION, "We updated "+ counterUpdate+"\nWe inserted " + counterInsert);
		alert.setHeaderText("Success");
		alert.setTitle("Success");
		alert.showAndWait();
		
		ArrayList <TicketBuyers> tb = new ArrayList<TicketBuyers>();
		for(ShowInTheater shIn: toUpdate)
		{
			tb.addAll(getAllTicketByIDS(shIn));
		}
		
		return tb;		
	}
	
	//this method will update the DB according to the received JSON File
	public static void importShowsFromJson()
	{		
			ArrayList<ShowInTheater> ourJsonResult = importShowInTheaterFromJson();
			ArrayList<ShowInTheater> toUpdate = new ArrayList<>();
			ArrayList<ShowInTheater> toInsert = new ArrayList<>();
			HashSet<HashMap<Integer,Integer>> showInTheaterIDs = getAllIDShowInTheater(); //the ids that exist
			int counterUpdate = 0;
			int counterInsert = 0;
			ArrayList <TicketBuyers> needToChange = new ArrayList<TicketBuyers>();
			for(ShowInTheater value : ourJsonResult)
			{
				int idTheater = value.getTheater().getTheaterID();
				int idShow = value.getShow().getId();
				
				HashMap<Integer, Integer> myKey = new HashMap<Integer, Integer>();
				myKey.put(idTheater, idShow);
				
				Boolean isExist = showInTheaterIDs.contains(myKey);

			try {	
				if(isExist) //update
				{
					toUpdate.add(value);
					updateShowInTheater(value);
					++counterUpdate;
					needToChange.addAll(isProblemWithUpdateSeats(value)); // this will save all the problematic
					
				}
				else // insert
				{
					toInsert.add(value);
					insertNewShowInTheater(value);
					++counterInsert;
				}
			}
			catch(ClassNotFoundException| SQLException e)
			{
				//System.out.println(e.getMessage());
				e.printStackTrace();
			}

			}
			
			
			Alert alert = new Alert(AlertType.INFORMATION, "We update "+ counterUpdate+"\nWe insert" + counterInsert);
			alert.setHeaderText("Success");
			alert.setTitle("Success");
			alert.showAndWait();
			
			ArrayList <TicketBuyers> tb = new ArrayList<TicketBuyers>();
			for(ShowInTheater shIn: toUpdate)
			{
				tb.addAll(getAllTicketByIDS(shIn));
			}
		
			for(TicketBuyers cus: tb)
			{
				//System.out.println("Hello " + cus.getFname() );
				//System.out.println("This is your recom ");
				ArrayList<ShowInTheater> shows = recommendUserNewDetails(cus);
				for(ShowInTheater sh : shows)
				{
					//System.out.println(sh.getShow() + "  In " + sh.getDate() );
				}
				
			}			
	}
		
	////////////////////////////////////Check Insert or Update//////////////////////////////
	
	//we get from DB all IDs of show that have show In theater (get Id show and Id Theater)
	//return hashSet with hashMap that key theater id and value show id
	public static HashSet<HashMap<Integer,Integer>> getAllIDShowInTheater()
	{
		HashSet<HashMap<Integer,Integer>> showInTheater = new HashSet<HashMap<Integer,Integer>>();
		ArrayList<Integer> theaterIDs = new ArrayList<Integer>();
		ArrayList<Integer> showIDs = new ArrayList<Integer>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_SHOW_IN_THEATER);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					theaterIDs.add(rs.getInt(i++));
					showIDs.add(rs.getInt(i++));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//putting in hashSet
		for(int i=0;i<showIDs.size();i++)
		{		
			HashMap<Integer, Integer> idsMap= new HashMap<>();
			idsMap.put(theaterIDs.get(i), showIDs.get(i));	
			showInTheater.add(idsMap);
		}
		return showInTheater;
	}
	
	
	////////////////////////////////////insert Values //////////////////////////////////////////
	
	//this method get ShowInTheater and insert him to DB (include show and theater)
	public static boolean insertNewShowInTheater(ShowInTheater b) throws ClassNotFoundException, SQLException
	{
		
		if(!isExistShow(b.getShow()))
		{
			insertShow(b.getShow());
		}
		
		if(!isExistTheater(b.getTheater()))
		{
			insertTheater(b.getTheater());
		}
		
		
		//inserting to showInTheater
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_SHOW_IN_THEATER)){
			int i = 1;
			stmt.setInt(i++, b.getTheater().getTheaterID());
			stmt.setInt(i++, b.getShow().getId());
			stmt.setInt(i++, b.getBasicTicketPrice());
			stmt.setDate(i++, b.getDate());
			stmt.setTime(i++, b.getStartHour());
			stmt.setString(i++, b.getStatus());
			stmt.setDate(i++, b.getUpdateDate());
			stmt.executeUpdate();
		}
		return true;	
	}
	
	//this method insert new Show to DB
	private static boolean insertShow(Show sh) throws ClassNotFoundException, SQLException
	{
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_SHOW)){
			int i = 1;	
			stmt.setInt(i++, sh.getId());
			stmt.setString(i++, sh.getShowName());
			stmt.setInt(i++, sh.getShowLeangth());
			stmt.setBoolean(i++, sh.isHasBreak());
			stmt.executeUpdate();
			
		}
		return true;	
	}
	
	//this method insert new Theater to DB
	private static boolean insertTheater(Theater th) throws ClassNotFoundException, SQLException
	{
		if(th == null)
		{
			return false;
		}
		
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_THEATER)){
			int i = 1;		
			stmt.setInt(i++, th.getTheaterID());
			stmt.setString(i++, th.getTheaterName());
			stmt.setString(i++, th.getManager());
			stmt.setString(i++, th.getCity());
			stmt.setInt(i++, th.getMaxCapacity());
			stmt.setInt(i++, th.getMaxInCapsule());
			stmt.executeUpdate();
			
		}
		return true;
		
	}

	//////////////////////////Update Values////////////////////////////
	
	//this method UPDATING new Show to DB
	private static boolean updateShow(Show sh) throws ClassNotFoundException, SQLException
	{
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_UPDA_SHOW)){
			int i = 1;
			stmt.setString(i++, sh.getShowName());
			stmt.setInt(i++, sh.getShowLeangth());
			stmt.setBoolean(i++, sh.isHasBreak());
			stmt.setInt(i++, sh.getId());
			stmt.executeUpdate();		
		}
		return true;	
	}
	
	//this method UPDATING new Theater to DB
	private static boolean updateTheater(Theater th) throws ClassNotFoundException, SQLException
	{
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_UPDA_THEATER)){
			int i = 1;
			stmt.setString(i++, th.getTheaterName());
			stmt.setString(i++, th.getManager());
			stmt.setString(i++, th.getCity());
			stmt.setInt(i++, th.getMaxCapacity());
			stmt.setInt(i++, th.getTheaterID());
			stmt.setInt(i++, th.getMaxInCapsule());
			stmt.executeUpdate();		
		}
		return true;
		
	}
	
	
	//We can update only dateOfShow, numberInCapsule, status
	public static boolean updateShowInTheater(ShowInTheater b) throws ClassNotFoundException, SQLException
	{
		if(!updateShow(b.getShow()) || !updateTheater(b.getTheater()))
		{
			return false;
		}	
		//inserting to showInTheater
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_UPDA_SHOW_IN_THEATER)){
			int i = 1;
			stmt.setInt(i++, b.getBasicTicketPrice());
			stmt.setDate(i++,b.getDate());
			stmt.setTime(i++, b.getStartHour());
			stmt.setString(i++, b.getStatus());
			stmt.setDate(i++, b.getUpdateDate());
			stmt.setInt(i++, b.getTheater().getTheaterID());
			stmt.setInt(i++, b.getShow().getId());
			stmt.executeUpdate();
		}
		return true;
	}
	
	//this method will save our old date of event
	private static java.sql.Date saveMyOldDate(int theaterId, int showId)
	{
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_GET_EVENT_DATE))
			{
				int k=1;
				callst.setInt(k++, theaterId);
				callst.setInt(k++, showId);

				ResultSet rs = callst.executeQuery();
				while (rs.next()) 
				{
					return rs.getDate(1);
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


		return null;
	}
	
	//this method get showId and theaterId and return all TicketBuyers
	public static ArrayList<TicketBuyers> getAllTicketByIDS(ShowInTheater sh)
	{
		int showId = sh.getShow().getId();
		int theaterId = sh.getTheater().getTheaterID();
		ArrayList<TicketBuyers> buyersList = new ArrayList<TicketBuyers>();
		int orderId;
		int amountOfTickets;
		String phoneNum;
		String mail;
		String fname;
		String lname;
		
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_SEL_SHOW_TICKETS_BY_SHOWID_THEATERID))
					{
					int k=1;
					callst.setInt(k++, theaterId);
					callst.setInt(k++, showId);
					
					
					ResultSet rs = callst.executeQuery();
					while (rs.next()) 
					{
						int i =1;
						orderId = (rs.getInt(i++));
						amountOfTickets = (rs.getInt(i++));
						phoneNum = (rs.getString(i++));
						mail = (rs.getString(i++));
						fname = (rs.getString(i++));
						lname = (rs.getString(i++));
						
						TicketBuyers tB = new TicketBuyers(orderId, amountOfTickets, phoneNum, mail, fname, lname, sh);
						//System.out.println(tB);
						buyersList.add(tB);
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return buyersList;	
	}
	
	//this method return arrayList of problem tickets (customer which doesnt have place in the theater)
	private static ArrayList<TicketBuyers> isProblemWithUpdateSeats(ShowInTheater sh)
	{
		
		int maxInCapsule = sh.getTheater().getMaxInCapsule(); // this is the max after all the spaces
		int maxCapaity = caculateNumOfCapsule(sh.getTheater())*maxInCapsule;	
		ArrayList<TicketBuyers> arr = getAllTicketByIDS(sh);
		ArrayList<TicketBuyers> toReturn = new ArrayList<TicketBuyers>(); 
		int sumOfTickets = 0;
	
		for(TicketBuyers tb : arr)
		{
			//System.out.println(tb);
			if(sumOfTickets + tb.getAmountOfTickets() <= maxCapaity && tb.getAmountOfTickets()<=maxInCapsule)
			{
				sumOfTickets += tb.getAmountOfTickets();
			}
			else 
			{
				toReturn.add(tb);
			}	
		}
		return toReturn;
	}
	
	
	//this method calculate the num of capsules
	private static int caculateNumOfCapsule(Theater tr)
	{
		int maxCapcity = tr.getMaxCapacity();
		int maxCapsula = tr.getMaxInCapsule();
		int numOfCapsule =0;
		int SPACE_BETWEEN_CAPSULE =1+1+maxCapsula*2;// because we want 2 between capsule and we enter 1 to all group
		
		while(maxCapcity>0)
		{
			maxCapcity = maxCapcity - maxCapsula - SPACE_BETWEEN_CAPSULE;
			numOfCapsule ++ ;	
		}
		return numOfCapsule;	
	}
		
	//This method will notify the user on the new details of the show
	public static ArrayList<ShowInTheater> recommendUserNewDetails(TicketBuyers buyer)
	{
		ShowInTheater shIn = buyer.getMyShow();
		String city = shIn.getTheater().getCity();
		LocalDate showDate;
		
		showDate = shIn.getOldDate().toLocalDate(); //because we want the oldDate
		//System.out.println(showDate);
		//System.out.println("this is the dates");
		java.sql.Date dtFrom = java.sql.Date.valueOf(showDate.minusWeeks(2)); //this date is the show date before 2 weeks
		//System.out.println(dtFrom);
		java.sql.Date dtTo =  java.sql.Date.valueOf(showDate.plusWeeks(2)); //this date is the show date after 2 weeks
		//System.out.println(dtTo);
		java.sql.Date dtNow = java.sql.Date.valueOf(LocalDate.now()); //this date is compere date becasue the 2 weeks between recomadation
			
		if(dtFrom.before(dtNow))// if we dont have 2 weeks alert
		{
			dtNow = java.sql.Date.valueOf(LocalDate.now().plusWeeks(2));
			dtFrom = dtNow;
			//dtFrom = buyer.getMyShow().getUpdateDate();
		}
		
		ArrayList<ShowInTheater> showList =  getAllRecomByParmWithoutSeats(city, dtFrom, dtTo);
		ArrayList<ShowInTheater> toReturn = new ArrayList<ShowInTheater>();
		
		for(ShowInTheater sh: showList)
		{
			if(canBeThere(sh, buyer.getAmountOfTickets()))
			{
				toReturn.add(sh);
			}
		}	
		return toReturn;	
	}
	
	//This method get city, and date from and after and return the all shows he can between the parm
	private static ArrayList<ShowInTheater> getAllRecomByParmWithoutSeats(String city, java.sql.Date dtPast, java.sql.Date dtFuture)
	{
		ArrayList<ShowInTheater> showList = new ArrayList<ShowInTheater>();
		
		int showId;
		String showName;
		int showLength;
		Boolean hasBreak;
		int theaterId;
		String theaterName;
		String managerName;
		String citySql;
		int maxCapacity;
		int maxInCapsule;
		int price;
		java.sql.Date dateOfShow; 
		java.sql.Time startHour;
		String status;
		java.sql.Date updateDate;
				
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_SEL_ALL_SHOWS_RECOM))
			{
				int k=1;
				//callst.setDate(k++, dtPast);
				callst.setDate(k++, dtFuture);
				callst.setDate(k++, dtPast);
				callst.setString(k++, city);
			
				ResultSet rs = callst.executeQuery();
				while (rs.next()) 
				{
					int i =1;
					showId = (rs.getInt(i++));
					showName = (rs.getString(i++));
					showLength = (rs.getInt(i++));
					hasBreak = (rs.getBoolean(i++));
					theaterId = (rs.getInt(i++));
					theaterName = (rs.getString(i++));
					managerName =  (rs.getString(i++));
					citySql = (rs.getString(i++));
					maxCapacity = (rs.getInt(i++));
					price = (rs.getInt(i++));
					maxInCapsule=(rs.getInt(i++));
					dateOfShow = (rs.getDate(i++));
					startHour = (rs.getTime(i++));
					status = (rs.getString(i++));
					updateDate = (rs.getDate(i++));
					
					Show sh = new Show(showId, showName, showLength, hasBreak);
					Theater th = new Theater(theaterId, theaterName, maxCapacity, managerName, citySql, maxInCapsule);
					ShowInTheater shIn = new ShowInTheater(th, sh, dateOfShow, startHour, price, updateDate, status);
					showList.add(shIn); 
				}

				return showList;

			} catch (SQLException e) {

				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return showList;
	}

	//this method will get ShowInTheater and amount of tickets and return if someone can sit there
	public static boolean canBeThere(ShowInTheater sh, int amount)
	{
		int maxInCapsule = sh.getTheater().getMaxInCapsule(); // this is the max after all the spaces
		int maxCapaity = caculateNumOfCapsule(sh.getTheater())*maxInCapsule;
		int sumOfTickets = sumOfTicket(sh);
		int optional = sumOfTickets + amount;
		if(optional <= maxCapaity && amount<=maxInCapsule)
		{
			return true;
		}
		return false;
	}
	
	//this method get ShowInTheater and return the tickets that buyers
	private static int sumOfTicket(ShowInTheater sh)
	{
		int maxInCapsule = sh.getTheater().getMaxInCapsule(); // this is the max after all the spaces
		int maxCapaity = caculateNumOfCapsule(sh.getTheater())*maxInCapsule;

		ArrayList<TicketBuyers> arr = getAllTicketByIDS(sh);
		
		int sumOfTickets = 0;
	
		for(TicketBuyers tb : arr)
		{
			if(sumOfTickets + tb.getAmountOfTickets() <= maxCapaity && tb.getAmountOfTickets()<=maxInCapsule)
			{
				sumOfTickets += tb.getAmountOfTickets();
			}
				
		}
		return sumOfTickets;
	}
	
	
	////// existing show & Theater //////
	//this method get show and return if exist
	private static boolean isExistShow(Show myShow)
	{
		int id = myShow.getId();
		int length =0;
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_SHOW_EXIST))
					{
					int k=1;
					callst.setInt(k++, id);
					
					ResultSet rs = callst.executeQuery();
					while (rs.next()) 
						length = rs.getInt(1);
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if(length == 0)
			return false;
		return true;
		
	}
	//this method get Theater and return if exist
	private static boolean isExistTheater(Theater myTheater)
	{
		int id = myTheater.getTheaterID();
		int maxCapacity = 0;
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_THEATER_EXIST))
					{
					int k=1;
					callst.setInt(k++, id);
					
					ResultSet rs = callst.executeQuery();
					while (rs.next()) 
						maxCapacity = rs.getInt(1);
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if(maxCapacity == 0)
			return false;
		return true;
	}
	
}
