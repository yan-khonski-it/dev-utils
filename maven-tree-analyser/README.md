# maven-tree-analyser

Analyses maven dependency tree, which
displays the full dependency graph, showing where multiple versions of the same artifact appear.

If a dependency occurs in multiple places with different versions,
Maven selects the version closest to your project (i.e., the shortest path from the root project POM). This is called the nearest-wins strategy.

If two paths to the same dependency have the same length, Maven uses the first encountered in the resolution process.

Now, you may want to exclude dependencies with different versions.
Usually you want to keep the latest version, but only if it does not break other dependencies.

To find the version conflicts, this tool analyses output of mvn dependency:tree and returns list of dependencies that have multiple versions.

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