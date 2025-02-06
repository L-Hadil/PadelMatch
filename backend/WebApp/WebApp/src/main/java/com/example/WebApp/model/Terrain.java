package com.example.WebApp.model;

import com.example.WebApp.enums.StatutTerrain;
import com.example.WebApp.enums.TypeSurface;
import jakarta.persistence.*;

@Entity
@Table(name = "terrains")
public class Terrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String localisation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutTerrain statut;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeSurface typeSurface;

    // Constructeurs
    public Terrain() {}

    public Terrain(String localisation, StatutTerrain statut, TypeSurface typeSurface) {
        this.localisation = localisation;
        this.statut = statut;
        this.typeSurface = typeSurface;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public StatutTerrain getStatut() {
        return statut;
    }

    public void setStatut(StatutTerrain statut) {
        this.statut = statut;
    }

    public TypeSurface getTypeSurface() {
        return typeSurface;
    }

    public void setTypeSurface(TypeSurface typeSurface) {
        this.typeSurface = typeSurface;
    }
}
