package com.szwedo.krystian.conferenceservice.model;

import lombok.Builder;

public record ThematicPathsStatistic(String thematic_paths_title,
                                     String interest) {
  @Builder
  public ThematicPathsStatistic {

  }
}