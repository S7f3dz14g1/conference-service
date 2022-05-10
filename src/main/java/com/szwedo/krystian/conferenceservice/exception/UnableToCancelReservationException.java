package com.szwedo.krystian.conferenceservice.exception;

public class UnableToCancelReservationException extends RuntimeException {
  public UnableToCancelReservationException() {
    super("Unable to cancel the reservation.");
  }
}