package com.yk.tools.ca.core;

import com.yk.tools.ca.CodeAnalyserArguments;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CodeAnalyser {

  public void runCodeAnalysis(CodeAnalyserArguments codeAnalyserArguments) {
    File directory = codeAnalyserArguments.inputDirectory();
    CodeAnalysisResult codeAnalysisResult = analyseDirectory(directory);
    System.out.println("Code analysis is complete.\n" + codeAnalysisResult);
  }

  private CodeAnalysisResult analyseDirectory(File directory) {
    CodeAnalysisResult totalResult = new CodeAnalysisResult();
    totalResult.incrementDirectoryCount();

    File[] files = directory.listFiles();
    if (files == null) {
      return totalResult;
    }

    for (File file : files) {
      if (file.isFile()) {
        CodeAnalysisResult fileResult = analyseFile(file);
        totalResult.add(fileResult);
      } else if (file.isDirectory()) {
        CodeAnalysisResult childDirectoryResult = analyseDirectory(file);
        totalResult.add(childDirectoryResult);
      } else {
        System.out.println("Unsupported file: " + file.getAbsolutePath());
      }
    }

    return totalResult;
  }

  private CodeAnalysisResult analyseFile(File file) {
    long characterCount = 0;
    long wordCount = 0;
    long lineCount = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        characterCount += line.length();
        wordCount += countWords(line);
        lineCount++;
      }
    } catch (IOException e) {
      System.out.printf("Error opening file: %s.", file.getAbsolutePath());
      e.printStackTrace();
    }

    return new CodeAnalysisResult(wordCount, characterCount, lineCount, 1, 0);
  }

  private long countWords(String line) {
    if (line == null || line.isBlank()) {
      return 0;
    }

    String[] words = line.trim().split("\\s+");
    return words.length;
  }
}
