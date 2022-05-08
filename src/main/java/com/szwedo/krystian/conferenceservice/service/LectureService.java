package com.szwedo.krystian.conferenceservice.service;

import com.szwedo.krystian.conferenceservice.model.ThematicPaths;
import java.util.List;


public interface LectureService {

  List<ThematicPaths> getConferenceInformation();
}