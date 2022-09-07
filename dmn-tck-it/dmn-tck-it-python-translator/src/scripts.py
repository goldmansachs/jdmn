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

fileName = parent + "/target/generated-test-sources/tck/pytest.log"
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
# 13 stringLib.substringAfter(string, match)
# 12 stringLib.substring(string, self.numberLib.toNumber(startPosition), self.numberLib.toNumber(length))
# 11 stringLib.substringBefore(string, match)
# 10 stringLib.upperCase(string)
# 6 numberLib.floor(n, scale)
# 4 listLib.append(list, *items)
# 4 durationLib.duration(from_)
# 3 listLib.insertBefore(list, self.intValue(position), newItem)
# 2 stringLib.endsWith(string, match)
# 2 listLib.remove(list, self.intValue(position))
# 2 numberLib.mean(*args)
# 2 rangeLib.after(arg1, arg2)
# 2 rangeLib.before(arg1, arg2)
# 2 rangeLib.coincides(arg1, arg2)
# 1 listLib.reverse(list)
# 1 listLib.union(*lists)
# 1 listLib.distinctValues(list)
# 1 contextEqual(com.gs.dmn.runtime.Context(), com.gs.dmn.runtime.Context())
# 1 contextEqual(com.gs.dmn.runtime.Context().add("foo", "bar").add("bar", "baz"), com.gs.dmn.runtime.Context().add("foo", "bar").add("bar", "baz"))
# 1 contextEqual(com.gs.dmn.runtime.Context().add("foo", "bar").add("bar", "baz"), com.gs.dmn.runtime.Context().add("bar", "baz").add("foo", "bar"))
# 1 contextEqual(com.gs.dmn.runtime.Context().add("foo", "bar"), com.gs.dmn.runtime.Context().add("foo", "bar"))
# 1 contextEqual(com.gs.dmn.runtime.Context().add("foo", "bar"), com.gs.dmn.runtime.Context().add("foo", "baz"))
# 1 contextEqual(com.gs.dmn.runtime.Context(), None)
# 1 listEqual(self.asList(self.number("1"), self.number("2"), com.gs.dmn.runtime.Context().add("a", self.asList(self.number("3"), self.number("4")))), self.asList(self.number("1"), self.number("2"), com.gs.dmn.runtime.Context().add("a", self.asList(self.number("3"), self.number("4")))))
# 1 listEqual(self.asList(self.number("1"), self.number("2"), com.gs.dmn.runtime.Context().add("a", self.asList(self.number("3"), self.number("4")))), self.asList(self.number("1"), self.number("2"), com.gs.dmn.runtime.Context().add("a", self.asList(self.number("3"), self.number("4"))).add("b", "foo")))
# 1 contextEqual(com.gs.dmn.runtime.Context().add("a", com.gs.dmn.runtime.Context().add("b", "foo").add("c", self.asList(self.number("1"), self.number("2")))), com.gs.dmn.runtime.Context().add("a", com.gs.dmn.runtime.Context().add("b", "foo").add("c", self.asList(self.number("1"), self.number("2")))))
# 1 contextEqual(com.gs.dmn.runtime.Context().add("a", com.gs.dmn.runtime.Context().add("c", "bar").add("b", "foo")), com.gs.dmn.runtime.Context().add("a", com.gs.dmn.runtime.Context().add("b", "foo").add("c", "bar")))
# 1 contextEqual(com.gs.dmn.runtime.Context().add("a", com.gs.dmn.runtime.Context().add("b", "foo").add("c", self.asList(self.number("1"), self.number("2")))), com.gs.dmn.runtime.Context().add("a", com.gs.dmn.runtime.Context().add("b", "foo").add("c", self.asList(self.number("2"), self.number("1")))))
# 1 contextEqual(self.elementAt(self.asList(com.gs.dmn.runtime.Context().add("a", "foo")), self.number("1")), com.gs.dmn.runtime.Context().add("a", "foo"))
# 1 rangeEqual(jdmn.runtime.Range.Range(true, self.number("1"), true, self.number("10")), jdmn.runtime.Range.Range(true, self.number("1"), true, self.number("10")))
# 1 rangeEqual(jdmn.runtime.Range.Range(true, self.number("1"), true, self.number("2")), jdmn.runtime.Range.Range(true, self.number("1"), true, self.number("3")))
# 1 rangeEqual(jdmn.runtime.Range.Range(true, self.number("2"), true, self.number("10")), jdmn.runtime.Range.Range(true, self.number("1"), true, self.number("10")))
# 1 rangeEqual(jdmn.runtime.Range.Range(false, self.number("1"), true, self.number("10")), jdmn.runtime.Range.Range(false, self.number("1"), true, self.number("10")))
# 1 rangeEqual(jdmn.runtime.Range.Range(true, self.number("1"), false, self.number("10")), jdmn.runtime.Range.Range(true, self.number("1"), false, self.number("10")))
# 1 rangeEqual(jdmn.runtime.Range.Range(false, None, false, self.number("10")), jdmn.runtime.Range.Range(false, null_input, false, self.number("10")))
# 1 rangeEqual(jdmn.runtime.Range.Range(false, None, true, self.number("10")), jdmn.runtime.Range.Range(false, null_input, true, self.number("10")))
# 1 rangeEqual(jdmn.runtime.Range.Range(false, self.number("10"), false, None), jdmn.runtime.Range.Range(false, self.number("10"), false, null_input))
# 1 rangeEqual(jdmn.runtime.Range.Range(true, self.number("10"), false, None), jdmn.runtime.Range.Range(true, self.number("10"), false, null_input))
