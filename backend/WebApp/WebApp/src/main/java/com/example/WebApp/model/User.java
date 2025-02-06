package com.example.WebApp.model;

import com.example.WebApp.enums.DisponibiliteGenerale;
import com.example.WebApp.enums.NotificationPreference;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING) // Stores the enum as a String in the database
    @Column(nullable = false)
    private DisponibiliteGenerale disponibiliteGenerale;

    @Enumerated(EnumType.STRING) // Stores the enum as a String in the database
    @Column(nullable = false)
    private NotificationPreference notificationPreference;

    // Constructors
    public User() {
    }

    public User(String username, String password, String email, String phoneNumber, DisponibiliteGenerale disponibiliteGenerale, NotificationPreference notificationPreference) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.disponibiliteGenerale = disponibiliteGenerale;
        this.notificationPreference = notificationPreference;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
