package entity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Calendar;


public class Show 
{

	private int id;
	private String showName;
	private int showLeangth; //in minites
	private boolean hasBreak;


	//Constructor

	public Show(int id, String showName, int showLeangth, boolean hasBreak) {
		super();
		this.id = id;
		this.showName = showName;
		this.showLeangth = showLeangth;
		this.hasBreak = hasBreak;
	}




	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getShowName() {
		return showName;
	}


	public void setShowName(String showName) {
		this.showName = showName;
	}


	public int getShowLeangth() {
		return showLeangth;
	}


	public void setShowLeangth(int showLeangth) {
		this.showLeangth = showLeangth;
	}

	public boolean isHasBreak() {
		return hasBreak;
	}


	public void setHasBreak(boolean hasBreak) {
		this.hasBreak = hasBreak;
	}


	

	@Override
	public String toString() {
		return getShowName();
	}



}
