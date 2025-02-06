package com.example.WebApp.service;

import com.example.WebApp.enums.StatutTerrain;
import com.example.WebApp.enums.TypeSurface;
import com.example.WebApp.model.Match;
import com.example.WebApp.model.Terrain;
import com.example.WebApp.repository.MatchRepository;
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
public class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchService matchService;

    private Terrain terrain;
    private Match match;

    @BeforeEach
    public void setUp() {
        terrain = new Terrain("Localisation1", StatutTerrain.DISPONIBLE, TypeSurface.HERBE);
        terrain.setId(1L);
        match = new Match(terrain, LocalDateTime.now(), "Test Match", "En attente");
        match.setId(1L);
    }

    @Test
    public void testFindAll() {
        List<Match> matches = Arrays.asList(match);
        when(matchRepository.findAll()).thenReturn(matches);

        List<Match> foundMatches = matchService.findAll();
        assertNotNull(foundMatches);
        assertFalse(foundMatches.isEmpty());
        assertEquals(1, foundMatches.size());
        verify(matchRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));

        Match foundMatch = matchService.findById(1L);
        assertNotNull(foundMatch);
        assertEquals(match.getId(), foundMatch.getId());
        verify(matchRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdNotFound() {
        when(matchRepository.findById(1L)).thenReturn(Optional.empty());

        Match foundMatch = matchService.findById(1L);
        assertNull(foundMatch);
        verify(matchRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByTerrainId() {
        List<Match> matches = Arrays.asList(match);
        when(matchRepository.findByTerrainId(1L)).thenReturn(matches);

        List<Match> foundMatches = matchService.findByTerrainId(1L);
        assertNotNull(foundMatches);
        assertFalse(foundMatches.isEmpty());
        assertEquals(1, foundMatches.size());
        verify(matchRepository, times(1)).findByTerrainId(1L);
    }

    @Test
    public void testFindMatchesWithinTimeFrame() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        List<Match> matches = Arrays.asList(match);
        when(matchRepository.findByDateHeureBetween(start, end)).thenReturn(matches);

        List<Match> foundMatches = matchService.findMatchesWithinTimeFrame(start, end);
        assertNotNull(foundMatches);
        assertFalse(foundMatches.isEmpty());
        assertEquals(1, foundMatches.size());
        verify(matchRepository, times(1)).findByDateHeureBetween(start, end);
    }

    @Test
    public void testSaveMatch() {
        when(matchRepository.save(match)).thenReturn(match);

        Match savedMatch = matchService.saveMatch(match);
        assertNotNull(savedMatch);
        assertEquals(match.getId(), savedMatch.getId());
        verify(matchRepository, times(1)).save(match);
    }

    @Test
    public void testDeleteMatch() {
        doNothing().when(matchRepository).deleteById(1L);

        matchService.deleteMatch(1L);
        verify(matchRepository, times(1)).deleteById(1L);
    }
}
