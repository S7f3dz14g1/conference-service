package com.szwedo.krystian.conferenceservice.controller;

import com.szwedo.krystian.conferenceservice.exception.UserNotFoundException;
import com.szwedo.krystian.conferenceservice.model.ThematicPaths;
import com.szwedo.krystian.conferenceservice.service.LectureService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LectureController.class)
class LectureControllerTest {

  @MockBean
  private LectureService service;
  @Autowired
  private WebApplicationContext context;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void getConferenceInformation_endpoint_should_return_200_status() throws Exception {
    //given
    Map<String, String> lecture = new HashMap<>();
    lecture.put("time", "subject");
    //when
    when(service.getConferenceInformation()).thenReturn(List.of(ThematicPaths.builder()
        .title("First")
        .lecture(lecture)
        .build()));
    //then
    this.mockMvc
        .perform(get("/lecture/"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", Matchers.is(1)))
        .andExpect(jsonPath("$[0].title").value("First"))
        .andExpect(jsonPath("$[0].lecture.keys()").value("[time]"));
  }

  @Test
  public void getLecturesByLogin_endpoint_should_return_200_status() throws Exception {
    //given
    String login = "login";
    //then
    this.mockMvc
        .perform(get("/lecture/", login))
        .andExpect(status().isOk());
  }

//  @Test
//  public void getLecturesByLogin_endpoint_should_return_404_status() throws Exception {
//    //given
//    String login = "login";
//    //when
//    doThrow(new UserNotFoundException(login))
//        .when(service).getLecturesByLogin(login);
//    //then
//    this.mockMvc
//        .perform(get("/lecture/")
//            .param("login", login))
//        .andExpect(status().isNotFound())
//        .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
//        .andExpect(result -> assertEquals("The user with login " + login + " does not found", result.getResolvedException().getMessage()));
//  }
}