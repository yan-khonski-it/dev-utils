package com.yk.tools.mta.core;

import java.util.HashSet;
import java.util.Set;

public class Artifact {

  private final String groupId;
  private final String artifactId;
  private final Set<String> versions = new HashSet<>();

  public Artifact(String groupId, String artifactId) {
    this.groupId = groupId;
    this.artifactId = artifactId;
  }

  public void addVersion(String version) {
    versions.add(version);
  }

  public String getGroupId() {
    return groupId;
  }

  public String getArtifactId() {
    return artifactId;
  }

  public int getVersionsSize() {
    return versions.size();
  }

  @Override
  public String toString() {
    return "Artifact{" +
        "coordinates='" + groupId + ":" + artifactId + '\'' +
        ", versions=" + versionsToString() +
        '}';
  }

  public String versionsToString() {
    return String.join(", ", versions);
  }
}
