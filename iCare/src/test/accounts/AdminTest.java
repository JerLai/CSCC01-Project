package test.accounts;

import static org.junit.jupiter.api.Assertions.assertEquals; 
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import icare.accounts.Admin;
import icare.accounts.Receptionist;
import icare.accounts.ServiceProvider;
import icare.accounts.SettlementWorker;

public class AdminTest {

	@Test
	@DisplayName("CreateReceptionist")
	void testCreateReceptionist () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		Receptionist recep = new Receptionist("hello", "bye", "why", "hi", 1);
		assertEquals(recep, admin.createReceptionist("hello", "bye", "why", "hi", 1));
	}

	@Test
	@DisplayName("CreateAdmin")
	void testCreateAdmin () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		Admin admin2 = new Admin("hello", "bye", "why", "hi", 1);
		assertEquals(admin2, admin.createAdmin("hello", "bye", "why", "hi", 1));
	}

	@Test
	@DisplayName("CreateSettlementWorker")
	void testCreateSettlementWorker () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		SettlementWorker settle = new SettlementWorker("hello", "bye", "why", "hi", 1);
		assertEquals(settle, admin.createSettlementWorker("hello", "bye", "why", "hi", 1));
	}

	@Test
	@DisplayName("CreateServiceProvider")
	void testCreateServiceProvider () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		ServiceProvider service = new ServiceProvider("hello", "bye", "why", "hi", 1);
		assertEquals(service, admin.createServiceProvider("hello", "bye", "why", "hi", 1));
	}

	@Test
	@DisplayName("CreateAccountReceptionist")
	void testCreateAccountReceptionist () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		Receptionist recep = new Receptionist("hello", "bye", "why", "hi", 1);
		assertEquals(recep, admin.createAccount("hello", "bye", "why", "hi", 1, "receptionist"));
	}

	@Test
	@DisplayName("CreateAccountAdmin")
	void testCreateAccountAdmin () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		Admin admin2 = new Admin("hello", "bye", "why", "hi", 1);
		assertEquals(admin2, admin.createAccount("hello", "bye", "why", "hi", 1, "admin"));
	}

	@Test
	@DisplayName("CreateAccountSettlementWorker")
	void testCreateAccountSettlementWorker () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		SettlementWorker settle = new SettlementWorker("hello", "bye", "why", "hi", 1);
		assertEquals(settle, admin.createAccount("hello", "bye", "why", "hi", 1, "settlementWorker"));
	}

	@Test
	@DisplayName("CreateAccountServiceProvider")
	void testCreateAccountServiceProvider () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		ServiceProvider service = new ServiceProvider("hello", "bye", "why", "hi", 1);
		assertEquals(service, admin.createAccount("hello", "bye", "why", "hi", 1, "serviceProvider"));
	}
}
