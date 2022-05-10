package com.szwedo.krystian.conferenceservice.exception;

public class LectureNotFoundException extends RuntimeException {
  public LectureNotFoundException(String title_lecture) {
    super("The lecture with subject " + title_lecture + " does not found");
  }
}