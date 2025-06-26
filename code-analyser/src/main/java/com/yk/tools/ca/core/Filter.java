package com.yk.tools.ca.core;

import java.util.Set;

/**
 * Default hard-coded filter to exclude files and directories from code analysis.
 */
public class Filter {

  private static final Set<String> DIRECTORIES = Set.of(
      ".idea",
      ".git",
      "test",
      "TMP",
      ".DS_Store",
      "__pycache__",
      "target",
      "virtual_environment"
      // add more, such as integration-tests, ui-tests,etc.
  );

  private static final Set<String> FILES = Set.of(
      ".gitignore",
      "README.md",
      "LICENSE",
      "CODEOWNERS",
      ".flattened-pom.xml",
      "Jenkinsfile"
  );

  public static boolean isFileExcluded(String fileName) {
    return FILES.contains(fileName);
  }

  public static boolean isDirectoryExcluded(String directory) {
    return DIRECTORIES.contains(directory);
  }
}
