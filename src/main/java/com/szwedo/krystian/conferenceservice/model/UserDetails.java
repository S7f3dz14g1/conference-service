package com.szwedo.krystian.conferenceservice.model;

import lombok.Builder;

public record UserDetails(String login,
                          String email) {

  @Builder
  public UserDetails {

  }
}