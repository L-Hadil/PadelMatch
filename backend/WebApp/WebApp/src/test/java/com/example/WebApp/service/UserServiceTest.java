package com.example.WebApp.service;

import com.example.WebApp.dto.UserDto;
import com.example.WebApp.enums.DisponibiliteGenerale;
import com.example.WebApp.enums.NotificationPreference;
import com.example.WebApp.model.User;
import com.example.WebApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("user1");
        user.setPassword("password");
        user.setEmail("user1@example.com");
        user.setPhoneNumber("1234567890");
        user.setDisponibiliteGenerale(DisponibiliteGenerale.JOURS_OUVRABLES);
        user.setNotificationPreference(NotificationPreference.EMAIL);
    }

    @Test
    public void testSaveUser() {
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);
        assertNotNull(savedUser);
        assertEquals(user.getId(), savedUser.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testFindById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1L);
        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User foundUser = userService.findById(1L);
        assertNull(foundUser);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByUsername() {
        when(userRepository.findByUsername("user1")).thenReturn(user);

        User foundUser = userService.findByUsername("user1");
        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername("user1");
    }

    @Test
    public void testCheckPassword() {
        boolean isPasswordValid = userService.checkPassword(user, "password");
        assertTrue(isPasswordValid);

        boolean isPasswordInvalid = userService.checkPassword(user, "wrongpassword");
        assertFalse(isPasswordInvalid);
    }

    @Test
    public void testRegisterNewUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("user2");
        userDto.setPassword("password");
        userDto.setEmail("user2@example.com");
        userDto.setPhoneNumber("0987654321");
        userDto.setDisponibiliteGenerale(DisponibiliteGenerale.WEEKEND);
        userDto.setNotificationPreference(NotificationPreference.SMS);

        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(userDto.getPassword());
        newUser.setEmail(userDto.getEmail());
        newUser.setPhoneNumber(userDto.getPhoneNumber());
        newUser.setDisponibiliteGenerale(userDto.getDisponibiliteGenerale());
        newUser.setNotificationPreference(userDto.getNotificationPreference());

        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User registeredUser = userService.registerNewUser(userDto);
        assertNotNull(registeredUser);
        assertEquals(userDto.getUsername(), registeredUser.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
