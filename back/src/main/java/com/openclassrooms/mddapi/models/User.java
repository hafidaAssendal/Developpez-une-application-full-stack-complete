package com.openclassrooms.mddapi.models;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "users", uniqueConstraints = {
  @UniqueConstraint(columnNames = "email"),
  @UniqueConstraint(columnNames = "username")
})
@Data
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"articles", "comments", "subscriptions"})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
