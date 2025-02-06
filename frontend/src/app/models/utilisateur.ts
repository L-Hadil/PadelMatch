export class Utilisateur {
  id: number;
  username: string;
  email: string;
  password: string;
  phoneNumber: string;

  constructor(id?: number, username?: string, email?: string, password?: string, phoneNumber?: string) {
    this.id = id || 0;
    this.username = username || '';
    this.email = email || '';
    this.password = password || '';
    this.phoneNumber = phoneNumber || '';
  }
}
