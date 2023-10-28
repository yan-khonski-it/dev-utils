# pom-xml-dependency-tool

Finds all maven projects that use provided as an argument dependency.

For example, a library `A` is used by a library `B`, and a library `C`. While a project `D` uses the library `C`.
There are more projects and libraries. However, when we ask for all projects or libraries that depend on `A`, we want to get `B`, `C`, `D`.

To run it locally, run
```shell
python3 ./pom-xml-dependency-tool/main.py
```