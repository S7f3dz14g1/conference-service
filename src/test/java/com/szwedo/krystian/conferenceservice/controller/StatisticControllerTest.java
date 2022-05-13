package com.szwedo.krystian.conferenceservice.controller;

import com.szwedo.krystian.conferenceservice.service.StatisticService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatisticController.class)
class StatisticControllerTest {

  @MockBean
  private StatisticService service;
  @Autowired
  private WebApplicationContext context;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void lectureStatistics_endpoint_should_return_200_status() throws Exception {
    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/statistic/lectureStatistics/"))
        .andExpect(status().isOk());
  }

  @Test
  public void thematicPathsStatistic_endpoint_should_return_200_status() throws Exception {
    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/statistic/thematicPathsStatistic/"))
        .andExpect(status().isOk());
  }
}