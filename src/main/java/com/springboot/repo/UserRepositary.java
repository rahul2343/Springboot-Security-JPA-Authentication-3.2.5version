package com.springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.entities.User;

import java.util.List;

public interface UserRepositary extends JpaRepository<User, String> {
	User findByUsername(String username);
}
