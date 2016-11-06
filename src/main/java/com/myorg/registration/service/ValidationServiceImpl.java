package com.myorg.registration.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myorg.registration.exception.ValidationException;
import com.myorg.registration.model.User;

@Service
public class ValidationServiceImpl implements ValidationService {

	private static final Logger logger = LoggerFactory.getLogger(ValidationServiceImpl.class);

	@Autowired
	private ExclusionService exclusionService;

	public void validateUser(User user) throws ValidationException {
		String username = user.getUserName();
		String password = user.getPassword();
		String dob = user.getDob();
		if ((username == null) || !StringUtils.isAlphanumeric(username)) {
			logger.debug("Invalid user name");
			throw new ValidationException("Invalid user name");
		}
		if ((password == null) || !password.matches("(?=.*[A-Z])(?=.*[0-9])(?=\\S+$).{4,}")) {
			logger.debug("Invalid password");
			throw new ValidationException("Invalid password");
		}
		if ((dob == null) || !dob.matches("(\\d{4})-(\\d{2})-(\\d{2})")) {
			logger.debug("Invalid date of birth");
			throw new ValidationException("Invalid date of birth");
		}
		if (!exclusionService.validate(dob, user.getSsn())) {
			logger.debug("User blacklisted");
			throw new ValidationException("User blacklisted");
		}
		logger.debug("User validation is successful");
	}
}
