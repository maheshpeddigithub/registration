package com.myorg.registration.service;

import org.springframework.stereotype.Service;

@Service
public class ExclusionServiceImpl implements ExclusionService {
	public boolean validate(String dob, String ssn) {
		// dummy implementation - always return true for any request
		return true;
	}
}
