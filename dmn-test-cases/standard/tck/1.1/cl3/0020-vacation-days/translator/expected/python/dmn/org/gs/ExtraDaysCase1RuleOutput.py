import typing
import decimal
import datetime
import time
import isodate
import functools

import jdmn.runtime.RuleOutput
import jdmn.runtime.Pair
import jdmn.runtime.PairComparator


# Generated(value = ["decisionTableRuleOutput.ftl", "Extra days case 1"])
class ExtraDaysCase1RuleOutput(jdmn.runtime.RuleOutput.RuleOutput):
    def __init__(self, matched: bool):
        super().__init__(matched)
        self.extraDaysCase1: typing.Optional[decimal.Decimal] = None

    def __eq__(self, other: typing.Any) -> bool:
        if self is other:
            return True
        if type(self) is not type(other):
            return False

        if self.extraDaysCase1 != other.extraDaysCase1:
            return False

        return True

    def __hash__(self):
        result = 0
        result = 31 * result + (0 if self.extraDaysCase1 is None else hash(self.extraDaysCase1))

        return result

    def __str_(self) -> str:
        result_ = "(matched=" + str(self.matched)
        result_ += ", extraDaysCase1='{0}'".format(self.extraDaysCase1)
        result_ += ")"
        return result_
