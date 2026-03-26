package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

  @Query("SELECT COUNT(s) > 0 FROM Subscription s " +
    "WHERE s.user.id = :userId AND s.theme.id = :themeId")
  boolean existsByUserIdAndTheme(@Param("userId") Long userId,
                                 @Param("themeId") Long themeId);

}
