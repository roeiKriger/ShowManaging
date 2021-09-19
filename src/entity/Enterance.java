package entity;

import java.sql.Date;

public class Enterance 
{
	private int id;
	private java.sql.Date entranceDate;
	private java.sql.Date testDate;
	private Person p;
	private Theater t;
	private int row;
	private int seat;
	
	public Enterance(int id, Date entranceDate, Date testDate, Person p, Theater t, int row, int seat) {
		super();
		this.id = id;
		this.entranceDate = entranceDate;
		this.testDate = testDate;
		this.p = p;
		this.t = t;
		this.row = row;
		this.seat = seat;
	}

	public int getId() {
		return id;
	}

	public java.sql.Date getEntranceDate() {
		return entranceDate;
	}

	public java.sql.Date getTestDate() {
		return testDate;
	}

	public Person getP() {
		return p;
	}

	public Theater getT() {
		return t;
	}

	public int getRow() {
		return row;
	}

	public int getSeat() {
		return seat;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEntranceDate(java.sql.Date entranceDate) {
		this.entranceDate = entranceDate;
	}

	public void setTestDate(java.sql.Date testDate) {
		this.testDate = testDate;
	}

	public void setP(Person p) {
		this.p = p;
	}

	public void setT(Theater t) {
		this.t = t;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	@Override
	public String toString() {
		return "Enterance [id=" + id + ", entranceDate=" + entranceDate + ", testDate=" + testDate + ", p=" + p + ", t="
				+ t + ", row=" + row + ", seat=" + seat + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entranceDate == null) ? 0 : entranceDate.hashCode());
		result = prime * result + id;
		result = prime * result + ((p == null) ? 0 : p.hashCode());
		result = prime * result + row;
		result = prime * result + seat;
		result = prime * result + ((t == null) ? 0 : t.hashCode());
		result = prime * result + ((testDate == null) ? 0 : testDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enterance other = (Enterance) obj;
		if (entranceDate == null) {
			if (other.entranceDate != null)
				return false;
		} else if (!entranceDate.equals(other.entranceDate))
			return false;
		if (id != other.id)
			return false;
		if (p == null) {
			if (other.p != null)
				return false;
		} else if (!p.equals(other.p))
			return false;
		if (row != other.row)
			return false;
		if (seat != other.seat)
			return false;
		if (t == null) {
			if (other.t != null)
				return false;
		} else if (!t.equals(other.t))
			return false;
		if (testDate == null) {
			if (other.testDate != null)
				return false;
		} else if (!testDate.equals(other.testDate))
			return false;
		return true;
	}
	
	

}
