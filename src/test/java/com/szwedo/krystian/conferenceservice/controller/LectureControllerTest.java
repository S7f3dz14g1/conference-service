package com.szwedo.krystian.conferenceservice.controller;

import com.szwedo.krystian.conferenceservice.model.ThematicPaths;
import com.szwedo.krystian.conferenceservice.service.LectureService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@WebMvcTest(LectureController.class)
class LectureControllerTest {

  @MockBean
  private LectureService service;
  @Autowired
  private WebApplicationContext webApplicationContext;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void should_return_the_list_of_ThematicPath() throws Exception {
    //given
    Map<String,String>lecture=new HashMap<>();
    lecture.put("time","subject");
    //when
    when(service.getConferenceInformation()).thenReturn(List.of(ThematicPaths.builder().title("First").lecture(lecture).build()));
    //then
    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/lecture/"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("First"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].lecture.keys()").value("[time]"));
  }
}