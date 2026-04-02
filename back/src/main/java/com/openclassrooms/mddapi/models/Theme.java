package com.openclassrooms.mddapi.models;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"articles","subscriptions"})
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

  @Builder
  public Theme(String title,
               List<Article> articles,
               String description,
               List<Subscription> subscriptions) {
    this.title = title;
    this.articles = articles;
    this.description = description;
    this.subscriptions = subscriptions;
  }
}
