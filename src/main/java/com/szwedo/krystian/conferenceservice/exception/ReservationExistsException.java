package com.szwedo.krystian.conferenceservice.exception;

public class ReservationExistsException extends RuntimeException {
  public ReservationExistsException() {
    super("You are already signed up for the lecture this hour.");
  }
}