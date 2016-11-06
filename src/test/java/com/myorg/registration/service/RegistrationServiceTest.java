package com.myorg.registration.service;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.myorg.registration.exception.UserAlreadyRegisteredException;
import com.myorg.registration.model.User;
import com.myorg.registration.repository.RegistrationRepository;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {

	@Mock
	RegistrationRepository repository;

	@InjectMocks
	private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

	private User user;

	@Before
	public void setUp() {
		user = new User();
		user.setUserName("jsmith123");
		user.setPassword("Password1");
		user.setDob("1985-11-11");
		user.setSsn("123-45-6789");
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = UserAlreadyRegisteredException.class)
	public void testUserRegistrationWhenSameUserAlreadyRegistered() throws UserAlreadyRegisteredException {
		List<User> users = new ArrayList<User>();
		users.add(user);

		// simulating user already registered condition
		when(repository.findAll()).thenReturn(users);

		// below is expected to throw UserAlreadyRegisteredException
		registrationService.saveUser(user);
		fail("this statement is not expected to be executed");
	}

	@Test
	public void testListUsers() throws UserAlreadyRegisteredException {
		List<User> users = new ArrayList<User>();
		users.add(user);
		when(repository.findAll()).thenReturn(users);
		List<User> savedUsers = registrationService.listUsers();
		assertEquals("list size not expected", 1, savedUsers.size());
	}

	@After
	public void tearDown() {
		user = null;
	}

}
