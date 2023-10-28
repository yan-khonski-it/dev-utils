import subprocess
import os
from core.system_parameters import SystemParameters
from core.utils import convert_list_to_string

# Set it based on your system
file_path_Separator = "\\"

# Define major parameters system dependent
main_directory = "C:\\Dev\\workspaces\\utils"
individual_repositories = ["pmd-java-14",
                           "kafka-compose",
                           "spring-cloud-config-demo"]

# List of directories that have nested repositories.
# We want to update all of them
list_of_directories_with_repositories = ["C:\Dev\workspaces\projects"]

system_parameters = SystemParameters(main_directory, file_path_Separator)


def update_repository_by_name(repository_name):
  repository_directory = (system_parameters.main_directory +
                          system_parameters.file_path_separator +
                          repository_name)

  update_repository_by_path(repository_directory)


def update_repository_by_path(repository_path):
  print("Updating repository: [" + repository_path + "] .")
  process = subprocess.Popen(
      ["git", "pull"],
      stdout=subprocess.PIPE,
      cwd=repository_path)
  output = process.communicate()[0]
  print(output)


def get_list_of_child_directories(directory):
  return [f.path for f in os.scandir(directory) if f.is_dir()]


def update_repositories_in_directory(directory_with_repositories):
  children = get_list_of_child_directories(directory_with_repositories)
  for child in children:
    update_repository_by_path(child)


def main():
  print("Updating individual repositories:\n" +
        convert_list_to_string(individual_repositories) + "\n")
  for repository in individual_repositories:
    update_repository_by_name(repository)

  print("Updating list of directories containing repositories:\n" +
        convert_list_to_string(list_of_directories_with_repositories) + "\n")

  for directory_with_repositories in list_of_directories_with_repositories:
    update_repositories_in_directory(directory_with_repositories)


if __name__ == "__main__":
  main()
