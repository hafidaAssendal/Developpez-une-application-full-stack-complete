import { Component, DestroyRef, OnInit, signal, ChangeDetectionStrategy, inject } from '@angular/core';
import { HeaderComponent } from "src/app/shared/header/header.component";
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips'
import { ThemeService } from 'src/app/services/theme.service';
import { Theme } from 'src/app/interfaces/theme.interface';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinner } from "@angular/material/progress-spinner";


@Component({
  selector: 'app-theme',
  standalone: true,
  imports: [HeaderComponent,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatChipsModule,
    MatProgressBarModule,
    MatGridListModule, MatProgressSpinner],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './theme.component.html',
  styleUrl: './theme.component.scss'
})
export class ThemeComponent implements OnInit {
 // Signals
  themes = signal<Theme[]>([]);
  isLoading = signal<boolean>(false);
  errorMessage = signal<string>('');

  private themeService = inject(ThemeService);
  private destroyRef = inject(DestroyRef);

  ngOnInit(): void {
    this.loadThemes();
  }

  private loadThemes(): void {
    this.isLoading.set(true);

    this.themeService.getThemes().pipe(
      takeUntilDestroyed(this.destroyRef)
    ).subscribe({
      next: (themes) => {
        this.themes.set(themes);
        this.isLoading.set(false);
      },
      error: () => {
        this.errorMessage.set('Erreur lors du chargement des thèmes');
        this.isLoading.set(false);
      }
    });
  }

  subscribe(theme: Theme): void {
    this.themeService.subscribe(theme.id).pipe(
      takeUntilDestroyed(this.destroyRef)
    ).subscribe({
      next: () => {
        //  Mettre à jour le signal localement
        this.themes.update(themes =>
          themes.map(t =>
            t.id === theme.id ? { ...t, subscribed: true } : t
          )
        );
      },
      error: () => {
        this.errorMessage.set('Erreur lors de l\'abonnement');
      }
    });
  }
}