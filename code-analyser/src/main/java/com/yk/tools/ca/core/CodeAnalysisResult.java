package com.yk.tools.ca.core;

/**
 * Represents result from code analyser.
 */
public class CodeAnalysisResult {

  private long wordCount;
  private long characterCount;
  private long lineCount;
  private long fileCount;
  private long directoryCount;

  public CodeAnalysisResult() {
    this.wordCount = 0;
    this.characterCount = 0;
    this.lineCount = 0;
    this.fileCount = 0;
    this.directoryCount = 0;
  }

  public CodeAnalysisResult(long wordCount, long characterCount, long lineCount, long fileCount, long directoryCount) {
    this.wordCount = wordCount;
    this.characterCount = characterCount;
    this.lineCount = lineCount;
    this.fileCount = fileCount;
    this.directoryCount = directoryCount;
  }

  public void add(CodeAnalysisResult codeAnalysisResult) {
    this.wordCount += codeAnalysisResult.wordCount;
    this.characterCount += codeAnalysisResult.characterCount;
    this.lineCount += codeAnalysisResult.lineCount;
    this.fileCount += codeAnalysisResult.fileCount;
    this.directoryCount += codeAnalysisResult.directoryCount;
  }

  public void incrementDirectoryCount() {
    this.directoryCount++;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{\n" +
        "wordCount: " + wordCount + ",\n" +
        "characterCount: " + characterCount + ",\n" +
        "lineCount: " + lineCount + ",\n" +
        "fileCount: " + fileCount + ",\n" +
        "directoryCount: " + directoryCount + "\n" +
        "}\n";
  }
}
