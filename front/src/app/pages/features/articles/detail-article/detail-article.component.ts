import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { switchMap } from 'rxjs';
import { Article } from 'src/app/interfaces/article.interface';
import { Comment } from 'src/app/interfaces/comment.interface';
import { ArticleService } from 'src/app/services/article.service';
import { HeaderComponent } from "src/app/shared/header/header.component";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { BackButtonComponent } from "src/app/shared/back-button/back-button.component";
import {  MatFormFieldModule } from "@angular/material/form-field";
import {  MatIconModule } from "@angular/material/icon";
import { DatePipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatDivider } from "@angular/material/divider";

@Component({
  selector: 'app-detail-article',
  standalone: true,
  imports: [
    DatePipe,
    ReactiveFormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatIconModule,
    HeaderComponent,
    BackButtonComponent,
    MatDivider
],
  templateUrl: './detail-article.component.html',
  styleUrl: './detail-article.component.scss'
})
export class DetailArticleComponent implements OnInit {

  article = signal<Article | null>(null);
  comments = signal<Comment[]>([]);
  isLoading = signal<boolean>(false);
  errorMessage = signal<string>('');

  commentForm: FormGroup;
   

  private articleService = inject(ArticleService);
  private route = inject(ActivatedRoute);
  private fb = inject(FormBuilder);
  private destroyRef = inject(DestroyRef);

  constructor() {
    this.commentForm = this.fb.group({
      content: ['', [Validators.required]]
    });
  }
  ngOnInit(): void {
    this.loadArticle();
  }

  private loadArticle(): void {
    this.isLoading.set(true);
    const id = Number(this.route.snapshot.paramMap.get('id'));

    // switchMap : annuler si nouvelle navigation
    this.articleService.getArticleById(id).pipe(
      takeUntilDestroyed(this.destroyRef),
      switchMap(article => {
        this.article.set(article);
        return this.articleService.getComment(id);
      })

    ).subscribe({
      next: (comments) => {
        this.comments.set(comments);
        this.isLoading.set(false);
      },
      error: () => {
       this.errorMessage.set('Erreur lors de chargement de l\'article....');
         this.isLoading.set(false);
      }
    })
  };
  
  sendComment():void{
      if (this.commentForm.invalid) return;
     const id = Number(this.route.snapshot.paramMap.get('id'));
    this.articleService.addComment(id,this.commentForm.value).pipe(
      takeUntilDestroyed(this.destroyRef)
    ).subscribe({
      next: (comment) => {
        this.comments.update(comments => [...comments, comment]);
        this.commentForm.reset();
      },
      error: () => {
        this.errorMessage.set('Erreur lors de l\'envoi du commentaire');
      }
    });
  }

}
