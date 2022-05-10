package com.szwedo.krystian.conferenceservice.exception;

public class LectureIsFullyBookedException extends RuntimeException {
  public LectureIsFullyBookedException(String subject) {
    super("The lecture on the  title " + subject + " is fully booked");
  }
}