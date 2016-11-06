package com.myorg.registration.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myorg.registration.exception.UserAlreadyRegisteredException;
import com.myorg.registration.model.User;
import com.myorg.registration.repository.RegistrationRepository;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);

	@Autowired
	private RegistrationRepository repository;

	public List<User> listUsers() {
		return repository.findAll();
	}

	public void saveUser(User user) throws UserAlreadyRegisteredException {
		if (isAlreadyRegistered(user.getUserName())) {
			logger.debug("User already registered");
			throw new UserAlreadyRegisteredException("User already registered");
		}
		repository.save(user);
	}

	private boolean isAlreadyRegistered(String username) {
		List<User> users = repository.findAll();
		for (User user : users) {
			if (user.getUserName().equals(username)) {
				return true;
			}
		}
		return false;
	}
}
