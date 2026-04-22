import { Injectable, signal } from '@angular/core';
import { Article } from '../interfaces/article.interface';
import { Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Comment } from '../interfaces/comment.interface';
import { CommentRequest } from '../interfaces/comment-request.interface';
import { ArticleRequest } from '../interfaces/article-request.interface';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private http: HttpClient) { }

  getArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(`${environment.apiUrl}/articles`);
  }

  getArticleById(id: number): Observable<Article> {
    return this.http.get<Article>(`${environment.apiUrl}/articles/${id}`);
  }
  
  addArticle(articleRequest:ArticleRequest):Observable<Article>{
    return this.http.post<Article>(`${environment.apiUrl}/articles`,articleRequest);
  }

  getComment(id: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${environment.apiUrl}/articles/${id}/comments`);
  }

 
   addComment(articleId: number, request: CommentRequest): Observable<Comment> {
    return this.http.post<Comment>(
      `${environment.apiUrl}/articles/${articleId}/comments`, request
    );
  }
}
