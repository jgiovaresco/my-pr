package fr.mypr.user.registration;

/**
 * The exception is thrown when the email given during the registration phase is already found from the database.
 */
public class DuplicateEmailException extends RuntimeException
{
	public DuplicateEmailException(String message)
	{
		super(message);
	}
}
