package com.szwedo.krystian.conferenceservice.service;

import com.szwedo.krystian.conferenceservice.controller.ReservationRequest;

import java.util.UUID;

public interface ReservationService {
  void reservationToLecture(ReservationRequest request);

  void cancelReservation(UUID reservationId, UUID userId);
}
