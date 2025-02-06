package com.example.WebApp.controller;


import com.example.WebApp.dto.UserDto;
import com.example.WebApp.model.User;
import com.example.WebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Inscription d'un nouvel utilisateur
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        User user = userService.registerNewUser(userDto);
        if (user != null) {
            return ResponseEntity.ok("User registered successfully.");
        } else {
            return ResponseEntity.badRequest().body("Registration failed.");
        }
    }

    // Connexion de l'utilisateur

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginUser) {
        User user = userService.findByUsername(loginUser.getUsername());
        if (user != null && user.getPassword().equals(loginUser.getPassword())) {
            return ResponseEntity.ok(user);  // Retourner un objet utilisateur ou un message de succès en JSON
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    // Récupération d'un utilisateur par son ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Mise à jour des informations de l'utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User existingUser = userService.findById(id);
        if (existingUser != null) {
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPassword(userDetails.getPassword());
            userService.saveUser(existingUser);
            return ResponseEntity.ok(existingUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Route pour récupérer les préférences utilisateur
    @GetMapping("/{id}/preferences")
    public ResponseEntity<?> getUserPreferences(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            UserDto userPreferences = new UserDto();
            userPreferences.setDisponibiliteGenerale(user.getDisponibiliteGenerale());
            userPreferences.setNotificationPreference(user.getNotificationPreference());
            return ResponseEntity.ok(userPreferences);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
