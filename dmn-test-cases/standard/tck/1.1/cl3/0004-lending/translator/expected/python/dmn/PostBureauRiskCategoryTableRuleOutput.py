import typing
import decimal
import datetime
import time
import isodate
import functools

import jdmn.runtime.RuleOutput
import jdmn.runtime.Pair
import jdmn.runtime.PairComparator


# Generated(value = ["decisionTableRuleOutput.ftl", "Post-bureauRiskCategoryTable"])
class PostBureauRiskCategoryTableRuleOutput(jdmn.runtime.RuleOutput.RuleOutput):
    def __init__(self, matched: bool):
        super().__init__(matched)
        self.postBureauRiskCategoryTable: typing.Optional[str] = None

    def __eq__(self, other: typing.Any) -> bool:
        if self is other:
            return True
        if type(self) is not type(other):
            return False

        if self.postBureauRiskCategoryTable != other.postBureauRiskCategoryTable:
            return False

        return True

    def __hash__(self):
        result = 0
        result = 31 * result + (0 if self.postBureauRiskCategoryTable is None else hash(self.postBureauRiskCategoryTable))

        return result

    def __str_(self) -> str:
        result_ = "(matched=" + str(self.matched)
        result_ += ", postBureauRiskCategoryTable='{0}'".format(self.postBureauRiskCategoryTable)
        result_ += ")"
        return result_
