package com.yk.tools.mta.core;

public class ArtifactPair {

  private Artifact artifact1;
  private Artifact artifact2;

  public ArtifactPair(Artifact artifact1, Artifact artifact2) {
    this.artifact1 = artifact1;
    this.artifact2 = artifact2;
  }

  public Artifact getArtifact1() {
    return artifact1;
  }

  public Artifact getArtifact2() {
    return artifact2;
  }
}
