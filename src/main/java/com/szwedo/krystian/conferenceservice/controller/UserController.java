package com.szwedo.krystian.conferenceservice.controller;

import com.szwedo.krystian.conferenceservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user/")
 class UserController {

  private final UserService service;

  @PutMapping("/")
  public void updateEmail(@RequestParam("login") String login,
                                @RequestParam("oldEmail") String oldEmail,
                                @RequestParam("newEmail") String newEmail) {
    service.updateEmail(login, oldEmail, newEmail);
  }
}