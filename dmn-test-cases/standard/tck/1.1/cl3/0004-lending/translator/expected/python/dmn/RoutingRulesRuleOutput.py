import typing
import decimal
import datetime
import time
import isodate
import functools

import jdmn.runtime.RuleOutput
import jdmn.runtime.Pair
import jdmn.runtime.PairComparator


# Generated(value = ["decisionTableRuleOutput.ftl", "RoutingRules"])
class RoutingRulesRuleOutput(jdmn.runtime.RuleOutput.RuleOutput):
    def __init__(self, matched: bool):
        super().__init__(matched)
        self.routingRules: typing.Optional[str] = None
        self.routingRulesPriority: int = 0

    def __eq__(self, other: typing.Any) -> bool:
        if self is other:
            return True
        if type(self) is not type(other):
            return False

        if self.routingRules != other.routingRules:
            return False
        if self.routingRulesPriority != other.routingRulesPriority:
            return False

        return True

    def __hash__(self):
        result = 0
        result = 31 * result + (0 if self.routingRules is None else hash(self.routingRules))
        result = 31 * result + (0 if self.routingRulesPriority is None else hash(self.routingRulesPriority))

        return result

    def __str_(self) -> str:
        result_ = "(matched=" + str(self.matched)
        result_ += ", routingRules='{0}'".format(self.routingRules)
        result_ += ")"
        return result_

    def sort(self, matchedResults_: typing.List[jdmn.runtime.RuleOutput.RuleOutput]) -> typing.List[jdmn.runtime.RuleOutput.RuleOutput]:
        routingRulesPairs = []
        for it in matchedResults_:
            routingRulesPairs.append(jdmn.runtime.Pair.Pair(it.routingRules, it.routingRulesPriority))
        routingRulesPairs.sort(key=functools.cmp_to_key(jdmn.runtime.PairComparator.PairComparator().compare))

        result_: typing.List[jdmn.runtime.RuleOutput.RuleOutput] = []
        for i, _ in enumerate(matchedResults_):
            output_ = RoutingRulesRuleOutput(True)
            output_.routingRules = routingRulesPairs[i].getLeft()
            output_.routingRulesPriority = routingRulesPairs[i].getRight()
            result_.append(output_)
        return result_
