import typing
import decimal
import datetime
import time
import isodate
import functools

import jdmn.runtime.RuleOutput
import jdmn.runtime.Pair
import jdmn.runtime.PairComparator


# Generated(value = ["decisionTableRuleOutput.ftl", "Strategy"])
class StrategyRuleOutput(jdmn.runtime.RuleOutput.RuleOutput):
    def __init__(self, matched: bool):
        super().__init__(matched)
        self.strategy: typing.Optional[str] = None

    def __eq__(self, other: typing.Any) -> bool:
        if self is other:
            return True
        if type(self) is not type(other):
            return False

        if self.strategy != other.strategy:
            return False

        return True

    def __hash__(self):
        result = 0
        result = 31 * result + (0 if self.strategy is None else hash(self.strategy))

        return result

    def __str_(self) -> str:
        result_ = "(matched=" + str(self.matched)
        result_ += ", strategy='{0}'".format(self.strategy)
        result_ += ")"
        return result_
