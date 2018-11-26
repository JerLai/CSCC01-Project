package main.java.com.icare.accounts;

public class Receptionist extends User{

	/**
	 * Creates a new Receptionist with relevant information.
	 * @param username
	 * @param firstName
	 * @param lastName
	 * @param iD increments by 1 for each User (unique Identifier)
	 */
	public Receptionist(String username, String firstName, String lastName, int iD) {
		super(username, firstName, lastName, iD);
	}

	// To Do: Implement methods for creating new client entry

}
