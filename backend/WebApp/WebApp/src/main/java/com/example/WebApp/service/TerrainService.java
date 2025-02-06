package com.example.WebApp.service;

import com.example.WebApp.enums.StatutTerrain;
import com.example.WebApp.model.Terrain;

import com.example.WebApp.repository.TerrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TerrainService {

    @Autowired
    private TerrainRepository terrainRepository;

    public Terrain saveTerrain(Terrain terrain) {
        return terrainRepository.save(terrain);
    }

    public Terrain findById(Long id) {
        return terrainRepository.findById(id).orElse(null);
    }

    public List<Terrain> findByStatut(StatutTerrain statut) {
        return terrainRepository.findByStatut(statut);
    }

    public void deleteTerrain(Long id) {
        terrainRepository.deleteById(id);
    }

    public List<Terrain> findAll() {
        return terrainRepository.findAll();
    }
}
