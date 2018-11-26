package main.java.com.icare.accounts;

public class Admin extends User{

	/**
	 * Creates a new Admin with relevant information.
	 * @param username
	 * @param firstName
	 * @param lastName
	 * @param iD increments by 1 for each User (unique Identifier)
	 */
	public Admin(String username, String firstName, String lastName, int iD) {
		super(username, firstName, lastName, iD);
	}

}
