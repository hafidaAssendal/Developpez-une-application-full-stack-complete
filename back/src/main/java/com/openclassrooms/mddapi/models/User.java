package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Accessors(chain = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"articles", "comments", "subscriptions"})
@Entity
@Table(name = "users", uniqueConstraints = {
  @UniqueConstraint(columnNames = "email"),
  @UniqueConstraint(columnNames = "username")
})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.NONE)
  private Long id;

  @NonNull
  @Size(max = 50)
  @Email
  private String email;

  @NonNull
  @Size(max = 50)
  private String username;

  @NonNull
  @Size(max = 120)
  private String password;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
  private List<Article> articles;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
  private List<Comment> comments;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Subscription> subscriptions;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
