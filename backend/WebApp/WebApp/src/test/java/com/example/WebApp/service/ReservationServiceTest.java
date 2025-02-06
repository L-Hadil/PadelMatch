package com.example.WebApp.service;

import com.example.WebApp.model.Reservation;
import com.example.WebApp.model.Terrain;
import com.example.WebApp.enums.StatutTerrain;
import com.example.WebApp.enums.TypeSurface;
import com.example.WebApp.repository.ReservationRepository;
import com.example.WebApp.repository.TerrainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private TerrainRepository terrainRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Terrain terrain;
    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        terrain = new Terrain("Localisation1", StatutTerrain.DISPONIBLE, TypeSurface.HERBE);
        terrain.setId(1L);
        reservation = new Reservation(1L, terrain, LocalDateTime.now(), LocalDateTime.now().plusHours(2));
    }

    @Test
    public void testSaveReservation() {
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        Reservation savedReservation = reservationService.saveReservation(reservation);
        assertNotNull(savedReservation);
        assertEquals(reservation.getId(), savedReservation.getId());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    public void testFindById() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        Reservation foundReservation = reservationService.findById(1L);
        assertNotNull(foundReservation);
        assertEquals(reservation.getId(), foundReservation.getId());
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdNotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());
        Reservation foundReservation = reservationService.findById(1L);
        assertNull(foundReservation);
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByUtilisateurId() {
        List<Reservation> reservations = Arrays.asList(reservation);
        when(reservationRepository.findByUtilisateurId(1L)).thenReturn(reservations);
        List<Reservation> foundReservations = reservationService.findByUtilisateurId(1L);
        assertNotNull(foundReservations);
        assertFalse(foundReservations.isEmpty());
        assertEquals(1, foundReservations.size());
        verify(reservationRepository, times(1)).findByUtilisateurId(1L);
    }

    @Test
    public void testFindByTerrainId() {
        List<Reservation> reservations = Arrays.asList(reservation);
        when(reservationRepository.findByTerrainId(1L)).thenReturn(reservations);
        List<Reservation> foundReservations = reservationService.findByTerrainId(1L);
        assertNotNull(foundReservations);
        assertFalse(foundReservations.isEmpty());
        assertEquals(1, foundReservations.size());
        verify(reservationRepository, times(1)).findByTerrainId(1L);
    }

    @Test
    public void testFindAll() {
        List<Reservation> reservations = Arrays.asList(reservation);
        when(reservationRepository.findAll()).thenReturn(reservations);
        List<Reservation> foundReservations = reservationService.findAll();
        assertNotNull(foundReservations);
        assertFalse(foundReservations.isEmpty());
        assertEquals(1, foundReservations.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteReservation() {
        doNothing().when(reservationRepository).deleteById(1L);
        reservationService.deleteReservation(1L);
        verify(reservationRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testCreateReservation() {
        when(terrainRepository.findById(1L)).thenReturn(Optional.of(terrain));
        when(reservationRepository.findByTerrainId(1L)).thenReturn(Arrays.asList());
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        LocalReservationRequest request = new LocalReservationRequest();
        request.setUtilisateurId(1L);
        request.setTerrainId(1L);
        request.setDateHeureDebut(LocalDateTime.now());
        request.setDateHeureFin(LocalDateTime.now().plusHours(2));

        Reservation createdReservation = reservationService.createReservation(1L, 1L, request.getDateHeureDebut(), request.getDateHeureFin());

        assertNotNull(createdReservation);
        verify(terrainRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).findByTerrainId(1L);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    public void testCreateReservationTerrainNotFound() {
        when(terrainRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            reservationService.createReservation(1L, 1L, LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        });

        verify(terrainRepository, times(1)).findById(1L);
        verify(reservationRepository, never()).findByTerrainId(anyLong());
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    public void testCreateReservationOverlap() {
        when(terrainRepository.findById(1L)).thenReturn(Optional.of(terrain));
        when(reservationRepository.findByTerrainId(1L)).thenReturn(Arrays.asList(reservation));

        LocalDateTime start = reservation.getDateHeureDebut().minusMinutes(30);
        LocalDateTime end = reservation.getDateHeureFin().plusMinutes(30);

        assertThrows(IllegalArgumentException.class, () -> {
            reservationService.createReservation(1L, 1L, start, end);
        });

        verify(terrainRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).findByTerrainId(1L);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    // Local equivalent of ReservationRequest for testing purposes
    private static class LocalReservationRequest {
        private Long utilisateurId;
        private Long terrainId;
        private LocalDateTime dateHeureDebut;
        private LocalDateTime dateHeureFin;

        public Long getUtilisateurId() {
            return utilisateurId;
        }

        public void setUtilisateurId(Long utilisateurId) {
            this.utilisateurId = utilisateurId;
        }

        public Long getTerrainId() {
            return terrainId;
        }

        public void setTerrainId(Long terrainId) {
            this.terrainId = terrainId;
        }

        public LocalDateTime getDateHeureDebut() {
            return dateHeureDebut;
        }

        public void setDateHeureDebut(LocalDateTime dateHeureDebut) {
            this.dateHeureDebut = dateHeureDebut;
        }

        public LocalDateTime getDateHeureFin() {
            return dateHeureFin;
        }

        public void setDateHeureFin(LocalDateTime dateHeureFin) {
            this.dateHeureFin = dateHeureFin;
        }
    }
}
