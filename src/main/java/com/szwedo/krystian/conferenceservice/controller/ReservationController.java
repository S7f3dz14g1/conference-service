package com.szwedo.krystian.conferenceservice.controller;

import com.szwedo.krystian.conferenceservice.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation/")
class ReservationController {

  private final ReservationService service;

  @PostMapping("/")
  public void reservationToLecture(@RequestBody ReservationRequest request) {
    service.reservationToLecture(request);
  }
}
