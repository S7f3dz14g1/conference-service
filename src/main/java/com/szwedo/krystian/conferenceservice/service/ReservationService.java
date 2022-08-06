package com.szwedo.krystian.conferenceservice.service;

import com.szwedo.krystian.conferenceservice.controller.ReservationRequest;

public interface ReservationService {
    void reservationToLecture(ReservationRequest request);

    void cancelReservation(String email, String login, String lectureSubject);
}
