package com.yk.tools.ca.core;

import com.yk.tools.ca.CodeConverterArguments;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeConverter {

  public void runCodeConverter(CodeConverterArguments codeConverterArguments) {
    File outputFile = codeConverterArguments.outputFile();
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
      processDirectory(codeConverterArguments.inputDirectory(), writer);
    } catch (IOException e) {
      System.out.printf("Failed to open output file. %s%n", outputFile.getAbsoluteFile());
      e.printStackTrace();
    }
  }

  private void processDirectory(File directory, BufferedWriter writer) throws IOException {
    if (!directory.exists() || !directory.isDirectory()) {
      System.err.printf("Directory %s does not exist or is not a directory.%n", directory.getAbsoluteFile());
    }

    if (Filter.isDirectoryExcluded(directory.getName())) {
      System.out.println("Directory excluded: " + directory.getAbsoluteFile());
      return;
    }

    Map<String, List<File>> packageFilesMap = new HashMap<>();
    collectFilesByPackage(directory, directory, packageFilesMap);

    // write the formatted output
    for (Map.Entry<String, List<File>> entry : packageFilesMap.entrySet()) {
      String packageName = entry.getKey();
      List<File> files = entry.getValue();

      // Write the package name
      writer.write("# " + packageName);
      writer.newLine();

      for (File file : files) {
        if (Filter.isFileExcluded(file.getName())) {
          System.out.println("File excluded: " + file.getAbsoluteFile());
          continue;
        }

        writer.write("## " + file.getName());
        writer.newLine();
        writeFileContent(file, writer);
        writer.newLine(); // Add blank line between files.
      }
    }
  }

  private void collectFilesByPackage(File rootDirectory, File currentDirectory, Map<String, List<File>> packageFilesMap) {
    if (Filter.isDirectoryExcluded(currentDirectory.getName())) {
      System.out.println("[collectFilesByPackage] Directory excluded: " + currentDirectory.getAbsoluteFile());
    }
    File[] files = currentDirectory.listFiles();
    if (files == null) {
      return;
    }

    for (File file : files) {
      if (file.isDirectory()) {
        collectFilesByPackage(rootDirectory, file, packageFilesMap);
      } else if (file.isFile()) {
        if (Filter.isFileExcluded(file.getName())) {
          System.out.println("[collectFilesByPackage] File excluded: " + file.getAbsoluteFile());
          continue;
        }

        String packageName = rootDirectory.toURI().relativize(file.getParentFile().toURI()).getPath();
        if (packageName.isBlank()) {
          packageName = "java";
        }

        // Remove trailing slash from the package name.
        if (packageName.endsWith("/")) {
          packageName = packageName.substring(0, packageName.length() - 1);
        }

        // Add files to the package
        packageFilesMap.computeIfAbsent(packageName, k -> new ArrayList<>()).add(file);
      }
    }
  }

  private void writeFileContent(File file, BufferedWriter writer) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = reader.readLine()) != null) {
        writer.write(line);
        writer.newLine();
      }
    }
  }
}
