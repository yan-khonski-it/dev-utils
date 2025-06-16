from datetime import datetime
import random
import os
import threading


def get_current_datetime_as_string() -> str:
  """
  Returns the current datetime as a string.
  """
  now = datetime.now()
  return str(now.strftime("%Y-%m-%dT%H:%M:%S"))


def get_current_datetime_as_string_with_random_suffix() -> str:
  now_str = get_current_datetime_as_string()
  return now_str + "--" + str(random.randint(0, 9))


def create_directory(directory_path: str) -> None:
  os.makedirs(directory_path)


def create_directory_if_not_exists(directory_path: str) -> None:
  if not os.path.exists(directory_path):
    create_directory(directory_path)


def print_value(value: str) -> None:
  now_str = get_current_datetime_as_string()
  current_thread_name = threading.current_thread().name
  print(now_str + " | " + current_thread_name + " | " + value)