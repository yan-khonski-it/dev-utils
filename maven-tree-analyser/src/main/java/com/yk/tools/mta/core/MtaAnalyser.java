package com.yk.tools.mta.core;

import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MtaAnalyser {

  private static final Pattern PATTERN = Pattern.compile(
      "(?:\\[[A-Z]+]\\s*)?[|\\\\\\s\\-+]*([\\w.\\-]+):([\\w.\\-]+):([\\w.\\-]+):([\\w.\\-]+)(?::([\\w.\\-]+))?");

  public void run(File input) {
    List<Artifact> artifacts = processLines(input);
    printResults(artifacts);
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

  private void printResults(List<Artifact> artifacts) {
    List<Artifact> processedArtifacts = new ArrayList<>(artifacts.size());

    for  (Artifact artifact : artifacts) {
      if (artifact.getVersionsSize() > 1) {
        processedArtifacts.add(artifact);
      }
    }

    if (processedArtifacts.isEmpty()) {
      System.out.println("No artifacts found");
    } else {
      for  (Artifact artifact : processedArtifacts) {
        System.out.println(artifact.toString());
      }
    }
  }
}
