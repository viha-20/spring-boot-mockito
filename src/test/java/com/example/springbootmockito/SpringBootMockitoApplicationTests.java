package com.example.springbootmockito;

import com.example.springbootmockito.model.User;
import com.example.springbootmockito.repository.UserRepository;
import com.example.springbootmockito.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringBootMockitoApplicationTests {

    @Autowired
    private UserService service;

    @MockBean // Mock objects are simulated objects that mimic the behavior of real objects in controlled ways.
    private UserRepository repository;

    // Test for adding a valid user
    @Test
    public void saveUserTest() {
        User user = new User(999, "Pranya", 33, "Pune");

        // Stubbing is the process of setting up the mock to return specific values when certain methods are called.
        when(repository.save(user)).thenReturn(user);

        assertEquals(user, service.addUser(user));
    }

    // Test for handling null user in addUser method
    @Test
    public void saveNullUserTest() {
        assertThrows(IllegalArgumentException.class, () -> service.addUser(null));
    }

    // Test for getting all users when users exist
    @Test
    public void getUsersTest() {
        when(repository.findAll()).thenReturn(Stream
                .of(new User(376, "Danile", 31, "USA"),
                        new User(958, "Huy", 35, "UK"))
                .collect(Collectors.toList()));
        assertEquals(2, service.getUsers().size());
    }

    // Test for getting all users when no users exist
    @Test
    public void getNoUsersTest() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        assertEquals(0, service.getUsers().size());
    }

    // Test for getting users by a valid address
    @Test
    public void getUserbyAddressTest() {
        String address = "Bangalore";
        when(repository.findByAddress(address))
                .thenReturn(Stream.of(new User(376, "Danile", 31, "Bangalore")).collect(Collectors.toList()));
        assertEquals(1, service.getUserbyAddress(address).size());
    }

    // Test for getting users by an address that does not exist
    @Test
    public void getUserbyNonexistentAddressTest() {
        when(repository.findByAddress("Unknown")).thenReturn(Collections.emptyList());
        assertEquals(0, service.getUserbyAddress("Unknown").size());
    }

    // Test for handling null address in getUserbyAddress method
    @Test
    public void getUserbyNullAddressTest() {
        assertThrows(IllegalArgumentException.class, () -> service.getUserbyAddress(null));
    }

    // Test for deleting an existing user
    @Test
    public void deleteUserTest() {
        User user = new User(999, "Pranya", 33, "Pune");
        service.deleteUser(user);

        // Verification is used to check if certain methods are called on the mock objects.
        verify(repository, times(1)).delete(user);
        /*It checks if the delete method was called exactly once
        on the mock repository with the user object.*/
    }

    // Test for handling null user in deleteUser method
    @Test
    public void deleteNullUserTest() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteUser(null));
    }

    // Test for handling database connection issues
    @Test
    public void exceptionHandlingTest() {
        // Database connection issue for getUsers
        when(repository.findAll()).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> service.getUsers());

        // Database connection issue for getUserbyAddress
        when(repository.findByAddress("Bangalore")).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> service.getUserbyAddress("Bangalore"));

        // Database connection issue for addUser
        User user = new User(999, "Pranya", 33, "Pune");
        when(repository.save(user)).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> service.addUser(user));

        // Database connection issue for deleteUser
        doThrow(new RuntimeException("Database error")).when(repository).delete(user);
        assertThrows(RuntimeException.class, () -> service.deleteUser(user));
    }
}
