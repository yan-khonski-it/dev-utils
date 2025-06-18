package com.yk.tools.mta;

import java.util.regex.*;

public class MavenDependencyParser {
  private static final Pattern DEP_PATTERN = Pattern.compile(
      "(?:\\[[A-Z]+\\]\\s*)?[|\\\\\\s\\-+]*([\\w.\\-]+):([\\w.\\-]+):([\\w.\\-]+):([\\w.\\-]+)(?::([\\w.\\-]+))?"
  );

  public static void main(String[] args) {
    String[] lines = {
        "[INFO] com.yk.training:jersey-example:war:1.0-SNAPSHOT",
        "[INFO] +- org.glassfish.jersey.containers:jersey-container-servlet:jar:2.31:compile",
        "[INFO] |  +- org.glassfish.jersey.containers:jersey-container-servlet-core:jar:2.31:compile",
        "[INFO] |  |  \\- org.glassfish.hk2.external:jakarta.inject:jar:2.6.1:compile",
        "[INFO] |  |  \\- org.glassfish.hk2.external:jakarta.inject:jar:2.6.1:compile"
    };

    for (String line : lines) {
      Matcher m = DEP_PATTERN.matcher(line);
      if (m.find()) {
        System.out.printf(
            "groupId=%s, artifactId=%s, type=%s, version=%s, scope=%s%n",
            m.group(1), m.group(2), m.group(3), m.group(4), m.group(5)
        );
      } else {
        System.out.println("NO MATCH: " + line);
      }
    }
  }
}