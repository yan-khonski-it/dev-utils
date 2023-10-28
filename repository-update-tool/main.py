import subprocess
import os
from core.system_parameters import SystemParameters
from core.utils import convert_list_to_string

# Define major parameters system dependent
main_directory = "C:\\Dev\\workspaces\\utils"
repositories_list = ["pmd-java-14",
                     "kafka-compose",
                     "spring-cloud-config-demo"]
file_path_Separator = "\\"
# List of folders that have nested repositories to be updated, for example a folder with libraries
folder_with_repositories_list = ["C:\Dev\workspaces\projects"]

system_parameters = SystemParameters(main_directory, file_path_Separator)


def update_repository_by_name(repository_name):
  repository_directory = system_parameters.main_directory + system_parameters.file_path_separator + repository_name
  update_repository_by_path(repository_directory)


def update_repository_by_path(repository_path):
  print("Updating repository: [" + repository_path + "] .")
  process = subprocess.Popen(["git", "pull"], stdout=subprocess.PIPE, cwd=repository_path)
  output = process.communicate()[0]
  print(output)


def update_folder_repositories(folder_with_repositories):
  subfolders = [f.path for f in os.scandir(folder_with_repositories) if f.is_dir()]
  for sub_folder in subfolders:
    update_repository_by_path(sub_folder)


def main():
  print("Updating repositories:\n" + convert_list_to_string(repositories_list) + "\n")
  for repository in repositories_list:
    update_repository_by_name(repository)

  print("Updating folders:\n" + convert_list_to_string(folder_with_repositories_list) + "\n")
  for folder_with_repositories in folder_with_repositories_list:
    update_folder_repositories(folder_with_repositories)


if __name__ == "__main__":
  main()
