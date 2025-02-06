package com.example.WebApp.controller;

import com.example.WebApp.model.Reservation;
import com.example.WebApp.model.Terrain;
import com.example.WebApp.enums.StatutTerrain;
import com.example.WebApp.enums.TypeSurface;
import com.example.WebApp.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @BeforeEach
    public void setup(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getAllReservations() throws Exception {
        Terrain terrain = new Terrain("Localisation1", StatutTerrain.DISPONIBLE, TypeSurface.HERBE);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        when(reservationService.findAll()).thenReturn(Arrays.asList(
                new Reservation(1L, terrain, start, end),
                new Reservation(2L, terrain, start.plusDays(1), end.plusDays(1))
        ));

        mockMvc.perform(get("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].utilisateurId").value(1))
                .andExpect(jsonPath("$[1].utilisateurId").value(2));
    }

    @Test
    public void getReservationById() throws Exception {
        Terrain terrain = new Terrain("Localisation2", StatutTerrain.RESERVE, TypeSurface.TERRE_BATTUE);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        when(reservationService.findById(1L)).thenReturn(new Reservation(1L, terrain, start, end));

        mockMvc.perform(get("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.utilisateurId").value(1));
    }

    @Test
    public void createReservation() throws Exception {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        Terrain terrain = new Terrain("Localisation3", StatutTerrain.DISPONIBLE, TypeSurface.DUR);
        Reservation reservation = new Reservation(1L, terrain, start, end);
        when(reservationService.createReservation(anyLong(), anyLong(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(reservation);

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"utilisateurId\": 1, \"terrainId\": 1, \"dateHeureDebut\": \"" + start.toString() + "\", \"dateHeureFin\": \"" + end.toString() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.utilisateurId").value(1));
    }

    @Test
    public void deleteReservation() throws Exception {
        Terrain terrain = new Terrain("Localisation3", StatutTerrain.DISPONIBLE, TypeSurface.DUR);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        Reservation reservation = new Reservation(1L, terrain, start, end);
        when(reservationService.findById(1L)).thenReturn(reservation);
        doNothing().when(reservationService).deleteReservation(1L);

        mockMvc.perform(delete("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getReservationByIdNotFound() throws Exception {
        when(reservationService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
