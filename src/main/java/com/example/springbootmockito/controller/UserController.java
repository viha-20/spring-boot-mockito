package com.example.springbootmockito.controller;

import java.util.List;

import com.example.springbootmockito.model.User;
import com.example.springbootmockito.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class UserController {
	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@PostMapping(value = "/save")
	public User saveUser(@RequestBody User user) {
		return service.addUser(user);
	}

	@GetMapping("/getUsers")
	public List<User> findAllUsers() {
		return service.getUsers();
	}

	@GetMapping("/getUserByAddress/{address}")
	public List<User> findUserByAddress(@PathVariable String address) {
		return service.getUserbyAddress(address);
	}

	@DeleteMapping(value="/remove")
	public User removeUser(@RequestBody User user) {
		service.deleteUser(user);
		return user;
	}
}
