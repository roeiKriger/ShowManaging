package entity;

public class Person 
{
	private String fName;
	private String lname;
	private String phoneNum;
	private String city;
	
	
	
	public Person(String fName, String lname, String phoneNum, String city) {
		super();
		this.fName = fName;
		this.lname = lname;
		this.phoneNum = phoneNum;
		this.city = city;
	}
	
	public Person()
	{
		
	}
	
	public String getfName() {
		return fName;
	}
	public String getLname() {
		return lname;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public String getCity() {
		return city;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return fName +" " + lname + ", Phone: " +getPhoneNum();
	}
	
	
	
	
	

}
