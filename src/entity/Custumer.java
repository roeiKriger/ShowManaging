package entity;

public class Custumer extends Person 
{
	private String mail;
	private String password;
	
	public Custumer(String fName, String lname, String phoneNum, String city, String mail, String password) {
		super(fName, lname, phoneNum, city);
		this.mail = mail;
		this.password = password;
	}
	
	//partinal constractor
	public Custumer(String mail, String password)
	{
		super();
		this.mail = mail;
		this.password = password;
	}
	
	public String getMail() {
		return mail;
	}

	public String getPassword() {
		return password;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Custumer other = (Custumer) obj;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
	
	
	
	
	

}
