# Useful staff to automate my work
Here are some tools, I use to automate my work with repositories / code.

## Prerequisites
python 3

## Clone the repo
```shell
git clone https://github.com/yan-khonski-it/dev-utils.git
```

## Automatic repository clone tool
It clones all repositories from the list of remote repositories (see `core/config.py`).

How can it help you? For example, you want to perform code search, finding the code patterns in multiple repositories.
When you have all the repositories cloned locally, you can build an index, and then perform searches.


## Automatic repository update tool
Given a list of repositories on local machine, this tool will update them (for each repository, it will call `git pull`).
Feel free to update it, for example, for each repository, get list of local branches. for each branch, checkout it and call `git pull`.


## Installation
### Create a virtual environment in the directory of the project
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
```commandline
.\virtual_environment\Scripts\activate
```

The output will be:
```commandline
(virtual_environment) PS {PATH}\dev-utils>
```

To deactivate the virtual environment
https://stackoverflow.com/questions/990754/how-to-leave-exit-deactivate-a-python-virtualenv
```commandline
deactivate
```

To install dependencies to the virtual environment
```commandline
pip install -r .\requirements.txt
```