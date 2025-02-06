package com.example.WebApp.repository;

import com.example.WebApp.model.Reservation;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUtilisateurId(Long utilisateurId);
    List<Reservation> findByTerrainId(Long terrainId);
}
