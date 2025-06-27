package com.yk.tools.ca.core;

import java.util.Set;

/**
 * Default hard-coded filter to exclude files and directories from code analysis.
 */
public class Filter {

  private static final Set<String> EXCLUDED_DIRECTORIES = Set.of(
      ".idea",
      ".git",
      "test",
      "TMP",
      ".DS_Store",
      "__pycache__",
      "target",
      "virtual_environment",
      ".settings",
      "documents",
      ".github",
      ".circleci"
      // add more, such as integration-tests, ui-tests,etc.
  );

  private static final Set<String> EXCLUDED_FILES = Set.of(
      ".gitignore",
      ".gitattributes",
      "README.md",
      "LICENSE",
      "CODEOWNERS",
      "CHANGES",
      "CHANGELOG",
      ".flattened-pom.xml",
      "Jenkinsfile",
      "codecov.yml",
      "install-jdk.sh",
      "KEYS.txt",
      "publish.sh",
      "osx-toolchains.xml",
      ".DS_Store"
  );

  private static final Set<String> EXCLUDED_FILENAME_EXTENSIONS = Set.of(
      "jpg",
      "png",
      "jar",
      "data",
      "dat",
      "bin",
      "keystore",
      "key",
      "crt"
  );

  public static boolean isFileExcluded(String fileName) {
    if (EXCLUDED_FILES.contains(fileName)) {
      return true;
    }

    String filenameExtension = getFilenameExtension(fileName);
    if (filenameExtension.isBlank()) {
      return false;
    }

    return EXCLUDED_FILENAME_EXTENSIONS.contains(filenameExtension);
  }

  public static boolean isDirectoryExcluded(String directory) {
    return EXCLUDED_DIRECTORIES.contains(directory);
  }

  private static String getFilenameExtension(String fileName) {
    int index = fileName.lastIndexOf('.');
    if (index == -1) {
      return "";
    }

    return fileName.substring(index + 1);
  }
}
