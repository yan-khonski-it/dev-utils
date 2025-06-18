# maven-tree-analyser

Analyses maven dependency tree, which
displays the full dependency graph, showing where multiple versions of the same artifact appear or similar artifacts*.

If a dependency occurs in multiple places with different versions,
Maven selects the version closest to your project (i.e., the shortest path from the root project POM). This is called the nearest-wins strategy.

If two paths to the same dependency have the same length, Maven uses the first encountered in the resolution process.

Now, you may want to exclude dependencies with different versions.
Usually you want to keep the latest version, but only if it does not break other dependencies.

To find the version conflicts, this tool analyses output of mvn dependency:tree and returns list of dependencies that have multiple versions.

## *Similar artifacts
Artifacts that have the same group id, and artifact id only have different suffixes, containing their versions.
We consider them similar artifacts, for example, if these artifacts ids have the same group id:
```text
commons-lang
commons-lang3
commons-lang4
commons-lang-1
commons-lang-2
```

In that case, this tool will point out the similar artifacts as well.

## Running it.

Build the project:
```commandline
mvn clean install
```

In the project that you want to get analysis, run:
```commandline
mvn dependecy:tree
```

Copy the output into input file.

Run this tool:
```commandline
java -cp ./target/maven-tree-analyser-1.0-SNAPSHOT.jar com.yk.tools.mta.Main  -input 'C:\Dev\tree-result.txt'
```

Output:
```text
Found artifacts with different versions:
Artifact{coordinates='com.fasterxml.jackson.core:jackson-databind', versions=2.8.1, 2.10.1}

Found similar artifacts pairs: 
Artifact{coordinates='org.apache.commons:commons-collections', versions=3.2.2} -> Artifact{coordinates='org.apache.commons:commons-collections4', versions=4.4}
Artifact{coordinates='org.apache.commons:commons-lang', versions=2.17.0} -> Artifact{coordinates='org.apache.commons:commons-lang3', versions=3.17.0}
```