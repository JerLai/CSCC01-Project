package main.java.com.icare.accounts;

public class UserFactory {

	/**
	 * Creates a new User with relevant information.
	 * @param username
	 * @param firstName
	 * @param lastName
	 * @param iD increments by 1 for each User (unique Identifier)
	 * @param userType type of User to be created
	 */
	public User getUser(String username, String firstName, String lastName, int iD, String userType){
		if (userType.equals("receptionist")) {
			return new Receptionist(username, firstName, lastName, iD);
		} else if (userType.equals("settlementWorker")) {
			return new SettlementWorker(username, firstName, lastName, iD);
		} else if (userType.equals("admin")) {
			return new Admin(username, firstName, lastName, iD);
		} else if (userType.equals("serviceProvider")) {
			return new ServiceProvider(username, firstName, lastName, iD);
		}
		return null;
	   }
}
