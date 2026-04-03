import { Component, signal } from '@angular/core';
import { Router } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
//import { AuthService } from '../../services/auth.service';
//import { LoginRequest } from '../../interfaces/login-request.interface';
import { HeaderComponent } from '../../../../shared/header/header.component';
import { BackButtonComponent } from '../../../../shared/back-button/back-button.component';
import { LoginRequest } from '../../interfaces/login-request.inerface';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    HeaderComponent,
    BackButtonComponent
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  errorMessage = signal<string>('');

  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    //private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      identifier: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) return;

    const request: LoginRequest = this.loginForm.value;

    // this.authService.login(request).subscribe({
    //   next: () => this.router.navigate(['/articles']),
    //   error: () => this.errorMessage.set('Identifiants incorrects')
    // });
  }
}