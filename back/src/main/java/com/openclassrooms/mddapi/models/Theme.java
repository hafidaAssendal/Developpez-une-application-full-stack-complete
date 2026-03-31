package com.openclassrooms.mddapi.models;

import lombok.*;
import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"articles","subscriptions"})
@Builder
@Entity
@Table(name = "themes")
public class Theme {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.NONE)
  private Long id;

  @Column(nullable = false, unique = true)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL)
  private List<Article> articles;

  @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL)
  private List<Subscription> subscriptions;
}
