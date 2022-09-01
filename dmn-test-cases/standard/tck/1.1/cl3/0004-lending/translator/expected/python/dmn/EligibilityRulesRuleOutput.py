import typing
import decimal
import datetime
import time
import isodate
import functools

import jdmn.runtime.RuleOutput
import jdmn.runtime.Pair
import jdmn.runtime.PairComparator


# Generated(value = ["decisionTableRuleOutput.ftl", "EligibilityRules"])
class EligibilityRulesRuleOutput(jdmn.runtime.RuleOutput.RuleOutput):
    def __init__(self, matched: bool):
        super().__init__(matched)
        self.eligibilityRules: typing.Optional[str] = None
        self.eligibilityRulesPriority: int = 0

    def __eq__(self, other: typing.Any) -> bool:
        if self is other:
            return True
        if type(self) is not type(other):
            return False

        if self.eligibilityRules != other.eligibilityRules:
            return False
        if self.eligibilityRulesPriority != other.eligibilityRulesPriority:
            return False

        return True

    def __hash__(self):
        result = 0
        result = 31 * result + (0 if self.eligibilityRules is None else hash(self.eligibilityRules))
        result = 31 * result + (0 if self.eligibilityRulesPriority is None else hash(self.eligibilityRulesPriority))

        return result

    def __str_(self) -> str:
        result_ = "(matched=" + str(self.matched)
        result_ += ", eligibilityRules='{0}'".format(self.eligibilityRules)
        result_ += ")"
        return result_

    def sort(self, matchedResults_: typing.List[jdmn.runtime.RuleOutput.RuleOutput]) -> typing.List[jdmn.runtime.RuleOutput.RuleOutput]:
        eligibilityRulesPairs = []
        for it in matchedResults_:
            eligibilityRulesPairs.append(jdmn.runtime.Pair.Pair(it.eligibilityRules, it.eligibilityRulesPriority))
        eligibilityRulesPairs.sort(key=functools.cmp_to_key(jdmn.runtime.PairComparator.PairComparator().compare))

        result_: typing.List[jdmn.runtime.RuleOutput.RuleOutput] = []
        for i, _ in enumerate(matchedResults_):
            output_ = EligibilityRulesRuleOutput(True)
            output_.eligibilityRules = eligibilityRulesPairs[i].getLeft()
            output_.eligibilityRulesPriority = eligibilityRulesPairs[i].getRight()
            result_.append(output_)
        return result_
