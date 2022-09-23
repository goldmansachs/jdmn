# flake8: noqa
import os
import re
import collections
from typing import List


def countMissingAttributes(groups: List[List[str]]) -> None:
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


def findMissingFunctions(groups: List[List[str]]) -> None:
    errors = collections.Counter()
    for group in groups:
        found = False
        for line in group:
            if "return self." in line:
                found = True
                hook = line.index("return self.")
                message = line[hook + 12:].strip()
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
groups = aggregateErrors(fileName)
# countMissingAttributes(fileName)
findMissingFunctions(groups)

# Stats
# 39 dateTimeLib.date(*args)
# 38 dateTimeLib.time(*args)
# 34 dateTimeLib.dateAndTime(*args)
# 31 stringLib.replace(input, pattern, replacement, flags)
# 31 durationLib.yearsAndMonthsDuration(from_, to)
# 14 rangeLib.during(arg1, arg2)
# 14 rangeLib.includes(arg1, arg2)
# 14 rangeLib.overlaps(range1, range2)
# 13 stringLib.substringAfter(string, match)
# 13 rangeLib.after(arg1, arg2)
# 12 stringLib.substring(string, self.numberLib.toNumber(startPosition), self.numberLib.toNumber(length))
# 12 rangeLib.before(arg1, arg2)
# 11 stringLib.substringBefore(string, match)
# 10 stringLib.upperCase(string)
# 10 rangeLib.startedBy(arg1, arg2)
# 10 rangeLib.starts(arg1, arg2)
# 9 rangeType.rangeEqual(range1, range2)
# 9 rangeLib.overlapsAfter(range1, range2)
# 9 rangeLib.overlapsBefore(range1, range2)
# 7 rangeLib.finishedBy(arg1, arg2)
# 7 rangeLib.finishes(arg1, arg2)
# 6 numberLib.floor(n, scale)
# 5 rangeLib.coincides(arg1, arg2)
# 4 listLib.append(list, *items)
# 4 durationLib.duration(from_)
# 4 rangeLib.metBy(range1, range2)
# 4 rangeLib.meets(range1, range2)
# 3 listLib.insertBefore(list, self.intValue(position), newItem)
# 2 stringLib.endsWith(string, match)
# 2 listLib.remove(list, self.intValue(position))
# 2 numberLib.mean(*args)
# 1 listLib.reverse(list)
# 1 listLib.union(*lists)
# 1 listLib.distinctValues(list)