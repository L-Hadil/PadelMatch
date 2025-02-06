package com.example.WebApp.service;

import com.example.WebApp.model.Match;
import com.example.WebApp.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    public Match findById(Long id) {
        return matchRepository.findById(id).orElse(null);
    }

    public List<Match> findByTerrainId(Long terrainId) {
        return matchRepository.findByTerrainId(terrainId);
    }

    public List<Match> findMatchesWithinTimeFrame(LocalDateTime start, LocalDateTime end) {
        return matchRepository.findByDateHeureBetween(start, end);
    }

    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }
}
