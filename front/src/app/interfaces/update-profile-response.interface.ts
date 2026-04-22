export interface UpdateProfileResponse {
    id: number;
    username: string;
    email: string;
    createdAt: Date;
    token: string | null; // ← null si email pas changé
}