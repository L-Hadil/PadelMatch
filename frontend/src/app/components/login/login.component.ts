import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { UtilisateurService } from '../../services/utilisateur.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  utilisateur = {
    username: '',
    password: ''
  };

  constructor(private utilisateurService: UtilisateurService, private router: Router) {}

  login(): void {
    console.log("Logging in user: ", this.utilisateur);
    this.utilisateurService.login(this.utilisateur).subscribe({
      next: (response) => {
        console.log('Login successful', response);
        // Redirection après connexion réussie
        this.router.navigate(['/reservation']);
      },
      error: (error) => {
        console.error('Erreur lors de la connexion', error);
      }
    });
  }
}
