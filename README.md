# Useful staff to automate my work
Here are some tools, I use to automate my work with repositories / code.

## Prerequisites
python 3

## Clone the repo
```shell
git clone https://github.com/yan-khonski-it/dev-utils.git
```

## repository clone tool
https://github.com/yan-khonski-it/dev-utils/tree/master/repository-clone-tool

It clones all repositories from the list of remote repositories (see `core/config.py`).

How can it help you? For example, you want to perform code search, finding the code patterns in multiple repositories.
When you have all the repositories cloned locally, you can build an index, and then perform searches.


## repository update tool

https://github.com/yan-khonski-it/dev-utils/tree/master/repository-update-tool

Given a list of repositories on local machine, this tool will update them (for each repository, it will call `git pull`).
Feel free to update it, for example, for each repository, get list of local branches. for each branch, checkout it and call `git pull`.


## Installation
### Create a virtual environment in the directory of the project

<details>
  <summary>Create a virtual environment on Windows</summary>

#### Windows
https://docs.python.org/3/library/venv.html

Install virtual environment if not installed
```commandline
pip install virtualenv
```

```commandline
python -m venv virtual_environment 
```

Activate the virtual environment
```text
.\virtual_environment\Scripts\activate
```

The output will be:
```text
(virtual_environment) PS {PATH}\dev-utils>
```

To deactivate the virtual environment
https://stackoverflow.com/questions/990754/how-to-leave-exit-deactivate-a-python-virtualenv
```commandline
deactivate
```
</details>

<details>
  <summary>Create a virtual environment on Mac</summary>

#### Mac
https://mnzel.medium.com/how-to-activate-python-venv-on-a-mac-a8fa1c3cb511

Install virtual environment if not installed
```shell
python3 -m pip install --user virtualenv
```

Create a virtual environment
```shell
python3 -m venv virtual_environment
```

Activate the virtual environment
```shell
source virtual_environment/bin/activate
```

To deactivate the virtual environment
https://stackoverflow.com/questions/990754/how-to-leave-exit-deactivate-a-python-virtualenv
```shell
deactivate
```
</details>



### To install dependencies to the virtual environment
```commandline
pip install -r ./requirements.txt
```