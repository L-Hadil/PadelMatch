package com.example.WebApp.repository;

import com.example.WebApp.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByTerrainId(Long terrainId);
    List<Match> findByDateHeureBetween(LocalDateTime start, LocalDateTime end);
}
