package com.yk.tools.mta.core;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArtifactParser {

  private static final Pattern PATTERN = Pattern.compile(
      "(?:\\[[A-Z]+]\\s*)?[|\\\\\\s\\-+]*([\\w.\\-]+):([\\w.\\-]+):([\\w.\\-]+):([\\w.\\-]+)(?::([\\w.\\-]+))?"
  );

  public void parse(String line, Map<String, Artifact> artifacts) {
    Matcher matcher = PATTERN.matcher(line);
    if (!matcher.find()) {
      return;
    }

    String groupId = matcher.group(1);
    String artifactId = matcher.group(2);
    String version = matcher.group(4);

    String key = groupId + ":" + artifactId;
    artifacts.computeIfAbsent(key, k -> new Artifact(groupId, artifactId)).addVersion(version);
  }
}
