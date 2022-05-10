package com.szwedo.krystian.conferenceservice.controller;


import lombok.Builder;

import javax.validation.constraints.NotBlank;

public record ReservationRequest(@NotBlank String login,
                                 @NotBlank String email,
                                 @NotBlank String title_lecture) {
  @Builder
  public ReservationRequest {

  }
}