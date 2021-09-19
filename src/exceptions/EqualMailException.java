package exceptions;

public class EqualMailException extends Exception{
	public EqualMailException() 
	{
		super("We have a user with this fields");
	}
}