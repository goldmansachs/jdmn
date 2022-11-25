import typing
import decimal
import datetime
import time
import isodate
import functools

import jdmn.runtime.RuleOutput
import jdmn.runtime.Pair
import jdmn.runtime.PairComparator


# Generated(value = ["decisionTableRuleOutput.ftl", "Extra days case 3"])
class ExtraDaysCase3RuleOutput(jdmn.runtime.RuleOutput.RuleOutput):
    def __init__(self, matched: bool):
        super().__init__(matched)
        self.extraDaysCase3: typing.Optional[decimal.Decimal] = None

    def __eq__(self, other: typing.Any) -> bool:
        if self is other:
            return True
        if type(self) is not type(other):
            return False

        if self.extraDaysCase3 != other.extraDaysCase3:
            return False

        return True

    def __hash__(self):
        result = 0
        result = 31 * result + (0 if self.extraDaysCase3 is None else hash(self.extraDaysCase3))

        return result

    def __str_(self) -> str:
        result_ = "(matched=" + str(self.matched)
        result_ += ", extraDaysCase3='{0}'".format(self.extraDaysCase3)
        result_ += ")"
        return result_
