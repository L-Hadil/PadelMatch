package com.example.WebApp.service;

import com.example.WebApp.dto.UserDto;
import com.example.WebApp.model.User;
import com.example.WebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        // Assuming password encryption and other security measures are handled elsewhere
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean checkPassword(User user, String rawPassword) {
        // Password comparison (should be replaced with encrypted comparison in production)
        return rawPassword.equals(user.getPassword());
    }

    public User registerNewUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword()); // Consider encrypting this in production
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setDisponibiliteGenerale(userDto.getDisponibiliteGenerale());
        user.setNotificationPreference(userDto.getNotificationPreference());
        return userRepository.save(user);
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
