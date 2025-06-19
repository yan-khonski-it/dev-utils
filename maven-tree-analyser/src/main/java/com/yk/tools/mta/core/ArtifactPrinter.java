package com.yk.tools.mta.core;

import java.util.List;

/**
 * Prints {@link Artifact} and {@link ArtifactPair} objects.
 */
public class ArtifactPrinter {

  public void printArtifacts(List<Artifact> artifacts) {
    if (artifacts.isEmpty()) {
      System.out.println("No artifacts found.");
      return;
    }

    for (Artifact artifact : artifacts) {
      System.out.println(printArtifact(artifact));
    }
  }

  public void printArtifactPairs(List<ArtifactPair> artifactPairs) {
    if (artifactPairs.isEmpty()) {
      System.out.println("No similar artifact pairs found.");
      return;
    }

    for (ArtifactPair pair : artifactPairs) {
      System.out.println(printArtifact(pair.artifact1()) + " -> " + printArtifact(pair.artifact2()));
    }
  }

  private String printArtifact(Artifact artifact) {
    return artifact.getGroupId() + ":" + artifact.getArtifactId() + " [" + artifact.versionsToString() + " ]";
  }
}
