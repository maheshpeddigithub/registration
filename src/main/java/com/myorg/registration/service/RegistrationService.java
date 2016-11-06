package com.myorg.registration.service;

import java.util.List;

import com.myorg.registration.exception.UserAlreadyRegisteredException;
import com.myorg.registration.model.User;

public interface RegistrationService {
	List<User> listUsers();
	void saveUser(User user) throws UserAlreadyRegisteredException;
}
