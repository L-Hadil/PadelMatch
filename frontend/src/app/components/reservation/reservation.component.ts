import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';

import { UtilisateurService } from '../../services/utilisateur.service';
import { ReservationService } from '../../services/reservation.service';
import { Terrain } from '../../models/terrain';
import { Reservation } from '../../models/reservation';

@Component({
  selector: 'app-reservation',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatIconModule
  ],
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent {
  terrains: Terrain[] = [];
  selectedTerrain: Terrain | null = null;
  reservation: Reservation = {
    utilisateurId: 1,  // Remplacez par l'ID de l'utilisateur connecté
    terrainId: 0,
    dateHeureDebut: new Date(),  // Initialisez avec la date actuelle
    dateHeureFin: new Date()     // Initialisez avec la date actuelle
  };

  constructor(private utilisateurService: UtilisateurService, private reservationService: ReservationService) {}

  ngOnInit(): void {
    this.loadTerrains();
  }

  loadTerrains(): void {
    this.terrains = [
      { id: 1, name: 'Terrain 1', status: 'ouvert' },
      { id: 2, name: 'Terrain 2', status: 'maintenance' },
      { id: 3, name: 'Terrain 3', status: 'ouvert' },
      { id: 4, name: 'Terrain 4', status: 'occupé' }
    ];
  }

  selectTerrain(terrain: Terrain): void {
    this.selectedTerrain = terrain;
    this.reservation.terrainId = terrain.id;
  }

  makeReservation(): void {
    if (this.selectedTerrain && this.reservation.dateHeureDebut && this.reservation.dateHeureFin) {
      this.reservationService.createReservation(this.reservation).subscribe({
        next: (response) => {
          console.log('Réservation réussie', response);
          alert('Réservation effectuée avec succès!');
          this.selectedTerrain = null;
          this.reservation = {
            utilisateurId: 1, // Remplacez par l'ID de l'utilisateur connecté
            terrainId: 0,
            dateHeureDebut: new Date(),  // Réinitialisez avec la date actuelle
            dateHeureFin: new Date()     // Réinitialisez avec la date actuelle
          };
        },
        error: (error) => {
          console.error('Erreur lors de la réservation', error);
          alert('Erreur lors de la réservation. Veuillez réessayer.');
        }
      });
    }
  }
}
