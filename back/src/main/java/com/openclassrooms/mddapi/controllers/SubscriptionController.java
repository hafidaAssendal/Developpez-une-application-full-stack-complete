package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.services.ISubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3400)
@RestController
@RequestMapping("api/subscriptions")
public class SubscriptionController {
  private final ISubscriptionService subscriptionService;

  public SubscriptionController(ISubscriptionService subscriptionService) {
    this.subscriptionService = subscriptionService;
  }

  // POST /api/subscriptions/{id_theme}
  @PostMapping("/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public MessageResponse subscribe(Authentication authentication,
                                   @PathVariable Long id) {
    subscriptionService.subscribe(authentication.getName(), id);
    return new MessageResponse("Abonné!");

  }

  // DELETE  /api/subscriptions/{id_theme}
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void unsubscribe(Authentication authentication,
                          @PathVariable Long id) {

    subscriptionService.unsubscribe(authentication.getName(), id);

  }
}
