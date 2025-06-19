package com.yk.tools.mta.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ArtifactParserTest {

  private final ArtifactParser parser = new ArtifactParser();

  private static Stream<Arguments> getValidLines() {
    return Stream.of(
        Arguments.of("[INFO] com.vk.training:jersey-example:war:1.0-SNAPSHOT", "com.vk.training", "jersey-example"),
        Arguments.of("com.vk.training:jersey-example:war:1.0-SNAPSHOT", "com.vk.training", "jersey-example"),
        Arguments.of("     com.vk.training:jersey-example:war:1.0-SNAPSHOT", "com.vk.training", "jersey-example"),
        Arguments.of("[INFO] org.glassfish.jersey.containers:jersey-container-servlet:jar:2.31",
            "org.glassfish.jersey.containers", "jersey-container-servlet"),
        Arguments.of("[INFO] org.glassfish.jersey.containers:jersey-container-servlet-core:jar:2.31",
            "org.glassfish.jersey.containers", "jersey-container-servlet-core"),
        Arguments.of("[INFO] org.glassfish.hk2.external:jakarta.inject:jar:2.6.1", "org.glassfish.hk2.external", "jakarta.inject"),
        Arguments.of("[INFO]   |    |  \\- org.glassfish.hk2.external:jakarta.inject:jar:2.6.1", "org.glassfish.hk2.external", "jakarta.inject"),
        Arguments.of("[INFO]   |    |   |   | \\+- org.glassfish.hk2.external:jakarta.inject:jar:2.6.1",
            "org.glassfish.hk2.external", "jakarta.inject")
    );
  }

  @MethodSource("getValidLines")
  @ParameterizedTest
  void parseValidLines(String line, String expectedGroupId, String expectedArtifactId) {
    Map<String, Artifact> artifactsMap = new HashMap<>();
    parser.parse(line, artifactsMap);
    assertThat(artifactsMap).hasSize(1);

    Artifact artifact = artifactsMap.get(expectedGroupId + ":" + expectedArtifactId);
    assertThat(artifact).isNotNull();
    assertThat(artifact.getGroupId()).isEqualTo(expectedGroupId);
    assertThat(artifact.getArtifactId()).isEqualTo(expectedArtifactId);
  }

  @Test
  void parseInvalidLines() {
    Map<String, Artifact> artifactsMap = new HashMap<>();

    parser.parse("Invalid line", artifactsMap);
    parser.parse("", artifactsMap);
    parser.parse(" ", artifactsMap);
    parser.parse("[INFO]", artifactsMap);
    parser.parse("[INFO] com.yk.training:jersey-example:w", artifactsMap);
    parser.parse("com.yk.training:jersey-example:w", artifactsMap);
    assertThat(artifactsMap).isEmpty();
  }
}