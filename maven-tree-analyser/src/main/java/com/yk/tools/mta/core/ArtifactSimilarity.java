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

  /**
   * Strips off digits and a pyphen.
   */
  private String normalize(String artifactId) {
    if (artifactId == null || artifactId.isBlank() || artifactId.length() < 2) {
      return artifactId;
    }

    int index = -1;
    for (int i = artifactId.length() - 1; i >= 0; i--) {
      char c = artifactId.charAt(i);
      if (Character.isDigit(c)) {
        continue;
      } else if (c == '-') {
        index = i;
        break;
      } else if (c == '.') {
        return artifactId;
      } else {
        index = i + 1;
        break;
      }
    }

    return index > 0 ? artifactId.substring(0, index) : artifactId;
  }

}
