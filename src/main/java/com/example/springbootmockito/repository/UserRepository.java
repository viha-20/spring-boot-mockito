package com.example.springbootmockito.repository;


import com.example.springbootmockito.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	List<User> findByAddress(String address);

}
