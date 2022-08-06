package com.szwedo.krystian.conferenceservice.controller;

import com.szwedo.krystian.conferenceservice.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/reservation/")
class ReservationController {

    private final ReservationService service;

    @PostMapping("/")
    void reservationToLecture(@RequestBody ReservationRequest request) {
        service.reservationToLecture(request);
    }

    @DeleteMapping("/")
    void cancelReservation(@RequestParam("login") String login,
                           @RequestParam("email") String email,
                           @RequestParam("lectureSubject") String lectureSubject) {
        service.cancelReservation(email, login, lectureSubject);
    }
}
