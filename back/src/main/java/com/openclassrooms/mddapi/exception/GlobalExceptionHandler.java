package com.openclassrooms.mddapi.exception;

import com.openclassrooms.mddapi.payload.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<MessageResponse> handleBadRequest(BadRequestException e) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(new MessageResponse(e.getMessage()));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<MessageResponse> handleNotFound(NotFoundException e) {
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(new MessageResponse(e.getMessage()));
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<MessageResponse> handleUnauthorized(UnauthorizedException e) {
    return ResponseEntity
      .status(HttpStatus.UNAUTHORIZED)
      .body(new MessageResponse(e.getMessage()));
  }
 // une régle de validation qui n'est pas respectée @Valid
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<MessageResponse> handleValidation(MethodArgumentNotValidException e) {
    String message = e.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(err -> err.getField() + " : " + err.getDefaultMessage())
      .findFirst()
      .orElse("Erreur de validation");
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(new MessageResponse(message));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<MessageResponse> handleAuth(AuthenticationException e) {
    return ResponseEntity
      .status(HttpStatus.UNAUTHORIZED)
      .body(new MessageResponse("Identifiants incorrects"));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<MessageResponse> handleGeneric(Exception e) {
    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(new MessageResponse("Erreur serveur : " + e.getMessage()));
  }
}
