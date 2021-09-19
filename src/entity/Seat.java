package entity;

public class Seat 
{
	private int row;
	private int col;
	private Theater hall;
	private Boolean isAvalible;
	//constractor//
	public Seat(int row, int col, Theater hall, Boolean isAvalible) {
		super();
		this.row = row;
		this.col = col;
		this.hall = hall;
		this.isAvalible = isAvalible;
	}
	
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public Theater getHall() {
		return hall;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public void setHall(Theater hall) {
		this.hall = hall;
	}

	public Boolean getIsAvalible() {
		return isAvalible;
	}

	public void setIsAvalible(Boolean isAvalible) {
		this.isAvalible = isAvalible;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + ((hall == null) ? 0 : hall.hashCode());
		result = prime * result + ((isAvalible == null) ? 0 : isAvalible.hashCode());
		result = prime * result + row;
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
		Seat other = (Seat) obj;
		if (col != other.col)
			return false;
		if (hall == null) {
			if (other.hall != null)
				return false;
		} else if (!hall.equals(other.hall))
			return false;
		if (isAvalible == null) {
			if (other.isAvalible != null)
				return false;
		} else if (!isAvalible.equals(other.isAvalible))
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "row=" + row + ", seat=" + col;
	}

	
	
	
	

}
