package com.openclassrooms.mddapi.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user","theme"})
@EqualsAndHashCode(of={"id"})
@Entity
@Table(name = "subscriptions",
  uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "theme_id"})
  }
)
public class Subscription {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.NONE)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "theme_id", nullable = false)
  private Theme theme;

  @CreationTimestamp
  @Column(name = "subscribed_at",updatable = false)
  private LocalDateTime subscribedAt;

}
