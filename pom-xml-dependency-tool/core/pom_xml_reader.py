from .artifact import Artifact
from xml.dom import minidom


class PomXmlReader:
  """Reads pom.xml file."""

  def __init__(self, file_path: str, artifacts: dict):
    self.artifacts = artifacts
    self.read_xml_file(file_path)


  def retrieve_text_value(self, element, tag_name: str):
    return element.getElementsByTagName(tag_name)[0].childNodes[0].data


  def add_artifact_if_not_exists(self, artifact: Artifact):
    already_existing_artifact = self.artifacts.get(artifact.to_string())
    if already_existing_artifact is None:
      already_existing_artifact = artifact
      self.artifacts[artifact.to_string()] = already_existing_artifact

    return already_existing_artifact


  def process_dependencies(self, dependencies, root: Artifact):
    for dependency in dependencies:
      group_id = self.retrieve_text_value(dependency, "groupId")
      artifact_id = self.retrieve_text_value(dependency, "artifactId")
      dependency_artifact = self.add_artifact_if_not_exists(
          Artifact(group_id, artifact_id))
      dependency_artifact.add_usage(root)


  def read_xml_file(self, file_path: str):
    document = minidom.parse(file_path)
    group_id = self.retrieve_text_value(document, "groupId")
    artifact_id = self.retrieve_text_value(document, "artifactId")
    root_artifact = self.add_artifact_if_not_exists(
        Artifact(group_id, artifact_id))

    dependencies = (document.getElementsByTagName("dependencies")[0]
                    .getElementsByTagName("dependency"))
    self.process_dependencies(dependencies, root_artifact)
