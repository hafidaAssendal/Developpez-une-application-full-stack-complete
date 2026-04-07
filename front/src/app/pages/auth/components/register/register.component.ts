import { Component, signal } from '@angular/core';
import { Router } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { SignupRequest } from '../../interfaces/signup-request.interface';
import { HeaderComponent } from 'src/app/shared/header/header.component';
import { BackButtonComponent } from 'src/app/shared/back-button/back-button.component';
import { AuthService } from '../../services/auth.service';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    HeaderComponent,
    BackButtonComponent
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  errorMessage = signal<string>('');
  successMessage = signal<string>('');

  private readonly PASSWORD_PATTERN =
    /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$/;

  registerForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [
                      Validators.required,
                      Validators.pattern(this.PASSWORD_PATTERN)
      ]]
    });
  }

  onSubmit(): void {
    if (this.registerForm.invalid) return;

    const request: SignupRequest = this.registerForm.value;

    this.authService.register(request).subscribe({
      next: () => {
        this.successMessage.set('Inscription réussie !');
        setTimeout(() => this.router.navigate(['/login']), 2000);
      },
      error: (err) => {
        this.errorMessage.set(err.error?.message || 'Erreur lors de l\'inscription');
      }
    });
  }
}