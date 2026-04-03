import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-back-button',
  standalone: true,
  imports: [MatIconModule],
  templateUrl: './back-button.component.html',
  styleUrl: './back-button.component.scss'
})
export class BackButtonComponent {
  @Input() route: string = '/'; // route par défaut

  constructor(private router: Router) {}

  goBack(): void {
    this.router.navigate([this.route]);
  }
}