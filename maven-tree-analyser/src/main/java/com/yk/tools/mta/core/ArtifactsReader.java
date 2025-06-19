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

/**
 * Reads artefacts from maven output.
 */
public class ArtifactsReader {

  private final ArtifactParser artifactParser = new ArtifactParser();

  public List<Artifact> read(File input) {
    Map<String, Artifact> artifactMap = new HashMap<>();

    try (InputStream is = new FileInputStream(input);
        BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
      String line;
      while ((line = br.readLine()) != null) {
        artifactParser.parse(line, artifactMap);
      }
    } catch (IOException e) {
      throw new RuntimeException(format("Failed to read lines from file: %s.", input.getAbsoluteFile()), e);
    }

    List<Artifact> artifactList = new ArrayList<>(artifactMap.values());
    artifactList.sort(new ArtifactComparator());
    return artifactList;
  }
}
