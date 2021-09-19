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
import java.util.HashMap;

import javax.swing.JFrame;

import entity.*;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import util.Consts;


public class ReportControl 
{
	public static ReportControl instance;
	
	public static ReportControl getInstance() {
		if (instance == null)
			instance = new ReportControl();
		return instance;
	}
	

	public JFrame produceReport(ShowInTheater events) 
	{
		Show s = events.getShow();
		Theater t = events.getTheater();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR))
			{
				HashMap<String, Object> params = new HashMap<>();

				params.put("tId", t.getTheaterID());
				params.put("sId", s.getId());
				JasperPrint print = JasperFillManager.fillReport(
						getClass().getResourceAsStream("/boundary/FoodReport.jasper"),
						params, conn);
				JFrame frame = new JFrame("Show Report for " + LocalDate.now());
				frame.getContentPane().add(new JRViewer(print));
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.pack();
				return frame;
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}

	
	//this method gets event and return hashMap of items name and amounts
	public HashMap<String, Integer> calcAmount(int theaterId, int showId)
	{
		HashMap<String, Integer> itemsInShow = new HashMap<String, Integer>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_AMOUNT_OF_FOOD)){
				stmt.setInt(1,theaterId);
				stmt.setInt(2,showId);
				ResultSet rs = stmt.executeQuery();{
					int i=1;
					while (rs.next()) 
					{
						itemsInShow.put(rs.getString(i++), rs.getInt(i++));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return itemsInShow;
		
		
	}
	
	//this method get event and item name and return the amount to give in show
	public int getAmount(int theaterId, int showId, String name)
	{
		HashMap<String, Integer> itemsInShow = calcAmount(theaterId, showId);
		return itemsInShow.get(name);
		
	}

	
}