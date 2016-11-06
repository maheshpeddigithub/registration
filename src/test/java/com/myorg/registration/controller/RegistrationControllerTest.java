package com.myorg.registration.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myorg.registration.exception.ErrorMessage;
import com.myorg.registration.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper mapper;
	private User user;

	@Before
	public void setUp() {
		mapper = new ObjectMapper();
		user = new User();
		user.setUserName("jsmith123");
		user.setPassword("Password1");
		user.setDob("1986-11-11");
		user.setSsn("123-45-6789");
	}

	@Test
	public void testRegistrationSuccessful() throws Exception {
		// calling register service with valid user details
		mockMvc.perform(MockMvcRequestBuilders.post("/register").content(mapper.writeValueAsString(user))
			.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

		// checking above call successfully registered user by making GET call on register service
		mockMvc.perform(MockMvcRequestBuilders.get("/register").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("[" + mapper.writeValueAsString(user) + "]")));
	}

	@Test
	public void testRegistrationFailWhenUserNameNotAlphaNumeric() throws Exception {
		// invalid user name
		user.setUserName("jsm!th12$");

		// it will return status code 412 along with Invalid user name message
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		errorMessage.setMessage("Invalid user name");

		// asserting invalid user name error condition
		mockMvc
			.perform(MockMvcRequestBuilders.post("/register").content(mapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isPreconditionFailed())
			.andExpect(content().string(equalTo(mapper.writeValueAsString(errorMessage))));
	}

	@Test
	public void testRegistrationFailWhenPasswordNotInExpectedFormat() throws Exception {
		// invalid password
		user.setPassword("password1");

		// it will return status code 412 along with Invalid password
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		errorMessage.setMessage("Invalid password");

		// asserting invalid password error condition
		mockMvc
			.perform(MockMvcRequestBuilders.post("/register").content(mapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isPreconditionFailed())
			.andExpect(content().string(equalTo(mapper.writeValueAsString(errorMessage))));
	}

	@Test
	public void testRegistrationFailWhenDOBNotISO8601() throws Exception {
		// invalid DOB
		user.setDob("19856-11-11");

		// it will return status code 412 along with Invalid date of birth
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		errorMessage.setMessage("Invalid date of birth");

		// asserting invalid DOB error condition
		mockMvc
			.perform(MockMvcRequestBuilders.post("/register").content(mapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isPreconditionFailed())
			.andExpect(content().string(equalTo(mapper.writeValueAsString(errorMessage))));
	}

	@Test
	public void testRegistrationFailWhenSameUserAlreadyRegistered() throws Exception {

		// it will return status code 409 along with Invalid date of birth
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setErrorCode(HttpStatus.CONFLICT.value());
		errorMessage.setMessage("User already registered");

		// trying to re-register already registered user and asserting error condition
		mockMvc
			.perform(MockMvcRequestBuilders.post("/register").content(mapper.writeValueAsString(user))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isConflict())
			.andExpect(content().string(equalTo(mapper.writeValueAsString(errorMessage))));
	}

	@After
	public void tearDown() {
		user = null;
		mapper = null;
	}
}
