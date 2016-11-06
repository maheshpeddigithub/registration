package com.myorg.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RegistrationMain {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(RegistrationMain.class, args);
	}
}
