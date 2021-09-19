package exceptions;

public class EmptyFieldException extends Exception{
	public EmptyFieldException() 
	{
		super("One or more fields is empty!");
	}
}
