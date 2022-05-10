package com.szwedo.krystian.conferenceservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(value = {UserNotFoundException.class})
  ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    ApiException apiException = getApiException(status, e.getMessage());
    return new ResponseEntity<>(apiException, status);
  }

  @ExceptionHandler(value = {UnableToCancelReservationException.class})
  ResponseEntity<Object> handleUnableToCancelReservationException(UnableToCancelReservationException e) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    ApiException apiException = getApiException(status, e.getMessage());
    return new ResponseEntity<>(apiException, status);
  }

  @ExceptionHandler(value = {LectureNotFoundException.class})
  ResponseEntity<Object> handleLectureNotFoundException(LectureNotFoundException e) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    ApiException apiException = getApiException(status, e.getMessage());
    return new ResponseEntity<>(apiException, status);
  }

  @ExceptionHandler(value = {UserEntityExistsException.class})
  ResponseEntity<Object> handleUserEntityExistsException(UserEntityExistsException e) {
    HttpStatus status = HttpStatus.CONFLICT;
    ApiException apiException = getApiException(status, e.getMessage());
    return new ResponseEntity<>(apiException, status);
  }

  @ExceptionHandler(value = {LectureIsFullyBookedException.class})
  ResponseEntity<Object> handleLectureIsFullyBookedException(LectureIsFullyBookedException e) {
    HttpStatus status = HttpStatus.CONFLICT;
    ApiException apiException = getApiException(status, e.getMessage());
    return new ResponseEntity<>(apiException, status);
  }

  @ExceptionHandler(value = {ReservationExistsException.class})
  ResponseEntity<Object> handleReservationExistsException(ReservationExistsException e) {
    HttpStatus status = HttpStatus.CONFLICT;
    ApiException apiException = getApiException(status, e.getMessage());
    return new ResponseEntity<>(apiException, status);
  }

  private ApiException getApiException(HttpStatus status, String message) {
    return ApiException.builder()
        .message(message)
        .status(status)
        .timeZone(ZonedDateTime.now(ZoneId.of("Z")))
        .build();
  }
}
