package com.example.WebApp.dto;

import com.example.WebApp.enums.DisponibiliteGenerale;
import com.example.WebApp.enums.NotificationPreference;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto {

    @NotBlank(message = "Phone number is required.")
    private String phoneNumber;

    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;

    private DisponibiliteGenerale disponibiliteGenerale;
    private NotificationPreference notificationPreference;

    // Constructors
    public UserDto() {}

    public UserDto(String username, String password, String email, String phoneNumber, DisponibiliteGenerale disponibiliteGenerale, NotificationPreference notificationPreference) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.disponibiliteGenerale = disponibiliteGenerale;
        this.notificationPreference = notificationPreference;
    }

    // Getters and setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DisponibiliteGenerale getDisponibiliteGenerale() {
        return disponibiliteGenerale;
    }

    public void setDisponibiliteGenerale(DisponibiliteGenerale disponibiliteGenerale) {
        this.disponibiliteGenerale = disponibiliteGenerale;
    }

    public NotificationPreference getNotificationPreference() {
        return notificationPreference;
    }

    public void setNotificationPreference(NotificationPreference notificationPreference) {
        this.notificationPreference = notificationPreference;
    }
}
