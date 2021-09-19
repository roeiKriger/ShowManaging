package entity;

public class TicketBuyers
{
	private int orderId;
	private int amountOfTickets;
	private String phoneNum;
	private String mail;
	private String fname;
	private String lname;
	private ShowInTheater myShow;
	

	
	public TicketBuyers(int orderId, int amountOfTickets, String phoneNum, String mail, String fname,
			String lname, ShowInTheater myShow) {
		super();
		this.orderId = orderId;
		this.amountOfTickets = amountOfTickets+1; // because we need 1 space between each order
		this.phoneNum = phoneNum;
		this.mail = mail;
		this.fname = fname;
		this.lname = lname;
		this.myShow = myShow;
	}
	

	public ShowInTheater getMyShow() {
		return myShow;
	}


	public void setMyShow(ShowInTheater myShow) {
		this.myShow = myShow;
	}



	public int getOrderId() {
		return orderId;
	}

	public int getAmountOfTickets() {
		return amountOfTickets;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public String getMail() {
		return mail;
	}

	public String getFname() {
		return fname;
	}

	public String getLname() {
		return lname;
	}

	@Override
	public String toString() {
		return "Name: " + fname+" "+ lname + ", " +"Phone: " + phoneNum + ", Email: " + mail + ", Amount: " + this.getAmountOfTickets();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + orderId;
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
		TicketBuyers other = (TicketBuyers) obj;
		if (orderId != other.orderId)
			return false;
		return true;
	}		
	
	
	
	
	
}
