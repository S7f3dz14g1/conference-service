package com.szwedo.krystian.conferenceservice.controller;

import com.szwedo.krystian.conferenceservice.entity.ReservationEntity;
import com.szwedo.krystian.conferenceservice.exception.UserNotFoundException;
import com.szwedo.krystian.conferenceservice.service.ReservationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReservationControllerTest {

  private ReservationService service;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    service = mock(ReservationService.class);
    mockMvc = MockMvcBuilders
        .standaloneSetup(new ReservationController(service))
        .build();
  }

  @Test
  public void should_throw_LectureIsFullyBookedException_when_number_of_occupied_places_is_more_then_four() {
    //given
    ReservationRequest request = getRequest();
    //when
    service.reservationToLecture(request);
    //then

  }

  private ReservationRequest getRequest() {
    return ReservationRequest.builder()
        .login("login")
        .email("email")
        .title_lecture("title")
        .build();
  }

//  @Test
//  public void should_return_conference_information() throws Exception {
//   this.mockMvc
//            .perform(MockMvcRequestBuilders.get("/lecture/"))
//            .andExpect(status().isOk());
//  }
}