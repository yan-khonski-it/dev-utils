import subprocess
from core.directory_with_repositories import DirectoryWithRepositories

# Set these directories with repositories,
# so for each directory the script will clone all repositories in it
baseDirectory = "C:\\Dev\\workspaces\\test-directory"
file_path_Separator = "\\"

# These repositories are just an example. Feel free to choose yours.
directories_to_clone_repositories = [
  DirectoryWithRepositories("ByteByteGoHq", [
    "https://github.com/ByteByteGoHq/system-design-101",
    "https://github.com/ByteByteGoHq/ml-bytebytego"
  ]),

  DirectoryWithRepositories("yangshun", [
    "https://github.com/yangshun/front-end-interview-handbook",
    "https://github.com/yangshun/tech-interview-handbook"
  ]),

  DirectoryWithRepositories("yan-khonski-it", [
    "https://github.com/yan-khonski-it/pmd-java-14",
    "https://github.com/yan-khonski-it/java-17-pmd"
  ])
]


def build_repository_path(repository_directory, repository_name):
  return (baseDirectory + file_path_Separator +
          repository_directory + file_path_Separator + repository_name)


def clone_repository(repository_directory, repository_url):
  repository_name = repository_url.split("/")[-1]
  repository_path = build_repository_path(repository_directory, repository_name)
  print("Cloning repository: [" + repository_url + "]" +
        " into directory: [" + repository_path + "] .")

  process = subprocess.Popen([
    "git", "clone", repository_url, repository_path
  ],
      stdout=subprocess.PIPE,
      cwd=baseDirectory)
  output = process.communicate()[0]
  print(output)


def clone_repositories(directory_with_repositories):
  repositories_directory = directory_with_repositories.repositories_directory
  print("Cloning repositories to directory: [" + repositories_directory + "] .")
  for url in directory_with_repositories.urls:
    clone_repository(repositories_directory, url)


def main():
  print("Cloning repositories into base directory: [" + baseDirectory + "] .")
  for directory in directories_to_clone_repositories:
    clone_repositories(directory)


if __name__ == "__main__":
  main()