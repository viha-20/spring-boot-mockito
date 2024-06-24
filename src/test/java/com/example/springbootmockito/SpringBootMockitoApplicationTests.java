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

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class SpringBootMockitoApplicationTests {

    @Autowired
    private UserService service;

    @MockBean   // Mocking Repository
    private UserRepository repository; /* Mock objects are simulated objects that mimic the behavior of real objects */


    @org.junit.Test
    public void getUsersTest() {
        when(repository.findAll()).thenReturn(Stream
                .of(new User(376, "Danile", 31, "USA"),
                        new User(958, "Huy", 35, "UK")).
                collect(Collectors.toList()));
        assertEquals(2, service.getUsers().size());
    }

    /*
    Stubbing is the process of setting up the mock to return specific values when certain methods are called.
    * */


    @org.junit.Test
    public void getUserbyAddressTest() {
        String address = "Bangalore";
        when(repository.findByAddress(address))
                .thenReturn(Stream.of(new User(376, "Danile", 31, "USA")).collect(Collectors.toList()));
        assertEquals(1, service.getUserbyAddress(address).size());
    }

    @org.junit.Test
    public void saveUserTest() {
        User user = new User(999, "Pranya", 33, "Pune");
        when(repository.save(user)).thenReturn(user);
        assertEquals(user, service.addUser(user));
    }

    @Test
    public void deleteUserTest() {
        User user = new User(999, "Pranya", 33, "Pune");
        service.deleteUser(user);
        verify(repository, times(1)).delete(user);
    }

    /*
    Verification is used to check if certain methods are called on the mock objects.
    * */

}
