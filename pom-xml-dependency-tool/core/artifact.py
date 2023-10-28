class Artifact:
  """
  Represents maven artifact with group id and artifact id;
  additionally, it contains all other artifacts that use it.
  visited flag is used for graph traversal,
  so we do not visit same artifact more than once.
  """

  def __init__(self, group_id: str, artifact_id: str):
    self.group_id = group_id
    self.artifact_id = artifact_id
    self.usages = []
    self.visited = False


  def to_string(self):
    return self.group_id + ":" + self.artifact_id


  def add_usage(self, usage):
    self.usages.append(usage)