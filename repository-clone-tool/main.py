import subprocess
import time
import json


base_directory = "C:/Dev/test-folder"
file_path_separator = "/"


def parse_input(filename: str) -> list:
  content = ""
  with open(filename, "r") as input_file:
    content = input_file.read()

  return json.loads(content)


def build_repository_path(repository_directory: str, repository_name: str) -> str:
  return base_directory + file_path_separator +repository_directory + file_path_separator + repository_name


def retrieve_repository_name(repository_url: str) -> str:
  repository_name = repository_url.split("/")[-1]
  if repository_name.endswith(".git"):
    repository_name = repository_name[:-4]

  return repository_name


def clone_repository(repository_directory: str, repository_url: str) -> None:
  repository_name = retrieve_repository_name(repository_url)
  repository_path = build_repository_path(repository_directory, repository_name)
  print("Cloning repository: [" + repository_url + "]" +
        " into directory: [" + repository_path + "] .")

  process = subprocess.Popen([
    "git", "clone", repository_url, repository_path
  ],
      stdout=subprocess.PIPE,
      cwd=base_directory)
  output = process.communicate()[0]
  print(output)


def clone_repositories(directory: str, repositories: list) -> None:
  print("Cloning repositories to directory: [" + directory + "] .")
  for url in repositories:
    clone_repository(directory, url)


def main():
  print("Cloning repositories into base directory: [" + base_directory + "] .")
  start = time.time()

  directories_list = parse_input("./repositoriesStructure.json")
  for directory in directories_list:
    clone_repositories(directory["directory"], directory["repositories"])

  end = time.time()
  total_time = end - start
  print("Cloning repositories took: [" + str(total_time) + "] seconds.")


if __name__ == "__main__":
  main()