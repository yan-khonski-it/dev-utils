package com.yk.tools.mta.core;

import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class MtaAnalyser {

  private final ArtifactsReader artifactsReader = new  ArtifactsReader();
  private final ArtifactSimilarity artifactSimilarity = new ArtifactSimilarity();
  private final ArtifactPrinter artifactPrinter = new  ArtifactPrinter();

  public void run(File input) {
    List<Artifact> artifacts = artifactsReader.read(input);
    List<ArtifactPair> pairs = findSimilarArtifactsPairs(artifacts);
    artifactPrinter.printArtifacts(artifacts);
    artifactPrinter.printArtifactPairs(pairs);
  }

  private List<ArtifactPair> findSimilarArtifactsPairs(List<Artifact> artifacts) {
    Map<String, List<Artifact>> artifactsMap = new HashMap<>();
    for (Artifact artifact : artifacts) {
      artifactsMap.computeIfAbsent(artifact.getGroupId(), k -> new ArrayList<>()).add(artifact);
    }

    List<ArtifactPair> pairs = new ArrayList<>();

    for (Map.Entry<String, List<Artifact>> entry : artifactsMap.entrySet()) {
      List<Artifact> artifactsValue = entry.getValue();
      if (artifactsValue.size() < 2) {
        continue;
      }

      for (int i = 0; i < artifactsValue.size() - 1; i++) {
        Artifact a1 = artifactsValue.get(i);
        Artifact a2 = artifactsValue.get(i + 1);
        if (artifactSimilarity.similar(a1, a2)) {
          pairs.add(new ArtifactPair(a1, a2));
        }
      }
    }

    return pairs;
  }
}
