package edu.eci.cvds.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import edu.eci.cvds.servlet.repositories.UserRepository;
import edu.eci.cvds.servlet.services.UserService; 
import edu.eci.cvds.servlet.model.User;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void shoulCreateUser() {
        User user = new User("user pruebas", "pruebas@mail.com", "123456879","password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        User createdUser = userService.createUser(user);
        assertEquals(user, createdUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void shoulUpdateUser() {
        User user = new User("user pruebas", "pruebas@mail.com", "123456879","password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        User updatedUser = userService.updateUser(user);
        assertEquals(user, updatedUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void shoulDeleteUser() {
        User user = new User("user pruebas", "pruebas@mail.com", "123456879","password");
        userService.deleteUser(user);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void shoulFindUserByEmail() {
        User user = new User("user pruebas", "pruebas@mail.com", "123456879","password");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        User foundUser = userService.findUserByEmail(user.getEmail());
        assertEquals(user, foundUser);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
    }
}