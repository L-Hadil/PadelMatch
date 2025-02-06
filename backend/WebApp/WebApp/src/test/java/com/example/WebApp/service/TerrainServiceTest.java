package com.example.WebApp.service;

import com.example.WebApp.enums.StatutTerrain;
import com.example.WebApp.enums.TypeSurface;
import com.example.WebApp.model.Terrain;
import com.example.WebApp.repository.TerrainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TerrainServiceTest {

    @Mock
    private TerrainRepository terrainRepository;

    @InjectMocks
    private TerrainService terrainService;

    private Terrain terrain;

    @BeforeEach
    public void setUp() {
        terrain = new Terrain("Localisation1", StatutTerrain.DISPONIBLE, TypeSurface.HERBE);
        terrain.setId(1L);
    }

    @Test
    public void testSaveTerrain() {
        when(terrainRepository.save(terrain)).thenReturn(terrain);

        Terrain savedTerrain = terrainService.saveTerrain(terrain);
        assertNotNull(savedTerrain);
        assertEquals(terrain.getId(), savedTerrain.getId());
        verify(terrainRepository, times(1)).save(terrain);
    }

    @Test
    public void testFindById() {
        when(terrainRepository.findById(1L)).thenReturn(Optional.of(terrain));

        Terrain foundTerrain = terrainService.findById(1L);
        assertNotNull(foundTerrain);
        assertEquals(terrain.getId(), foundTerrain.getId());
        verify(terrainRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdNotFound() {
        when(terrainRepository.findById(1L)).thenReturn(Optional.empty());

        Terrain foundTerrain = terrainService.findById(1L);
        assertNull(foundTerrain);
        verify(terrainRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByStatut() {
        List<Terrain> terrains = Arrays.asList(terrain);
        when(terrainRepository.findByStatut(StatutTerrain.DISPONIBLE)).thenReturn(terrains);

        List<Terrain> foundTerrains = terrainService.findByStatut(StatutTerrain.DISPONIBLE);
        assertNotNull(foundTerrains);
        assertFalse(foundTerrains.isEmpty());
        assertEquals(1, foundTerrains.size());
        verify(terrainRepository, times(1)).findByStatut(StatutTerrain.DISPONIBLE);
    }

    @Test
    public void testFindAll() {
        List<Terrain> terrains = Arrays.asList(terrain);
        when(terrainRepository.findAll()).thenReturn(terrains);

        List<Terrain> foundTerrains = terrainService.findAll();
        assertNotNull(foundTerrains);
        assertFalse(foundTerrains.isEmpty());
        assertEquals(1, foundTerrains.size());
        verify(terrainRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteTerrain() {
        doNothing().when(terrainRepository).deleteById(1L);

        terrainService.deleteTerrain(1L);
        verify(terrainRepository, times(1)).deleteById(1L);
    }
}
