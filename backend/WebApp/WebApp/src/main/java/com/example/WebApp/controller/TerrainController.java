package com.example.WebApp.controller;

import com.example.WebApp.model.Terrain;
import com.example.WebApp.service.TerrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terrains")
public class TerrainController {

    @Autowired
    private TerrainService terrainService;

    // Obtention de tous les terrains
    @GetMapping
    public List<Terrain> getAllTerrains() {
        return terrainService.findAll();
    }

    // Obtention d'un terrain par ID
    @GetMapping("/{id}")
    public ResponseEntity<Terrain> getTerrainById(@PathVariable Long id) {
        Terrain terrain = terrainService.findById(id);
        if (terrain != null) {
            return ResponseEntity.ok(terrain);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
