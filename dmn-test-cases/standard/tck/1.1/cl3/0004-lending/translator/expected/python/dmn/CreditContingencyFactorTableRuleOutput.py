import typing
import decimal
import datetime
import time
import isodate
import functools

import jdmn.runtime.RuleOutput
import jdmn.runtime.Pair
import jdmn.runtime.PairComparator


# Generated(value = ["decisionTableRuleOutput.ftl", "CreditContingencyFactorTable"])
class CreditContingencyFactorTableRuleOutput(jdmn.runtime.RuleOutput.RuleOutput):
    def __init__(self, matched: bool):
        super().__init__(matched)
        self.creditContingencyFactorTable: typing.Optional[decimal.Decimal] = None

    def __eq__(self, other: typing.Any) -> bool:
        if self is other:
            return True
        if type(self) is not type(other):
            return False

        if self.creditContingencyFactorTable != other.creditContingencyFactorTable:
            return False

        return True

    def __hash__(self):
        result = 0
        result = 31 * result + (0 if self.creditContingencyFactorTable is None else hash(self.creditContingencyFactorTable))

        return result

    def __str_(self) -> str:
        result_ = "(matched=" + str(self.matched)
        result_ += ", creditContingencyFactorTable='{0}'".format(self.creditContingencyFactorTable)
        result_ += ")"
        return result_
