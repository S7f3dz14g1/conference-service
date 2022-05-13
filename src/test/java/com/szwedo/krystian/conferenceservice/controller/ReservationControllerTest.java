package com.szwedo.krystian.conferenceservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.szwedo.krystian.conferenceservice.exception.UnableToCancelReservationException;
import com.szwedo.krystian.conferenceservice.exception.UserNotFoundException;
import com.szwedo.krystian.conferenceservice.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReservationController.class)
class ReservationControllerTest {

  @MockBean
  private ReservationService service;
  @Autowired
  private WebApplicationContext context;
  @Autowired
  private MockMvc mockMvc;

//  @Test
//  public void reservationToLecture_endpoint_should_return_200_status() throws Exception {
//    //given
//    ObjectMapper mapper = new ObjectMapper();
//    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
//    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
//    String requestJson=ow.writeValueAsString(getRequest() );
//    String json = "{\n" + " \"login\" :\"login\",\n" + " \"email\" :\"email\",\n" + " \"title_lecture\" :\"lectureSubject\",\n" + "}";
//    //then
//    this.mockMvc
//        .perform(post("/reservation/")
//            .content(requestJson))
//            .andExpect(status().isOk());
//  }

  @Test
  public void cancelReservation_endpoint_should_return_404_status() throws Exception {
    //given
    String email = "email";
    String login = "login";
    String lecture_subject = "lectureSubject";
    //when
    doThrow(new UnableToCancelReservationException())
        .when(service).cancelReservation(email, login, lecture_subject);
    //then
    this.mockMvc
        .perform(delete("/reservation/")
            .param("login", login)
            .param("email", email)
            .param("lectureSubject", lecture_subject))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof UnableToCancelReservationException))
        .andExpect(result -> assertEquals("Unable to cancel the reservation.", result.getResolvedException().getMessage()));
  }

  @Test
  public void cancelReservation_endpoint_should_return_202_status() throws Exception {
    //given
    String email = "email";
    String login = "login";
    String lecture_subject = "lectureSubject";
    //then
    this.mockMvc
        .perform(delete("/reservation/")
            .param("login", login)
            .param("email", email)
            .param("lectureSubject", lecture_subject))
        .andExpect(status().isOk());
  }

  private ReservationRequest getRequest() {
    return ReservationRequest.builder()
        .login("login")
        .email("email")
        .title_lecture("title")
        .build();
  }


}