package com.example.WebApp.service;

import com.example.WebApp.model.Reservation;
import com.example.WebApp.model.Terrain;
import com.example.WebApp.repository.ReservationRepository;
import com.example.WebApp.repository.TerrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TerrainRepository terrainRepository;

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public List<Reservation> findByUtilisateurId(Long utilisateurId) {
        return reservationRepository.findByUtilisateurId(utilisateurId);
    }

    public List<Reservation> findByTerrainId(Long terrainId) {
        return reservationRepository.findByTerrainId(terrainId);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    // Vérifie la disponibilité du terrain avant de sauvegarder la réservation
    public Reservation createReservation(Long utilisateurId, Long terrainId, LocalDateTime dateHeureDebut, LocalDateTime dateHeureFin) {
        Terrain terrain = terrainRepository.findById(terrainId).orElseThrow(() -> new IllegalArgumentException("Terrain non trouvé"));
        List<Reservation> reservations = findByTerrainId(terrainId);

        for (Reservation reservation : reservations) {
            if (dateHeureDebut.isBefore(reservation.getDateHeureFin()) && dateHeureFin.isAfter(reservation.getDateHeureDebut())) {
                throw new IllegalArgumentException("Le terrain est déjà réservé pour cette période");
            }
        }

        Reservation reservation = new Reservation(utilisateurId, terrain, dateHeureDebut, dateHeureFin);
        return saveReservation(reservation);
    }
}
