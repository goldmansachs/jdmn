import typing
import decimal
import datetime
import time
import isodate
import functools

import jdmn.runtime.RuleOutput
import jdmn.runtime.Pair
import jdmn.runtime.PairComparator


# Generated(value = ["decisionTableRuleOutput.ftl", "ApplicationRiskScoreModel"])
class ApplicationRiskScoreModelRuleOutput(jdmn.runtime.RuleOutput.RuleOutput):
    def __init__(self, matched: bool):
        super().__init__(matched)
        self.applicationRiskScoreModel: typing.Optional[decimal.Decimal] = None

    def __eq__(self, other: typing.Any) -> bool:
        if self is other:
            return True
        if type(self) is not type(other):
            return False

        if self.applicationRiskScoreModel != other.applicationRiskScoreModel:
            return False

        return True

    def __hash__(self):
        result = 0
        result = 31 * result + (0 if self.applicationRiskScoreModel is None else hash(self.applicationRiskScoreModel))

        return result

    def __str_(self) -> str:
        result_ = "(matched=" + str(self.matched)
        result_ += ", applicationRiskScoreModel='{0}'".format(self.applicationRiskScoreModel)
        result_ += ")"
        return result_
