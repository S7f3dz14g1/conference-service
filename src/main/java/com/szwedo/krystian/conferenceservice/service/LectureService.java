package com.szwedo.krystian.conferenceservice.service;

import com.szwedo.krystian.conferenceservice.entity.LectureEntity;
import com.szwedo.krystian.conferenceservice.model.ThematicPaths;
import java.util.List;

public interface LectureService {

  List<ThematicPaths> getConferenceInformation();

  List<LectureEntity> getLectureByLogin(String login);
}