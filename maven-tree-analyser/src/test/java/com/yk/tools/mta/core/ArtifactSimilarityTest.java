package com.yk.tools.mta.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ArtifactSimilarityTest {

  private final ArtifactSimilarity similarity = new ArtifactSimilarity();

  private static Stream<Arguments> getSimilarArtifacts() {
    return Stream.of(
        Arguments.of(
            new Artifact("com.vk.training", "jersey-example-1"),
            new Artifact("com.vk.training", "jersey-example-2")
        ),
        Arguments.of(
            new Artifact("org.glassfish.jersey.containers", "jersey-container-servlet-1"),
            new Artifact("org.glassfish.jersey.containers", "jersey-container-servlet2")
        ),
        Arguments.of(
            new Artifact("org.glassfish.jersey.containers", "jersey-container-servlet-core1"),
            new Artifact("org.glassfish.jersey.containers", "jersey-container-servlet-core")
        ),
        Arguments.of(
            new Artifact("org.glassfish.hk2.external", "jakarta.inject2"),
            new Artifact("org.glassfish.hk2.external", "jakarta.inject3")
        )
    );
  }

  private static Stream<Arguments> getNotSimilarArtifacts() {
    return Stream.of(
        Arguments.of(
            new Artifact("com.vk.training", "jersey-example-1"),
            new Artifact("com.vk", "jersey-example-1")
        ),
        Arguments.of(
            new Artifact("org.glassfish.jersey.containers", "jersey-container-servlet-1a"),
            new Artifact("org.glassfish.jersey.containers", "jersey-container-servlet-1b")
        ),
        Arguments.of(
            new Artifact("org.glassfish.jersey.containers", "jersey-container-servlet.core1"),
            new Artifact("org.glassfish.jersey.containers", "jersey-container-servlet")
        ),
        Arguments.of(
            new Artifact("org.glassfish.hk2.external", "jakarta.inject.2"),
            new Artifact("org.glassfish.hk2.external", "jakarta.inject.3")
        )
    );
  }

  @MethodSource("getSimilarArtifacts")
  @ParameterizedTest
  void testSimilar(Artifact a, Artifact b) {
    boolean similar = similarity.similar(a, b);
    assertTrue(similar, "Artifacts should be similar: " + a + " and " + b);
  }

  @MethodSource("getNotSimilarArtifacts")
  @ParameterizedTest
  void testNotSimilar(Artifact a, Artifact b) {
    boolean similar = similarity.similar(a, b);
    assertFalse(similar, "Artifacts should not be similar: " + a + " and " + b);
  }
}
