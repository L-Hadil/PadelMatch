package com.example.WebApp.controller;

import com.example.WebApp.model.Match;
import com.example.WebApp.model.Terrain;
import com.example.WebApp.enums.StatutTerrain;
import com.example.WebApp.enums.TypeSurface;
import com.example.WebApp.service.MatchService;
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
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MatchController.class)
public class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;

    @BeforeEach
    public void setup(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getAllMatches() throws Exception {
        Terrain terrain = new Terrain("Localisation1", StatutTerrain.DISPONIBLE, TypeSurface.HERBE);
        List<Match> matches = Arrays.asList(
                new Match(terrain, LocalDateTime.now(), "Team A vs Team B", "En cours"),
                new Match(terrain, LocalDateTime.now().plusHours(2), "Team C vs Team D", "En attente")
        );
        when(matchService.findAll()).thenReturn(matches);

        mockMvc.perform(get("/api/matchs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].description").value("Team A vs Team B"))
                .andExpect(jsonPath("$[0].status").value("En cours"))
                .andExpect(jsonPath("$[1].description").value("Team C vs Team D"))
                .andExpect(jsonPath("$[1].status").value("En attente"));
    }

    @Test
    public void getMatchById() throws Exception {
        Terrain terrain = new Terrain("Localisation2", StatutTerrain.RESERVE, TypeSurface.TERRE_BATTUE);
        Match match = new Match(terrain, LocalDateTime.now(), "Team X vs Team Y", "En attente");
        when(matchService.findById(1L)).thenReturn(match);

        mockMvc.perform(get("/api/matchs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Team X vs Team Y"))
                .andExpect(jsonPath("$.status").value("En attente"));
    }

    @Test
    public void getMatchByIdNotFound() throws Exception {
        when(matchService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/matchs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
