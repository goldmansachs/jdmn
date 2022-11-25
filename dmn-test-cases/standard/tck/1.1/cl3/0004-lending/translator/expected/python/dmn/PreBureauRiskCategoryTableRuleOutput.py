import typing
import decimal
import datetime
import time
import isodate
import functools

import jdmn.runtime.RuleOutput
import jdmn.runtime.Pair
import jdmn.runtime.PairComparator


# Generated(value = ["decisionTableRuleOutput.ftl", "Pre-bureauRiskCategoryTable"])
class PreBureauRiskCategoryTableRuleOutput(jdmn.runtime.RuleOutput.RuleOutput):
    def __init__(self, matched: bool):
        super().__init__(matched)
        self.preBureauRiskCategoryTable: typing.Optional[str] = None

    def __eq__(self, other: typing.Any) -> bool:
        if self is other:
            return True
        if type(self) is not type(other):
            return False

        if self.preBureauRiskCategoryTable != other.preBureauRiskCategoryTable:
            return False

        return True

    def __hash__(self):
        result = 0
        result = 31 * result + (0 if self.preBureauRiskCategoryTable is None else hash(self.preBureauRiskCategoryTable))

        return result

    def __str_(self) -> str:
        result_ = "(matched=" + str(self.matched)
        result_ += ", preBureauRiskCategoryTable='{0}'".format(self.preBureauRiskCategoryTable)
        result_ += ")"
        return result_
