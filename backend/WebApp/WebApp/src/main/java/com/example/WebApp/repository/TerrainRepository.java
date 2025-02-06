package com.example.WebApp.repository;

import com.example.WebApp.enums.StatutTerrain;
import com.example.WebApp.model.Terrain;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TerrainRepository extends JpaRepository<Terrain, Long> {
    List<Terrain> findByStatut(StatutTerrain statut);
}
