package com.yk.tools.mta.core;

import java.util.Comparator;

/**
 * Compares two {@link Artifact} objects.
 * <p>
 * First we want artifacts that have the most versions (sort by versions in descending order).
 * <p>
 * Then we want artifacts sorted by group id and artifact id in ascending order.
 */
public class ArtifactComparator implements Comparator<Artifact> {

  @Override
  public int compare(Artifact o1, Artifact o2) {
    int versions1 = o1.getVersionsSize();
    int versions2 = o2.getVersionsSize();
    if (versions1 != versions2) {
      return versions2 - versions1;
    }

    String group1 = o1.getGroupId();
    String group2 = o2.getGroupId();
    int groupIdComparison = group1.compareTo(group2);

    if (groupIdComparison != 0) {
      return groupIdComparison;
    }

    return o1.getArtifactId().compareTo(o2.getArtifactId());
  }
}
