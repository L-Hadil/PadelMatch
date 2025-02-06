package com.example.WebApp.controller;

import com.example.WebApp.dto.UserDto;
import com.example.WebApp.enums.DisponibiliteGenerale;
import com.example.WebApp.enums.NotificationPreference;
import com.example.WebApp.model.User;
import com.example.WebApp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testRegisterUser() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("password");
        user.setEmail("user1@example.com");
        user.setPhoneNumber("1234567890");
        user.setDisponibiliteGenerale(DisponibiliteGenerale.JOURS_OUVRABLES);
        user.setNotificationPreference(NotificationPreference.EMAIL);
        when(userService.registerNewUser(any(UserDto.class))).thenReturn(user);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user1\",\"password\":\"password\",\"email\":\"user1@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully."));
    }


    @Test
    public void testLogin5User() throws Exception {
        // Creating the user object with all required fields
        User user = new User("user1", "password", "user1@example.com", "1234567890", DisponibiliteGenerale.JOURS_OUVRABLES, NotificationPreference.EMAIL);
        when(userService.findByUsername("user1")).thenReturn(user);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user1\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("user1")));
    }

    @Test
    public void testGetUserById() throws Exception {
        // Creating the user object with all required fields
        User user = new User("user1", "password", "user1@example.com", "1234567890", DisponibiliteGenerale.JOURS_OUVRABLES, NotificationPreference.EMAIL);
        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Creating the existing user object with all required fields
        User existingUser = new User("user1", "password", "user1@example.com", "1234567890", DisponibiliteGenerale.JOURS_OUVRABLES, NotificationPreference.EMAIL);
        // Creating the updated user object with all required fields
        User updatedUser = new User("updatedUser", "newPassword", "updatedUser@example.com", "9876543210", DisponibiliteGenerale.WEEKEND, NotificationPreference.SMS);
        when(userService.findById(1L)).thenReturn(existingUser);
        doAnswer(invocation -> {
            User user = invocation.getArgument(0);
            assertEquals("updatedUser", user.getUsername());
            assertEquals("newPassword", user.getPassword());
            assertEquals("updatedUser@example.com", user.getEmail());
            return null;
        }).when(userService).saveUser(any(User.class));

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"updatedUser\",\"password\":\"newPassword\",\"email\":\"updatedUser@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updatedUser"));
    }
}