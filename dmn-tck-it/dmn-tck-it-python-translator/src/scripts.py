# flake8: noqa
import os
import re
import collections
from typing import List


def countMissingAttributes(fileName_: str) -> None:
    groups: List[List[str]] = aggregateErrors(fileName_)

    errors = collections.Counter()
    for group in groups:
        found = False
        for line in group:
            if re.search("^[a-zA-Z]+Error", line) is not None:
                found = True
                if line.startswith("AttributeError: "):
                    hook = line.index("object has no attribute")
                    message = "AttributeError: " + line[hook:]
                    errors[message] += 1
        if not found:
            print("ERROR {}".format(group))

    print("Stats ")
    for e in sorted(errors.items(), key=lambda x: x[1], reverse=True):
        print(e[1], e[0].strip())


def aggregateErrors(fileName: str) -> List[List[str]]:
    file1 = open(fileName, "r", encoding='utf-8')
    lines = file1.readlines()

    group = []
    currentError = []
    for line in lines:
        if line.__contains__("[   ERROR] Exception caught in"):
            # start error
            if len(currentError) != 0:
                group.append(currentError)
            currentError = []
        currentError.append(line)

    return group


os.path.dirname(__file__)
parent = os.path.dirname(os.path.dirname(__file__))

fileName = parent + "/target/generated-test-sources/pytest.log"
# fileName = parent + "/src/pytest-1.log"
countMissingAttributes(fileName)
