import subprocess
import os
import time
import json
from core.system_parameters import SystemParameters
from core.utils import convert_list_to_string

# Set it based on your system
file_path_separator = "\\"

# Define major parameters system dependent
main_directory = "C:\\Dev\\workspaces\\tools"
system_parameters = SystemParameters(main_directory, file_path_separator)


def parse_input(filename: str) -> list:
  content = ""
  with open(filename) as input_file:
    content = input_file.read()

  return json.loads(content)


def parse_directories(input_file: str) -> list:
  list_of_directories_with_repositories = parse_input(input_file)
  directories = []
  for directory in list_of_directories_with_repositories:
    directories.append(main_directory + file_path_separator + directory["directory"])

  return directories


def update_repository_by_path(repository_path: str) -> None:
  print("Updating repository: [" + repository_path + "] .")
  process = subprocess.Popen(
      ["git", "pull"],
      stdout=subprocess.PIPE,
      cwd=repository_path)
  output = process.communicate()[0]
  print(output)


def check_repository_branches(repository_path: str) -> None:
  process = subprocess.Popen(
      ["git", "branch"],
      stdout = subprocess.PIPE,
      cwd = repository_path
  )

  output = process.communicate()[0]
  if len(output) > 12: # 12 is the lenght of "*  master\n "
    print("Repository with more branches: [ " + repository_path + "]. Branches are")
    print(output)


def get_list_of_child_directories(directory: str) -> list:
  return [f.path for f in os.scandir(directory) if f.is_dir()]


def update_repositories_in_directory(directory_with_repositories: str) -> None:
  children = get_list_of_child_directories(directory_with_repositories)
  for child in children:
    update_repository_by_path(child)


def check_repositories_branches_in_directory(directory_with_repositories: dict) -> None:
  children = get_list_of_child_directories(directory_with_repositories)
  for child in children:
    check_repository_branches(child)


def main():
  start = time.time()

  directories = parse_directories("./repositoriesStructure.json")
  print("Updating list of directories containing repositories\n" + convert_list_to_string(directories) + "\n")

  for directory_with_repositories in directories:
    update_repositories_in_directory(directory_with_repositories)

  end = time.time()
  total_time = end - start
  print("Updating repositories took: [" + str(total_time) + "] seconds.")

  print("Checking repositories branches.")
  for directory_with_repositories in directories:
    check_repositories_branches_in_directory(directory_with_repositories)


if __name__ == "__main__":
  main()
