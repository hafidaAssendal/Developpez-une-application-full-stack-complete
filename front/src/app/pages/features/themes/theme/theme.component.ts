import { Component, DestroyRef, OnInit, signal, ChangeDetectionStrategy } from '@angular/core';
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


@Component({
  selector: 'app-theme',
  standalone: true,
  imports: [HeaderComponent,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatChipsModule,
    MatProgressBarModule,
    MatGridListModule

  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './theme.component.html',
  styleUrl: './theme.component.scss'
})
export class ThemeComponent implements OnInit {

  listThemes = signal<Theme[]>([]);
  errorMessage  = signal<string>('');


  constructor(private themService: ThemeService, private destroyRef: DestroyRef) { }

  ngOnInit(): void {
    this.loadThemes();

  }

  loadThemes(): void {
    this.themService.getThemes()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: (themes) => {
          this.listThemes.set(themes);

        },
        error: (err) => {
          this.errorMessage .set("erreur de chargement de themes.. ");
        }

      });

  }
  subscribe(idTheme: number): void{
    this.themService.subscribe(idTheme)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({

      next: () => {
        // Mettre à jour le signal localement
        this.listThemes.update(themes =>
          themes.map(t =>
            t.id === idTheme ? { ...t, subscribed: true } : t
          )
        );
      },
      error: () => {
        this.errorMessage.set('Erreur lors de l\'abonnement');
      }
       
      });
    
  }

}
