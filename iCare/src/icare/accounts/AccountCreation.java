package icare.accounts;

public interface AccountCreation {

	User createAccount (String username, String password, String firstName, String lastName, int iD, String accountType);

	Receptionist createReceptionist (String username, String password, String firstName, String lastName, int iD);

	SettlementWorker createSettlementWorker (String username, String password, String firstName, String lastName, int iD);

	Admin createAdmin (String username, String password, String firstName, String lastName, int iD);

	ServiceProvider createServiceProvider (String username, String password, String firstName, String lastName, int iD);
}
