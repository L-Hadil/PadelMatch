export interface Terrain {
    id: number;
    name: string;
    status: 'ouvert' | 'maintenance' | 'occupé';  // Assurez-vous que le statut 'occupé' est bien défini ici
  }
  