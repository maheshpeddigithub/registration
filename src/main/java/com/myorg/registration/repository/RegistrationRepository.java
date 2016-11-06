package com.myorg.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myorg.registration.model.User;

@Repository
public interface RegistrationRepository extends JpaRepository<User, String> {
}
