package com.openclassrooms.mddapi.models;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "subscriptions",
  uniqueConstraints = {
    // Un user ne peut s'abonner qu'une fois au même thème
    @UniqueConstraint(columnNames = {"user_id", "theme_id"})
  }
)
public class Subscription {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "theme_id", nullable = false)
  private Theme theme;

  @Column(name = "subscribed_at")
  private LocalDateTime subscribedAt;

  @PrePersist
  protected void onCreate() {
    subscribedAt = LocalDateTime.now();
  }
}
