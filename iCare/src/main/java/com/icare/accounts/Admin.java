package main.java.com.icare.accounts;

public class Admin extends User implements AccountCreation{

	public Admin(String username, String password, String firstName, String lastName, int iD) {
		super(username, password, firstName, lastName, iD);
	}

	public User createAccount (String username, String password, String firstName, String lastName, int iD, String accountType) {
		User user = null;

		// depending on requested accountType, creates appropriate User
		if (accountType == "receptionist") {
			user = createReceptionist(username, password, firstName, lastName, iD);
		} else if (accountType == "settlementWorker") {
			user = createSettlementWorker(username, password, firstName, lastName, iD);
		} else if (accountType == "admin") {
			user = createAdmin(username, password, firstName, lastName, iD);
		} else if (accountType == "serviceProvider") {
			user = createServiceProvider(username, password, firstName, lastName, iD);
		}

		return user;
	}

	public Receptionist createReceptionist (String username, String password, String firstName, String lastName, int iD) {
		Receptionist receptionist = new Receptionist(username, password, firstName, lastName, iD);
		return receptionist;
	}

	public SettlementWorker createSettlementWorker (String username, String password, String firstName, String lastName, int iD) {
		SettlementWorker settlementWorker = new SettlementWorker(username, password, firstName, lastName, iD);
		return settlementWorker;
	}

	public Admin createAdmin (String username, String password, String firstName, String lastName, int iD) {
		Admin admin = new Admin(username, password, firstName, lastName, iD);
		return admin;
	}

	public ServiceProvider createServiceProvider (String username, String password, String firstName, String lastName, int iD) {
		ServiceProvider serviceProvider = new ServiceProvider(username, password, firstName, lastName, iD);
		return serviceProvider;
	}

}
