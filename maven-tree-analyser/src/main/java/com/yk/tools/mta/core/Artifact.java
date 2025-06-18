package com.yk.tools.mta.core;

import java.util.ArrayList;
import java.util.List;

public class Artifact {

  private String groupId;
  private String artifactId;
  private List<String> versions = new ArrayList<String>();

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

  public List<String> getVersions() {
    return versions;
  }

  public int getVersionsSize() {
    return versions.size();
  }

  @Override
  public String toString() {
    return "Artifact{" +
        "groupId='" + groupId + '\'' +
        ", artifactId='" + artifactId + '\'' +
        ", versions=" + versionsToString() +
        '}';
  }

  private String versionsToString() {
    return String.join(", ", versions);
  }
}
