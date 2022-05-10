package com.szwedo.krystian.conferenceservice.exception;

public class UserEntityExistsException extends RuntimeException {
  public UserEntityExistsException(String login) {
    super("The user with login " + login + " already exists");
  }
}