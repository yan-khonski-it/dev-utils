import os.path
import shutil

import utils
from utils import print_value

MAC_DIRECTORY = "/Library/Preferences/SystemConfiguration"
ALL_FILES = [
  "com.apple.airport.preferences.plist",
  "com.apple.network.identification.plist",
  "com.apple.wifi.message-tracer.plist",
  "NetworkInterface.plist",
  "preferences.plist"
]

FILES = [file for file in ALL_FILES if os.path.exists(os.path.join(MAC_DIRECTORY, file))]


def main():
  backup_directory_name = "backup__" + utils.get_current_datetime_as_string_with_random_suffix()
  backup_directory = os.path.join(MAC_DIRECTORY, backup_directory_name)
  utils.create_directory(backup_directory)

  for file in FILES:
    file_path = os.path.join(MAC_DIRECTORY, file)
    backup_file_path = os.path.join(backup_directory, file)
    print_value(f"Creating backup for file {file_path}, backup {backup_file_path}.")
    shutil.copy(file_path, backup_file_path)
    os.remove(file_path)


if __name__ == "__main__":
  main()
