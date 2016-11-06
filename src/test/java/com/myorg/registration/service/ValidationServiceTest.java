package com.myorg.registration.service;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.myorg.registration.exception.ValidationException;
import com.myorg.registration.model.User;

@RunWith(MockitoJUnitRunner.class)
public class ValidationServiceTest {

	@Mock
	private ExclusionService exclusionService;

	@InjectMocks
	private ValidationServiceImpl validationService = new ValidationServiceImpl();

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

	@Test(expected = ValidationException.class)
	public void testValidationserviceWhenUserNameIsNotAlphaNumeric() throws ValidationException {
		user.setUserName("jsmith$23");

		// below is expected to throw ValidationException since user name is not in the expected format
		validationService.validateUser(user);
		fail("this statement is not expected to be executed");
	}

	@Test(expected = ValidationException.class)
	public void testValidationserviceWhenPasswordIsNotInExpectedFormat() throws ValidationException {
		user.setPassword("password1");

		// below is expected to throw ValidationException since password is not in the expected format
		validationService.validateUser(user);
		fail("this statement is not expected to be executed");
	}

	// here more test scenarios can be added for password validation

	@Test(expected = ValidationException.class)
	public void testValidationserviceWhenDOBIsNotISO8601Format() throws ValidationException {
		user.setDob("19856-11-11");

		// below is expected to throw ValidationException since DOB is not in the expected format
		validationService.validateUser(user);
		fail("this statement is not expected to be executed");
	}

	@Test(expected = ValidationException.class)
	public void testValidationserviceWhenUserIsBlackListed() throws ValidationException {

		// simulating/mocking user blacklisted condition
		when(exclusionService.validate("1985-11-11", "123-45-6789")).thenReturn(false);

		// below is expected to throw ValidationException since user is blacklisted
		validationService.validateUser(user);
		fail("this statement is not expected to be executed");
	}

	@After
	public void tearDown() {
		user = null;
	}

}
