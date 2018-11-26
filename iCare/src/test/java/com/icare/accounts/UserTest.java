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
		// create UserFactory
		usfac = new UserFactory();
	}

	@Test
	@DisplayName("AdminUser")
	void testUserAdmin () {
		// test creation of Admin User
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals(admin.getClass(), Admin.class);
	}

	@Test
	@DisplayName("ReceptionistUser")
	void testUserReceptionist () {
		// test creation of Receptionist User
		recep = usfac.getUser("recep", "lol", "haha", 1, "receptionist");
		assertEquals(recep.getClass(), Receptionist.class);
	}

	@Test
	@DisplayName("SettlementWorkerUser")
	void testUserSettlementWorker () {
		// test creation of SettlementWorker User
		settle = usfac.getUser("settle", "lol2", "haha2", 2, "settlementWorker");
		assertEquals(settle.getClass(), SettlementWorker.class);
	}

	@Test
	@DisplayName("ServiceProviderUser")
	void testUserServiceProvider () {
		// test creation of ServiceProvider User
		serv = usfac.getUser("serv", "lol3", "haha3", 4, "serviceProvider");
		assertEquals(serv.getClass(), ServiceProvider.class);
	}

	@Test
	@DisplayName("ConstructorUsername")
	void testUserConstructorUsername () {
		// test username initialization
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals("admin", admin.username);
	}

	@Test
	@DisplayName("ConstructorFirstName")
	void testUserConstructorFirstName () {
		// test first name initialization
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals("why", admin.firstName);
	}

	@Test
	@DisplayName("ConstructorLastName")
	void testUserConstructorLastName () {
		// test last name initialization
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals("hi", admin.lastName);
	}

	@Test
	@DisplayName("ConstructorID")
	void testUserConstructorID () {
		// test ID initialization
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals(3, admin.ID);
	}

	@Test
	@DisplayName("UsernameSetter")
	void testUserUsernameSetter () {
		// test username setter
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		admin.setUsername("olleh");
		assertEquals("olleh", admin.username);
	}

	@Test
	@DisplayName("UsernameGetter")
	void testUserUsernameGetter () {
		// test username getter
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals("admin", admin.getUsername());
	}

	@Test
	@DisplayName("FirstNameSetter")
	void testUserFirstNameSetter () {
		// test first name setter
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		admin.setFirstName("yhw");
		assertEquals("yhw", admin.firstName);
	}

	@Test
	@DisplayName("FirstNameGetter")
	void testUserFirstNameGetter () {
		// test first name getter
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals("why", admin.getFirstName());
	}

	@Test
	@DisplayName("LastNameSetter")
	void testUserLastNameSetter () {
		// test last name setter
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		admin.setLastName("ih");
		assertEquals("ih", admin.lastName);
	}

	@Test
	@DisplayName("LastNameGetter")
	void testUserLastNameGetter () {
		// test last name getter
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals("hi", admin.getLastName());
	}

	@Test
	@DisplayName("IDGetter")
	void testUserIDGetter () {
		// test ID getter
		admin = usfac.getUser("admin", "why", "hi", 3, "admin");
		assertEquals(3, admin.getID());
	}
}
