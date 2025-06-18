package com.yk.tools.mta;

public class Main {

  private static final ArgumentParser ARGUMENT_PARSER = new ArgumentParser();

  public static void main(String[] args) {
    ARGUMENT_PARSER.parse(args);
    ARGUMENT_PARSER.runTask();
  }
}