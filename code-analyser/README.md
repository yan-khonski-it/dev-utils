# Code analyser

I use this tool for small repositories. It builds a context file that I can upload to a Language Model (LLM) and ask questions.

By default, it will run code analysis, telling how many files, 
lines and words and characters in the code, so you can estimate if LLM will have enough context size.

Then you can run code converter, which will convert the source code into a single file.


## Running

Clone repository
```commandline
git clone https://github.com/yan-khonski-it/dev-utils
```

Navigate to code-analyser directory:
```commandline
cd ./code-analyser
```

Build the project:
```commandline
mvn clean install
```

Run the project:
```commandline
java -cp ./target/code-analyser-1.0-SNAPSHOT.jar com.yk.tools.ca.Main
```

## Arguments:
If you want to run code analyser, the use these arguments:
```commandline
-task analyser -inputDirectory <INPUT_DIRECTORY>
```

Example
```commandline
java -cp ./target/code-analyser-1.0-SNAPSHOT.jar com.yk.tools.ca.Main -task analyser -inputDirectory 'C:\Dev\workspaces\tools\pmd-java-21\src\main\java'
```

If you want to run code converter, then use these arguments:
```commandline
-task converter -inputDirectory <INPUT_DIRECTORY> -outputFile <OUTPUT_FILE_LOCATION>
```

For example, the whole command will be:
```commandline
java -cp ./target/code-analyser-1.0-SNAPSHOT.jar com.yk.tools.ca.Main -task converter -inputDirectory 'C:\Dev\workspaces\tools\pmd-java-21\src\main\java' -outputFile 'C:\Dev\pmd-java-21.converted.txt'
```

The you can upload such code into LLM application, for example, ChatGPT, and ask questions. For example:
```text
I have java code in the format as single file
# package name - separates packages
## File name or class name - classes in the packge above.

Could you please, review this code
```

Example using MCP client for code analysis and MCP server for code access:

https://github.com/yan-khonski-it/ai-labs/tree/main/mcp-example#mcp-example