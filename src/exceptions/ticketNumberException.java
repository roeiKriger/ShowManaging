package exceptions;

public class ticketNumberException extends Exception
{
	public ticketNumberException() 
	{
		super("The number of tickets is inpossible in thie event");
	}
}
