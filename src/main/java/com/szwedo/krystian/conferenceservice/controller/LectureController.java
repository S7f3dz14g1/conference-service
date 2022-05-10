package com.szwedo.krystian.conferenceservice.controller;

import com.szwedo.krystian.conferenceservice.entity.LectureEntity;
import com.szwedo.krystian.conferenceservice.model.ThematicPaths;
import com.szwedo.krystian.conferenceservice.service.LectureService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/lecture/")
class LectureController {

  private final LectureService service;

  @GetMapping("/")
  public List<ThematicPaths> getConferenceInformation(){
    return service.getConferenceInformation();
  }

  @GetMapping("/{nick}")
  public List<LectureEntity> getLecturesByLogin(@PathVariable("nick") String login){
    return service.getLectureByLogin(login);
  }
}