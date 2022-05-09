package com.szwedo.krystian.conferenceservice.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String login) {
    super("The user with login " + login + " does not found");
  }
}