package main.java.com.icare.accounts;

public class UserFactory {

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
