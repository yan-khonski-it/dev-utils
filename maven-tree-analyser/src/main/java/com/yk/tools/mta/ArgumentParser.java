package com.yk.tools.mta;

import static java.lang.String.format;

import com.yk.tools.mta.core.MtaAnalyser;
import java.io.File;

public class ArgumentParser {

  private static final int ARG_SIZE = 2;

  private final MtaAnalyser mtaAnalyser = new MtaAnalyser();

  private File input;


  public ArgumentParser() {
  }

  public void parse(String[] args) {
    if (args.length == ARG_SIZE) {
      parseMtaArguments(args);
    } else if (args == null || args.length == 0) {
      args = new String[ARG_SIZE];
      args[0] = "-input";
      args[1] = "'C:\\Dev\\tree-result.txt'";
      parseMtaArguments(args);
    } else {
      throw new IllegalArgumentException("Invalid arguments provided. Cannot run tree analyser.");
    }
  }

  public void runTask() {
    if (input != null) {
      mtaAnalyser.run(input);
    } else {
      throw new IllegalStateException("Invalid arguments provided. Cannot run any task.");
    }
  }

  private void parseMtaArguments(String[] args) {
    if (!"-input".equals(args[0])) {
      throw new IllegalArgumentException(format("Invalid argument 0. Expected '-input', but received '%s'.", args[0]));
    }

    this.input = parseInputFile(args[1]);
  }

  private File parseInputFile(String inputFileArgument) {
    if (inputFileArgument == null || inputFileArgument.isBlank()) {
      throw new IllegalArgumentException("Input file cannot be empty or blank.");
    }

    inputFileArgument = inputFileArgument.trim().replaceAll("'", "").replaceAll("\"", ""); // Remove quotes
    File inputFile = new File(inputFileArgument);
    if (!inputFile.exists()) {
      throw new IllegalArgumentException(format("Input file '%s' does not exist.", inputFileArgument));
    }

    return inputFile;
  }
}
