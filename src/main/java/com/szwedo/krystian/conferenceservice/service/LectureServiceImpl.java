package com.szwedo.krystian.conferenceservice.service;

import com.szwedo.krystian.conferenceservice.dao.LectureRepository;
import com.szwedo.krystian.conferenceservice.entity.LectureEntity;
import com.szwedo.krystian.conferenceservice.model.ThematicPaths;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class LectureServiceImpl implements LectureService {

  private final LectureRepository repository;

  @Override
  public List<ThematicPaths> getConferenceInformation() {
    return adaptThematicPaths(repository.findAll());
  }

  private List<ThematicPaths> adaptThematicPaths(List<LectureEntity> preselection) {
    return preselection.stream().map(p ->
            ThematicPaths.builder()
                .title(p.getThematicPathsTitle())
                .lecture(getLectures(preselection, p.getThematicPathsTitle()))
                .build())
        .toList();
  }

  private Map<String, String> getLectures(List<LectureEntity> preselection, String title) {
    Map<String, String> lectures = new HashMap<>();
    preselection.forEach(p -> {
      if (p.getThematicPathsTitle().equals(title))
        lectures.put(p.getTimes(), p.getTitle());
    });
    return lectures;
  }
}