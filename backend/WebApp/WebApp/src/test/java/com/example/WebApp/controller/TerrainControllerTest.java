package com.example.WebApp.controller;

import com.example.WebApp.model.Terrain;
import com.example.WebApp.enums.StatutTerrain;
import com.example.WebApp.enums.TypeSurface;
import com.example.WebApp.service.TerrainService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TerrainController.class)
public class TerrainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TerrainService terrainService;

    @BeforeEach
    public void setup(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getAllTerrains() throws Exception {
        List<Terrain> terrains = Arrays.asList(
                new Terrain("Localisation1", StatutTerrain.DISPONIBLE, TypeSurface.HERBE),
                new Terrain("Localisation2", StatutTerrain.RESERVE, TypeSurface.TERRE_BATTUE)
        );
        when(terrainService.findAll()).thenReturn(terrains);

        mockMvc.perform(get("/api/terrains")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].localisation").value("Localisation1"))
                .andExpect(jsonPath("$[0].statut").value("DISPONIBLE"))
                .andExpect(jsonPath("$[0].typeSurface").value("HERBE"))
                .andExpect(jsonPath("$[1].localisation").value("Localisation2"))
                .andExpect(jsonPath("$[1].statut").value("RESERVE"))
                .andExpect(jsonPath("$[1].typeSurface").value("TERRE_BATTUE"));
    }

    @Test
    public void getTerrainById() throws Exception {
        Terrain terrain = new Terrain("Localisation1", StatutTerrain.DISPONIBLE, TypeSurface.HERBE);
        when(terrainService.findById(1L)).thenReturn(terrain);

        mockMvc.perform(get("/api/terrains/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.localisation").value("Localisation1"))  // Corrected typo here
                .andExpect(jsonPath("$.statut").value("DISPONIBLE"))
                .andExpect(jsonPath("$.typeSurface").value("HERBE"));
    }


    @Test
    public void getTerrainByIdNotFound() throws Exception {
        when(terrainService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/terrains/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
