package com.example.WebApp.controller;

import com.example.WebApp.model.Match;
import com.example.WebApp.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matchs")
public class MatchController {

    @Autowired
    private MatchService matchService;

    // Obtention de tous les matchs
    @GetMapping
    public List<Match> getAllMatchs() {
        return matchService.findAll();
    }

    // Obtention d'un match par ID
    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long id) {
        Match match = matchService.findById(id);
        if (match != null) {
            return ResponseEntity.ok(match);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
