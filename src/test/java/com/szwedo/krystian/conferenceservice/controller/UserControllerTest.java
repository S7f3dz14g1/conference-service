package com.szwedo.krystian.conferenceservice.controller;

import com.szwedo.krystian.conferenceservice.exception.UserEntityExistsException;
import com.szwedo.krystian.conferenceservice.exception.UserNotFoundException;
import com.szwedo.krystian.conferenceservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

  @MockBean
  private UserService service;
  @Autowired
  private WebApplicationContext context;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void getAllUsers_endpoint_should_return_200_status() throws Exception {
    this.mockMvc
        .perform(get("/user/"))
        .andExpect(status().isOk());
  }

  @Test
  public void updateEmail_endpoint_should_return_200_status() throws Exception {
    //given
    String login = "login";
    String oldEmail = "oldEmail";
    String newEmail = "newEmail";
    //then
    this.mockMvc
        .perform(put("/user/")
            .param("login", login)
            .param("oldEmail", oldEmail)
            .param("newEmail", newEmail))
        .andExpect(status().isOk());
  }

  @Test
  public void updateEmail_endpoint_should_return_404() throws Exception {
    //given
    String login = "login";
    String oldEmail = "oldEmail";
    String newEmail = "newEmail";
    //when
    doThrow(new UserNotFoundException(login))
        .when(service).updateEmail(login, oldEmail, newEmail);
    //then
    this.mockMvc
        .perform(put("/user/")
            .param("login", login)
            .param("oldEmail", oldEmail)
            .param("newEmail", newEmail))
        .andExpect(status().isNotFound())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
        .andExpect(result -> assertEquals("The user with login " + login + " does not found", result.getResolvedException().getMessage()));
  }

  @Test
  public void updateEmail_endpoint_should_return_409() throws Exception {
    //given
    String login = "login";
    String oldEmail = "oldEmail";
    String newEmail = "newEmail";
    //when
    doThrow(new UserEntityExistsException(login))
        .when(service).updateEmail(login, oldEmail, newEmail);
    //then
    this.mockMvc
        .perform(put("/user/")
            .param("login", login)
            .param("oldEmail", oldEmail)
            .param("newEmail", newEmail))
        .andExpect(status().isConflict())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserEntityExistsException))
        .andExpect(result -> assertEquals("The user with login " + login + " already exists", result.getResolvedException().getMessage()));
  }
}