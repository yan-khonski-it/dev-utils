package com.yk.tools.ca;

import static java.lang.String.format;

import com.yk.tools.ca.core.CodeAnalyser;
import com.yk.tools.ca.core.CodeConverter;
import java.io.File;

public class ArgumentParser {

  private static final int CODE_CONVERTER_ARG_SIZE = 6;
  private static final int CODE_ANALYSER_ARG_SIZE = 4;

  private final CodeConverter codeConverter = new CodeConverter();
  private final CodeAnalyser codeAnalyser = new CodeAnalyser();

  private CodeConverterArguments codeConverterArguments;
  private CodeAnalyserArguments codeAnalyserArguments;


  public ArgumentParser() {
  }

  public void parse(String[] args) {
    if (args.length == CODE_ANALYSER_ARG_SIZE) {
      parseCodeAnalyserArguments(args);
    } else if (args.length == CODE_CONVERTER_ARG_SIZE) {
      parseCodeConverterArguments(args);
    } else {
      throw new IllegalArgumentException("Invalid arguments provided. Cannot run any task.");
    }
  }

  public void runTask() {
    if (codeAnalyserArguments != null) {
      codeAnalyser.runCodeAnalysis(codeAnalyserArguments);
    } else if (codeConverterArguments != null) {
      codeConverter.runCodeConverter(codeConverterArguments);
    } else {
      throw new IllegalStateException("Invalid arguments provided. Cannot run any task.");
    }
  }

  private void parseCodeConverterArguments(String[] args) {
    if (!"-task".equals(args[0])) {
      throw new IllegalArgumentException(format("Invalid argument 1. Expected '-task', but received '%s'.", args[0]));
    }

    if (!"converter".equals(args[1])) {
      throw new IllegalArgumentException(format("Invalid argument 2. Expected 'converter', but received '%s'.", args[1]));
    }

    if (!"-inputDirectory".equals(args[2])) {
      throw new IllegalArgumentException(format("Invalid argument 3. Expected '-inputDirectory', but received '%s'.", args[2]));
    }

    File inputDirectory = parseInputDirectory(args[3]);

    if (!"-outputFile".equals(args[4])) {
      throw new IllegalArgumentException(format("Invalid argument 5. Expected '-outputFile', but received '%s'.", args[4]));
    }

    String outputFileArg = args[5];
    if (outputFileArg == null || outputFileArg.isBlank()) {
      throw new IllegalArgumentException("Output file argument cannot be empty or blank.");
    }

    outputFileArg = outputFileArg.trim().replaceAll("'", ""); // Remove quotes
    File outputFile = new File(outputFileArg);
    if (outputFile.exists()) {
      throw new IllegalArgumentException(format("Output file '%s' already exists.", outputFileArg));
    }

    this.codeConverterArguments = new CodeConverterArguments(inputDirectory, outputFile);
  }

  private void parseCodeAnalyserArguments(String[] args) {
    if (!"-task".equals(args[0])) {
      throw new IllegalArgumentException(format("Invalid argument 1. Expected '-task', but received '%s'.", args[0]));
    }

    if (!"analyser".equals(args[1])) {
      throw new IllegalArgumentException(format("Invalid argument 2. Expected 'analyser', but received '%s'.", args[1]));
    }

    if (!"-inputDirectory".equals(args[2])) {
      throw new IllegalArgumentException(format("Invalid argument 3. Expected '-inputDirectory', but received '%s'.", args[2]));
    }

    File inputDirectory = parseInputDirectory(args[3]);
    this.codeAnalyserArguments = new CodeAnalyserArguments(inputDirectory);
  }

  private File parseInputDirectory(String inputDirectory) {
    if (inputDirectory == null || inputDirectory.isBlank()) {
      throw new IllegalArgumentException("Input directory cannot be empty or blank.");
    }

    inputDirectory = inputDirectory.trim().replaceAll("'", "").replaceAll("\"", ""); // Remove quotes
    File inputDirectoryFile = new File(inputDirectory);
    if (!inputDirectoryFile.exists()) {
      throw new IllegalArgumentException(format("Input directory '%s' does not exist.", inputDirectory));
    }

    return inputDirectoryFile;
  }
}
