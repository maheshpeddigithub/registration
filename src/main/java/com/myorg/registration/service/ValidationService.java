package com.myorg.registration.service;

import com.myorg.registration.exception.ValidationException;
import com.myorg.registration.model.User;

public interface ValidationService {

	void validateUser(User user) throws ValidationException;

}
