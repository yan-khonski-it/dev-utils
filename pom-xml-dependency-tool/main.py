import os
import time
from core.pom_xml_reader import PomXmlReader


file_path_Separator = "\\"
base_directory = "C:\\Dev\\workspaces\\test-directory"
intput = "org.apache.logging.log4j:log4j-core"

all_artifacts = {}
found_dependencies = []


def append_artifacts_from_pom_xml(pom_xml_path):
  print("Searching in file: [" + pom_xml_path + "] .")
  PomXmlReader(pom_xml_path, all_artifacts)


def walk_directory(directory):
  children = os.listdir(directory)
  for child in children:
    child_path = directory + file_path_Separator + child

    # Exclude .git and .idea, and other non code directories
    if os.path.isdir(child_path) and not child.startswith("."):
      walk_directory(child_path)
    elif os.path.isfile(child_path) and child == "pom.xml":
      append_artifacts_from_pom_xml(child_path)


def search_artifact(artifact_name: str):
  artifact = all_artifacts.get(artifact_name)
  if artifact is None:
    return

  if artifact.visited:
    return

  found_dependencies.append(artifact_name)
  artifact.visited = True

  for usage in artifact.usages:
    search_artifact(usage.to_string())


def main():
  print("Searching text "
        "[" + intput + "] " +
        "in repositories. Base directory: "
        "[" + base_directory + "].")
  start = time.time()

  walk_directory(base_directory)
  search_artifact(intput)

  # Remove the first element, because it is the searched artifact itself
  if len(found_dependencies) > 0:
    found_dependencies.pop(0)

  print("\n\nFound modules / projects depending on [" + intput + "]:\n")
  print(found_dependencies)

  end = time.time()
  total_time = end - start
  print("Searching took: [" + str(total_time) + "] seconds.")


if __name__ == "__main__":
  main()
