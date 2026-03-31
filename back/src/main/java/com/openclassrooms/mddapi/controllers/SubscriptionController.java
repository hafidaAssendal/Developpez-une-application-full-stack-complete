package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.services.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3400)
@RestController
@RequestMapping("api/subscriptions")
public class SubscriptionController {
  private final SubscriptionService subscriptionService;

  public SubscriptionController(SubscriptionService subscriptionService) {
    this.subscriptionService = subscriptionService;
  }

  // POST /api/subscriptions/{id_theme}
  @PostMapping("/{id_theme}")
  @ResponseStatus(HttpStatus.CREATED)
  public MessageResponse subscribe(Authentication authentication,
                                   @PathVariable Long id_theme) {
    subscriptionService.subscribe(authentication.getName(), id_theme);
    return new MessageResponse("Abonné!");

  }

  // DELETE  /api/subscriptions/{id_theme}
  @DeleteMapping("/{id_theme}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void unsubscribe(Authentication authentication,
                          @PathVariable Long id_theme) {

    subscriptionService.unsubscribe(authentication.getName(), id_theme);

  }
}
