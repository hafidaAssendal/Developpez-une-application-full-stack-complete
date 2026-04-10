import { Component,OnInit,signal,computed,inject,DestroyRef } from '@angular/core';
import { Router } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { DatePipe, SlicePipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { HeaderComponent } from 'src/app/shared/header/header.component';
import { Article } from 'src/app/interfaces/article.interface';
import { ArticleService } from 'src/app/services/article.service';
;

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [
    SlicePipe,
    DatePipe,
    MatButtonModule,
    MatProgressSpinnerModule,
    HeaderComponent
  ],
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss'
})
export class ArticlesComponent implements OnInit {

  // Signals
  private listArticles = signal<Article[]>([]);
  isLoading = signal<boolean>(false);
  errorMessage = signal<string>('');
  sortAsc = signal<boolean>(false);

  // Computed pour le  tri dynamique
  sortedArticles = computed(() => {
    return [...this.listArticles()].sort((a, b) => {
      const dateA = new Date(a.createdAt).getTime();
      const dateB = new Date(b.createdAt).getTime();
      return this.sortAsc() ? dateA - dateB : dateB - dateA;
    });
  });

  private articleService = inject(ArticleService);
  private router = inject(Router);
  private destroyRef = inject(DestroyRef);

  ngOnInit(): void {
    this.loadFeed();
  }

  private loadFeed(): void {
    this.isLoading.set(true);
    this.errorMessage.set('');

    this.articleService.getArticles().pipe(
      takeUntilDestroyed(this.destroyRef) // désabonnement auto
    ).subscribe({
      next: (articles) => {
        this.listArticles.set(articles);
        this.isLoading.set(false);
      },
      error: () => {
        this.errorMessage.set('Erreur lors du chargement des articles');
        this.isLoading.set(false);
      }
    });
  }

  toggleSort(): void {
    this.sortAsc.update(v => !v);
  }

  goToArticle(id: number): void {
    this.router.navigate(['/articles', id]);
  }

  goToCreate(): void {
    this.router.navigate(['/articles/create']);
  }

  goToThemes(): void {
    this.router.navigate(['/themes']);
  }
}

