package com.myorg.registration.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myorg.registration.exception.ErrorMessage;
import com.myorg.registration.exception.UserAlreadyRegisteredException;
import com.myorg.registration.exception.ValidationException;
import com.myorg.registration.model.User;
import com.myorg.registration.service.RegistrationService;
import com.myorg.registration.service.ValidationService;

@RestController
public class RegistratonController {

	private static final Logger logger = LoggerFactory.getLogger(RegistratonController.class);

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private ValidationService validationService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listUsers() {
		List<User> users = registrationService.listUsers();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Void> registerUser(@RequestBody User user)
			throws ValidationException, UserAlreadyRegisteredException {
		validationService.validateUser(user);
		registrationService.saveUser(user);
		logger.debug("User registered successfully");
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorMessage> validationExceptionHandler(Exception ex) {
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.PRECONDITION_FAILED);
	}

	@ExceptionHandler(UserAlreadyRegisteredException.class)
	public ResponseEntity<ErrorMessage> userAlreadyRegisteredExceptionHandler(Exception ex) {
		ErrorMessage error = new ErrorMessage();
		error.setErrorCode(HttpStatus.CONFLICT.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.CONFLICT);
	}

}