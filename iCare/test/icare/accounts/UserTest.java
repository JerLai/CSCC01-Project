package icare.accounts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

	@Test
	@DisplayName("ConstructorUsername")
	void testUserConstructorUsername () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		assertEquals("hello", admin.username);
	}

	@Test
	@DisplayName("ConstructorPassword")
	void testUserConstructorPassword () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		assertEquals("bye", admin.password);
	}

	@Test
	@DisplayName("ConstructorFirstName")
	void testUserConstructorFirstName () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		assertEquals("why", admin.firstName);
	}

	@Test
	@DisplayName("ConstructorLastName")
	void testUserConstructorLastName () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		assertEquals("hi", admin.lastName);
	}

	@Test
	@DisplayName("ConstructorID")
	void testUserConstructorID () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		assertEquals(3, admin.ID);
	}

	@Test
	@DisplayName("UsernameSetter")
	void testUserUsernameSetter () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		admin.setUsername("olleh");
		assertEquals("olleh", admin.username);
	}

	@Test
	@DisplayName("UsernameGetter")
	void testUserUsernameGetter () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		assertEquals("hello", admin.getUsername());
	}

	@Test
	@DisplayName("PasswordSetter")
	void testUserPasswordSetter () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		admin.setPassword("eyb");
		assertEquals("eyb", admin.password);
	}

	@Test
	@DisplayName("PasswordGetter")
	void testUserPasswordGetter () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		assertEquals("bye", admin.getPassword());
	}

	@Test
	@DisplayName("FirstNameSetter")
	void testUserFirstNameSetter () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		admin.setFirstName("yhw");
		assertEquals("yhw", admin.firstName);
	}

	@Test
	@DisplayName("FirstNameGetter")
	void testUserFirstNameGetter () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		assertEquals("why", admin.getFirstName());
	}

	@Test
	@DisplayName("LastNameSetter")
	void testUserLastNameSetter () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		admin.setLastName("ih");
		assertEquals("ih", admin.lastName);
	}

	@Test
	@DisplayName("LastNameGetter")
	void testUserLastNameGetter () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		assertEquals("hi", admin.getLastName());
	}

	@Test
	@DisplayName("IDGetter")
	void testUserIDGetter () {
		Admin admin = new Admin("hello", "bye", "why", "hi", 3);
		assertEquals(3, admin.getID());
	}
}
