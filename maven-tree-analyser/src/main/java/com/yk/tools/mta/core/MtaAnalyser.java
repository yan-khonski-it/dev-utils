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
import java.util.regex.Pattern;

public class MtaAnalyser {

  private static final Pattern PATTERN = Pattern.compile(
      "(?:\\[[A-Z]+]\\s*)?[|\\\\\\s\\-+]*([\\w.\\-]+):([\\w.\\-]+):([\\w.\\-]+):([\\w.\\-]+)(?::([\\w.\\-]+))?");

  private final ArtifactSimilarity artifactSimilarity = new ArtifactSimilarity();

  public void run(File input) {
    List<Artifact> artifacts = processLines(input);
    List<ArtifactPair> pairs = findSimilarArtifactsPairs(artifacts);
    printResults(artifacts, pairs);
  }

  private List<Artifact> processLines(File input) {
    Map<String, Artifact> artifacts = new HashMap<>();

    try (InputStream is = new FileInputStream(input);
        BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
      String line;
      while ((line = br.readLine()) != null) {
        Matcher matcher = PATTERN.matcher(line);
        if (!matcher.find()) {
          continue;
        }

        String groupId = matcher.group(1);
        String artifactId = matcher.group(2);
        String version = matcher.group(4);

        String key = groupId + ":" + artifactId;
        artifacts.computeIfAbsent(key, k -> new Artifact(groupId, artifactId)).addVersion(version);
      }
    } catch (IOException e) {
      throw new RuntimeException(format("Failed to read lines from file: %s", input.getAbsoluteFile()), e);
    }

    List<Artifact> artifactList = new ArrayList<>(artifacts.values());
    artifactList.sort(new ArtifactComparator());
    return artifactList;
  }

  private void printResults(List<Artifact> artifacts, List<ArtifactPair> pairs) {
    List<Artifact> processedArtifacts = new ArrayList<>(artifacts.size());

    for (Artifact artifact : artifacts) {
      if (artifact.getVersionsSize() > 1) {
        processedArtifacts.add(artifact);
      }
    }

    if (processedArtifacts.isEmpty()) {
      System.out.println("No artifacts with different versions found");
    } else {
      System.out.println("Found artifacts with different versions:");
      for (Artifact artifact : processedArtifacts) {
        System.out.println(artifact.toString());
      }
    }

    System.out.println();

    if (pairs.isEmpty()) {
      System.out.println("No similar artifacts pairs found");
    } else {
      System.out.println("Found similar artifacts pairs: ");
      for (ArtifactPair pair : pairs) {
        System.out.println(pair.getArtifact1().toString() + " -> " + pair.getArtifact2().toString());
      }
    }
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
