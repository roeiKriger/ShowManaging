package entity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Calendar;

public class ShowInTheater 
{
	private Theater theater;
	private Show show;
	private Date date;
	private Time startHour;
	private int basicTicketPrice;
	private String status;
	private Date updateDate;
	private Date oldDate; //this save our old date we update
	
	public ShowInTheater(Theater theater, Show show, Date date, Time startHour, int basicTicketPrice, Date updateDate, String status) 
	{
		super();
		this.theater = theater;
		this.show = show;
		this.date = date;
		this.startHour = startHour;
		this.basicTicketPrice = basicTicketPrice;
		this.updateDate = updateDate;
		this.status = status;
	}
	
	
	public ShowInTheater(Theater theater, Show show, Date date, Time startHour, int basicTicketPrice, Date updateDate) 
	{
		super();
		this.theater = theater;
		this.show = show;
		this.date = date;
		this.startHour = startHour;
		this.basicTicketPrice = basicTicketPrice;
		this.updateDate = updateDate;
		this.status = "avaliable";
	}
	
	public ShowInTheater(Theater theater, Show show, Date date, Time startHour, int basicTicketPrice) 
	{
		super();
		this.theater = theater;
		this.show = show;
		this.date = date;
		this.startHour = startHour;
		this.basicTicketPrice = basicTicketPrice;
		this.updateDate = todayDate();
		this.status = "avaliable";
	}
	
	public ShowInTheater(Show show, Theater theater,int basicTicketPrice, Date date, Time startHour,String status,  Date updateDate) 
	{
		super();
		this.theater = theater;
		this.show = show;
		this.date = date;
		this.startHour = startHour;
		this.basicTicketPrice = basicTicketPrice;
		this.updateDate = updateDate;
		this.status = "avaliable";
	}
	
	
	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}

	


	public Date getOldDate() {
		return oldDate;
	}


	public void setOldDate(Date oldDate) {
		this.oldDate = oldDate;
	}


	public Theater getTheater() {
		return theater;
	}
	public void setTheater(Theater theater) {
		this.theater = theater;
	}
	public Show getShow() {
		return show;
	}
	public void setShow(Show show) {
		this.show = show;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getStartHour() {
		return startHour;
	}
	public void setStartHour(Time startHour) {
		this.startHour = startHour;
	}
	public int getBasicTicketPrice() {
		return basicTicketPrice;
	}
	public void setBasicTicketPrice(int basicTicketPrice) {
		this.basicTicketPrice = basicTicketPrice;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@Override
	public String toString() {
		return "Show \"" + getShow().getShowName() +"\", in Theater "+ getTheater().getTheaterName() +" in "+ getDate() + " at " + getStartHour();
	}
	
	public java.sql.Date todayDate()
	{
		LocalDate todayDate = LocalDate.now();
		Date date = Date.valueOf(todayDate);
		return date;
		
		
	}

	  public int compareTo(ShowInTheater o) {
	    return this.getDate().compareTo(o.getDate());
	  }
}
