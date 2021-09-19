package entity;



public class Theater 
{
	//Fields//
	private int theaterID;
	private String theaterName;
	private int maxCapacity;
	private String manager;
	private String city;
	private int maxInCapsule;
	
	//Constructor///
	public Theater(int theaterID, String theaterName, int maxCapacity, String manager, String city, int maxInCapsule) {
		super();
		this.theaterID = theaterID;
		this.theaterName = theaterName;
		this.maxCapacity = maxCapacity;
		this.manager = manager;
		this.city = city;
		this.maxInCapsule = maxInCapsule; //this is from color from HW1, its from the JSON file
	}
	
	public Theater(int theaterID, String theaterName, int maxCapacity, String manager, String city) {
		super();
		this.theaterID = theaterID;
		this.theaterName = theaterName;
		this.maxCapacity = maxCapacity;
		this.manager = manager;
		this.city = city;
		this.maxInCapsule = 5; //this is minimum capsule that can be, its can update by JSON
	}
	
	public Theater(int theaterID, String theaterName, String manager, String city, int maxCapacity, int maxInCapsule) {
		super();
		this.theaterID = theaterID;
		this.theaterName = theaterName;
		this.maxCapacity = maxCapacity;
		this.manager = manager;
		this.city = city;
		this.maxInCapsule = maxInCapsule; //this is from color from HW1, its from the JSON file
	}
	
	//Getters and Setters///
	public int getTheaterID() {
		return theaterID;
	}
	public void setTheaterID(int theaterID) {
		this.theaterID = theaterID;
	}
	public String getTheaterName() {
		return theaterName;
	}
	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public int getMaxInCapsule() {
		return maxInCapsule;
	}

	public void setMaxInCapsule(int maxInCapsule) {
		this.maxInCapsule = maxInCapsule;
	}


	//toString Method//
	@Override
	public String toString() {
		return getTheaterName();
	}
	
	
	

	
}
