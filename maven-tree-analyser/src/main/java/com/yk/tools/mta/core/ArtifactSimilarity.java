package com.yk.tools.mta.core;

public class ArtifactSimilarity {

  public boolean similar(Artifact a, Artifact b) {
    if (!a.getGroupId().equals(b.getGroupId())) {
      return false;
    }

    // Example
    // commons-lang
    // commons-lang3
    // commons-lang4
    // commons-lang-1
    // commons-lang-2
    // all their combinations are similar artifacts
    String artifactId1 = normalize(a.getArtifactId());
    String artifactId2 = normalize(b.getArtifactId());

    return artifactId1.equals(artifactId2);
  }

  private String normalize(String artifactId) {
    // Remove trailing -<digit> or <digit>
    return artifactId.replaceAll("(-?\\d{1,3})$", "");
  }

}
