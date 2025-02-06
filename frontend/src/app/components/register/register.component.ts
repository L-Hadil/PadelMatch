import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { UtilisateurService } from '../../services/utilisateur.service';
import { Router } from '@angular/router';
import { Utilisateur } from '../../models/utilisateur'; // Importez le modèle Utilisateur

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  utilisateur: Utilisateur = {
    id: 0, // Initialisez l'id ici
    username: '',
    email: '',
    password: '',
    phoneNumber: ''
  };

  constructor(private utilisateurService: UtilisateurService, private router: Router) {}

  register(): void {
    console.log("Registering user: ", this.utilisateur);
    this.utilisateurService.register(this.utilisateur).subscribe({
      next: (response) => {
        console.log('Registration successful', response);
        // Redirection après inscription réussie
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.error('Erreur lors de l\'inscription', error);
      }
    });
  }
}
