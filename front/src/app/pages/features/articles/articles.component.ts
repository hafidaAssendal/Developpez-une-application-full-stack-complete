import { Component, effect, signal } from '@angular/core';
import { AuthService } from '../../auth/services/auth.service';
import { User } from 'src/app/interfaces/user.interface';

@Component({
  selector: 'app-articles',
  standalone: true,
  imports: [],
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.scss'
})
export class ArticlesComponent {

  usertest = this.authService.user;
  constructor( private authService: AuthService) {
  
   effect(() => {
      console.log('[ArticlesComponent] usertest changed :', this.usertest());
    });

  }



}
