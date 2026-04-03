import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-back-button',
  standalone: true,
  imports: [MatIconModule, MatButtonModule],
  templateUrl: './back-button.component.html',
  styleUrls: ['./back-button.component.scss']
})
export class BackButtonComponent {

  constructor(private location: Location) {}

  goBack(): void {
    this.location.back();
  }
}