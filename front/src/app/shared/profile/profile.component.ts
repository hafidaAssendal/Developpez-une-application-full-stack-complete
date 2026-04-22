import { Component, computed, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { MatFormField, MatLabel, MatError } from "@angular/material/form-field";
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from "@angular/forms";
import { MatInput } from "@angular/material/input";
import { MatDividerModule } from "@angular/material/divider";
import { User } from 'src/app/interfaces/user.interface';
import { MatButtonModule } from '@angular/material/button';
import { UserServiceService } from 'src/app/services/user-service.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { Theme } from 'src/app/interfaces/theme.interface';
import { ThemeService } from 'src/app/services/theme.service';
import { MatProgressSpinner } from "@angular/material/progress-spinner";
import { TokenService } from 'src/app/services/token.service';
import { UpdateProfileRequest } from 'src/app/interfaces/update-profile-request.interface';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [HeaderComponent,
    MatFormField,
    MatLabel,
    MatInput,
    ReactiveFormsModule,
    MatButtonModule,
    MatGridListModule,
    MatDividerModule,
    MatCardModule, MatError, MatProgressSpinner],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {

  // Signals
  subscriptions = signal<Theme[]>([]);
  isLoading = signal<boolean>(false);
  successMessage = signal<string>('');
  errorMessage = signal<string>('');
  private originalValues = signal<{ username: string, email: string }>({
    username: '',
    email: ''
  });
  private currentValues = signal<{ username: string, email: string, password: string }>({
    username: '',
    email: '',
    password: ''
  });
  //  Computed pour détecter si le formulaire a changé 
  hasChanges = computed(() => {
    const current = this.currentValues();
    const original = this.originalValues();

    const usernameChanged = current.username !== original.username;
    const emailChanged = current.email !== original.email;
    const passwordFilled = current.password !== null
      && current.password !== undefined
      && current.password.length > 0;

    return usernameChanged || emailChanged || passwordFilled;
  });

  profileForm: FormGroup;



  private fb = inject(FormBuilder);
  private userService = inject(UserServiceService)
  private destroyRef = inject(DestroyRef);
  private themeService = inject(ThemeService);
  private tokenService = inject(TokenService);

  constructor() {
    this.profileForm = this.fb.group({
      username: ['', [Validators.maxLength(50), Validators.required]],
      email: ['', [Validators.email, Validators.required, Validators.maxLength(50)]],
      password: ['', [Validators.pattern('^$|^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$')]]
    })
  }


  ngOnInit(): void {
    this.loadProfile();
    this.loadSubscriptions();
    // ✅ Mettre à jour le signal à chaque changement du formulaire
    this.profileForm.valueChanges.pipe(
      takeUntilDestroyed(this.destroyRef)
    ).subscribe(values => {
      this.currentValues.set(values);
    });
  }


  private loadProfile(): void {
    this.isLoading.set(true);
    this.userService.getUser().pipe(
      takeUntilDestroyed(this.destroyRef)
    ).subscribe({
      next: (user: User) => {

        this.profileForm.patchValue({
          username: user.username,
          email: user.email
        });

        //  Sauvegarder les valeurs originales
        this.originalValues.set({
          username: user.username,
          email: user.email
        });

        // Initialiser currentValues aussi
        this.currentValues.set({
          username: user.username,
          email: user.email,
          password: ''
        });

        this.isLoading.set(false);
      },
      error: () => this.errorMessage.set('Erreur lors du chargement du profil')
    });

  }

  private loadSubscriptions(): void {
    this.themeService.getThemes().pipe(
      takeUntilDestroyed(this.destroyRef)
    ).subscribe({
      next: (themes) => {
        // Garder uniquement les thèmes abonnés
        this.subscriptions.set(themes.filter(t => t.subscribed));
      },
      error: () => this.errorMessage.set('Erreur lors du chargement des abonnements')
    });
  }


  onSave(): void {

    if (this.profileForm.invalid || !this.hasChanges()) return;
    this.isLoading.set(true);
    this.successMessage.set('');
    this.errorMessage.set('');

    const request: UpdateProfileRequest = this.profileForm.value;

    // ignore si le password est vide
    if (!request.password) { delete request.password; }

    this.userService.updateUser(request).pipe(
      takeUntilDestroyed(this.destroyRef)
    ).subscribe({
      next: (response) => {
        this.successMessage.set('Profil mis à jour avec succès !');
        this.isLoading.set(false);
        // mis à jour les valeurs originales après sauvegarde
        this.originalValues.set({
          username: this.profileForm.value.username,
          email: this.profileForm.value.email
        });

        if (response.token) {
          this.tokenService.set(response.token);
        }

      },
      error: () => {
        this.errorMessage.set('Erreur lors de la mise à jour');
        this.isLoading.set(false);
      }
    });


  }

  unsubscribe(theme: Theme): void {
    this.themeService.unSubscribe(theme.id).pipe(
      takeUntilDestroyed(this.destroyRef)
    ).subscribe({
      next: () => {
        this.subscriptions.update(subs =>
          subs.filter(s => s.id !== theme.id)
        );
      },
      error: () => this.errorMessage.set('Erreur lors du désabonnement')
    });
  }

}
