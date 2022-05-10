package com.szwedo.krystian.conferenceservice.controller;

import com.szwedo.krystian.conferenceservice.entity.LectureEntity;
import com.szwedo.krystian.conferenceservice.entity.ReservationEntity;
import com.szwedo.krystian.conferenceservice.exception.UserNotFoundException;
import com.szwedo.krystian.conferenceservice.model.ThematicPaths;
import com.szwedo.krystian.conferenceservice.service.LectureService;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LectureControllerTest {

  private LectureService service;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    service = mock(LectureService.class);
    mockMvc = MockMvcBuilders
        .standaloneSetup(new LectureController(service))
        .build();
  }

  @Test
  public void should_return_the_list_of_ThematicPath() throws Exception {
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
        .perform(MockMvcRequestBuilders.get("/lecture/"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", Matchers.is(1)))
        .andExpect(jsonPath("$[0].title").value("First"))
        .andExpect(jsonPath("$[0].lecture.keys()").value("[time]"));
  }

  @Test
  public void should_return_the_list_of_lectures() throws Exception {
    //given
    String login = "login";
    //when
    when(service.getLecturesByLogin(login)).thenReturn(List.of(LectureEntity.
        builder()
        .id(UUID.randomUUID())
        .subject("subject")
        .times("times")
        .thematicPathsTitle("path")
        .build()));
    //then
    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/lecture/").header("login", login))
        .andExpect(status().isOk());
  }

  @Test
  public void should_return_status_not_found_when_user_does_not_exist() {
    //given
    String login = "login";
    //when
    when(service.getLecturesByLogin(login)).thenThrow(UserNotFoundException.class);
    //then
    Assertions
        .assertThatThrownBy(() -> this.mockMvc
            .perform(MockMvcRequestBuilders.get("/lecture/").param("login", login))
            .andExpect(status().isNotFound())
            .andExpect(content().string("The user with login " + login + " does not found")));
  }

  private ReservationEntity getReservation(UUID userId, UUID lectureId) {
    return ReservationEntity.builder()
        .id(UUID.randomUUID())
        .lectureId(lectureId)
        .userId(userId)
        .build();
  }
}

