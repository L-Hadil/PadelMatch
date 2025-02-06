import { Utilisateur } from './utilisateur';

describe('Utilisateur', () => {
  it('should create an instance', () => {
    
    const utilisateur = new Utilisateur(1, 'Nom', 'email@example.com', 'passw');
    expect(utilisateur).toBeTruthy();
  });
});
