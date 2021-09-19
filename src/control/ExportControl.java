package control;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import entity.Enterance;
import entity.Person;
import entity.Show;
import entity.Theater;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.Consts;



public class ExportControl
{
	public static ExportControl instance;
	public static ExportControl getInstance() 
	{
		if (instance == null)
			instance = new ExportControl();
		return instance;
	}



	public ArrayList<Enterance> getAllEntrance()
	{
		ArrayList<Enterance> enterList = new ArrayList<Enterance>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQl_GET_ALL_ENTRACE);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					enterList.add(new Enterance(rs.getInt(i++),rs.getDate(i++), rs.getDate(i++), findPersonByPhone(rs.getString(i++)), findTheaterById(rs.getInt(i++)),rs.getInt(i++),rs.getInt(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return enterList;	
	}
	
	//this method gets Theater id and return the theater
	public Theater findTheaterById(int id)
	{
		ArrayList<Theater> halls = orderControl.getInstance().getTheaters();
		for(Theater t : halls)
		{
			if(t.getTheaterID() ==id)
			{
				return t;
			}
		}
		return null;
	}
	
	//this method gets phone number of Person and return the person
	public Person findPersonByPhone(String phone)
	{
		ArrayList<Person> people = ShibutzControl.getInstance().getAllPersons();
		for(Person p : people)
		{
			if(p.getPhoneNum().equals(phone))
			{
				return p;
			}
		}
		return null;
	}
	
	//This method get start Date and end Date and return arrayList of the entrance between this dates
	public ArrayList<Enterance> getEntranceByDates(java.sql.Date start, java.sql.Date end)
	{
		ArrayList<Enterance> allIn = getAllEntrance();
		ArrayList<Enterance> toReturn = new ArrayList<Enterance>();
		
		for(Enterance en: allIn)
		{
			java.sql.Date enterDate = en.getEntranceDate();
			if(enterDate.before(end) && enterDate.after(start))
			{
				toReturn.add(en);
			}
		}
		return toReturn;
		
		
	}
	
	
	public Boolean exportEntranceToXML(java.sql.Date start, java.sql.Date end) 
	{
		ArrayList<Enterance> in = getEntranceByDates(start, end);



		try {
			DocumentBuilderFactory dbFactory =
					DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			// root element
			Element rootElement = doc.createElement("Entrance");
			doc.appendChild(rootElement);
			for(int i=0;i<in.size();i++)
			{

				// transaction element
				Element enterance = doc.createElement("Entrance");
				rootElement.appendChild(enterance);

				// setting attribute to element
                // assign key to customer.
                Attr attr = doc.createAttribute("ID");
                attr.setValue(String.valueOf(i));
                enterance.setAttributeNode(attr);


				Element fName = doc.createElement("FirstName");
				fName.appendChild(doc.createTextNode(in.get(i).getP().getfName().toString()));
				enterance.appendChild(fName);

				Element Lname = doc.createElement("LastName");
				Lname.appendChild(doc.createTextNode(in.get(i).getP().getLname().toString()));
				enterance.appendChild(Lname);
				
				Element phone = doc.createElement("Phone");
				phone.appendChild(doc.createTextNode(in.get(i).getP().getPhoneNum().toString()));
				enterance.appendChild(phone);
				
				Element hall = doc.createElement("Hall");
				hall.appendChild(doc.createTextNode(in.get(i).getT().getTheaterName().toString()));
				enterance.appendChild(hall);
				
				
				Element row = doc.createElement("Row");
				row.appendChild(doc.createTextNode(String.valueOf(in.get(i).getRow())));
				enterance.appendChild(row);
				
				Element seat = doc.createElement("Seat");
				seat.appendChild(doc.createTextNode(String.valueOf(in.get(i).getSeat())));
				enterance.appendChild(seat);
				

				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				File file = new File("xml/customers.xml");
				StreamResult result = new StreamResult(file);
				transformer.transform(source, result);
				return true;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	
}
