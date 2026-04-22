import { Component, ChangeDetectionStrategy, inject, DestroyRef, signal, OnInit } from '@angular/core';
import { HeaderComponent } from "src/app/shared/header/header.component";
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ArticleService } from 'src/app/services/article.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { sign } from 'crypto';
import { Theme } from 'src/app/interfaces/theme.interface';
import { ThemeService } from 'src/app/services/theme.service';
import { BackButtonComponent } from "src/app/shared/back-button/back-button.component";
import { Router, RouterLink } from '@angular/router';
import { MatProgressSpinner } from "@angular/material/progress-spinner";


@Component({
  selector: 'app-create-article',
  standalone: true,
  imports: [HeaderComponent,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule, 
    ReactiveFormsModule, 
    BackButtonComponent, 
    MatProgressSpinner],
  templateUrl: './create-article.component.html',
  styleUrl: './create-article.component.scss'
})
export class CreateArticleComponent implements OnInit {
  errorMessage = signal<string>('');
  themeList = signal<Theme[]>([]);
  isLoading = signal<boolean>(false);

  articleForm: FormGroup;

  private fb = inject(FormBuilder);
  private articleService = inject(ArticleService);
  private destroyRef = inject(DestroyRef);
  private themeService = inject(ThemeService);
  private router = inject(Router)

  constructor() {
    this.articleForm = this.fb.group({
      title: ['', [Validators.required]],
      content: ['', [Validators.required]],
      themeId: ['', [Validators.required]]
    })
  }
  ngOnInit(): void {
    this.themeService.getThemes().pipe(
      takeUntilDestroyed(this.destroyRef)
    ).subscribe(
      {
        next: (themes) => {
          this.themeList.set(themes);
        },
        error: () => {
          this.errorMessage.set("Echec de chargement des themes...");
        }
      }
    )
  }

  submit(): void {
    if (this.articleForm.invalid) return;
    this.isLoading.set(true);

    this.articleService.addArticle(this.articleForm.value).pipe(
      takeUntilDestroyed(this.destroyRef)
    ).subscribe({
      next: (article) => {
        this.isLoading.set(false);
        this.router.navigate(['/articles']);


      },
      error: () => {
        this.errorMessage.set('Erreur lors de la création de l\'article');
        this.isLoading.set(false);
      }

    })
  }


}
