package com.szwedo.krystian.conferenceservice.controller;

import com.szwedo.krystian.conferenceservice.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation/")
class ReservationController {

  private final ReservationService service;

  @PostMapping("/")
  public void reservationToLecture(@RequestBody ReservationRequest request) {
    service.reservationToLecture(request);
  }

  @DeleteMapping("/")
  public void cancelReservation(@RequestParam("reservationId") UUID reservationId,
                                @RequestParam("userId") UUID userId) {
    service.cancelReservation(reservationId, userId);
  }
}
