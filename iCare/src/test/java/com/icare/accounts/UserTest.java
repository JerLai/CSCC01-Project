package test.java.com.icare.accounts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import main.java.com.icare.accounts.Admin;
import main.java.com.icare.accounts.Receptionist;
import main.java.com.icare.accounts.ServiceProvider;
import main.java.com.icare.accounts.SettlementWorker;
import main.java.com.icare.accounts.User;
import main.java.com.icare.accounts.UserFactory;

public class UserTest {

	private static UserFactory usfac;
	private User admin;
	private User recep;
	private User settle;
	private User serv;

	@BeforeAll
	static void initUserFactory () {
		usfac = new UserFactory();
	}

	@Test
	@DisplayName("AdminUser")
	void testUserAdmin () {
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals(admin.getClass(), Admin.class);
	}

	@Test
	@DisplayName("ReceptionistUser")
	void testUserReceptionist () {
		recep = usfac.getUser("recep", "lol", "haha", 1, "receptionist");
		assertEquals(recep.getClass(), Receptionist.class);
	}

	@Test
	@DisplayName("SettlementWorkerUser")
	void testUserSettlementWorker () {
		settle = usfac.getUser("settle", "lol2", "haha2", 2, "settlementWorker");
		assertEquals(settle.getClass(), SettlementWorker.class);
	}

	@Test
	@DisplayName("ServiceProviderUser")
	void testUserServiceProvider () {
		serv = usfac.getUser("serv", "lol3", "haha3", 4, "serviceProvider");
		assertEquals(serv.getClass(), ServiceProvider.class);
	}

	@Test
	@DisplayName("ConstructorUsername")
	void testUserConstructorUsername () {
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals("admin", admin.username);
	}

	@Test
	@DisplayName("ConstructorFirstName")
	void testUserConstructorFirstName () {
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals("why", admin.firstName);
	}

	@Test
	@DisplayName("ConstructorLastName")
	void testUserConstructorLastName () {
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals("hi", admin.lastName);
	}

	@Test
	@DisplayName("ConstructorID")
	void testUserConstructorID () {
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals(3, admin.ID);
	}

	@Test
	@DisplayName("UsernameSetter")
	void testUserUsernameSetter () {
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		admin.setUsername("olleh");
		assertEquals("olleh", admin.username);
	}

	@Test
	@DisplayName("UsernameGetter")
	void testUserUsernameGetter () {
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals("admin", admin.getUsername());
	}

	@Test
	@DisplayName("FirstNameSetter")
	void testUserFirstNameSetter () {
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		admin.setFirstName("yhw");
		assertEquals("yhw", admin.firstName);
	}

	@Test
	@DisplayName("FirstNameGetter")
	void testUserFirstNameGetter () {
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals("why", admin.getFirstName());
	}

	@Test
	@DisplayName("LastNameSetter")
	void testUserLastNameSetter () {
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		admin.setLastName("ih");
		assertEquals("ih", admin.lastName);
	}

	@Test
	@DisplayName("LastNameGetter")
	void testUserLastNameGetter () {
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals("hi", admin.getLastName());
	}

	@Test
	@DisplayName("IDGetter")
	void testUserIDGetter () {
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals(3, admin.getID());
	}
}
