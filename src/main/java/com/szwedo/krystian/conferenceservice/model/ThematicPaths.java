package com.szwedo.krystian.conferenceservice.model;

import lombok.Builder;

import java.util.Map;

public record ThematicPaths(String title,
                            Map<String,String> lecture) {
  @Builder
  public ThematicPaths{
  }
}