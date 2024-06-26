package com.example.springbootmockito.service;

import com.example.springbootmockito.model.User;
import com.example.springbootmockito.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class UserService {

	private final UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	public User addUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null");
		}
		return repository.save(user);
	}

	public List<User> getUsers() {
		List<User> users = repository.findAll();
		log.info("Getting data from DB : {}", users);
		return users;
	}

	public List<User> getUserbyAddress(String address) {
		if (address == null) {
			throw new IllegalArgumentException("Address cannot be null");
		}
		log.info("Address: {}", address);
		return repository.findByAddress(address);
	}

	public void deleteUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null");
		}
		log.info("Deleting user: {}", user);
		repository.delete(user);
	}
}
