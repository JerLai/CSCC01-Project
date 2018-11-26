package main.java.com.icare.accounts;

public class ServiceProvider extends User{

	/**
	 * Creates a new ServiceProvider with relevant information.
	 * @param username
	 * @param firstName
	 * @param lastName
	 * @param iD increments by 1 for each User (unique Identifier)
	 */
	public ServiceProvider(String username, String firstName, String lastName, int iD) {
		super(username, firstName, lastName, iD);
	}

	// To Do: Implement methods for viewing/choosing clients

}
