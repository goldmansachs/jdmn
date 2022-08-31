import typing
import decimal
import datetime
import time
import isodate

import type_.TBureauData


# Generated(value = {"itemDefinition.ftl", "tBureauData"})
class TBureauDataImpl(type_.TBureauData.TBureauData):
    def __init__(self, bankrupt: typing.Optional[bool] = None, creditScore: typing.Optional[decimal.Decimal] = None):
        type_.TBureauData.TBureauData.__init__(self)

        self.bankrupt = bankrupt
        self.creditScore = creditScore

    def __eq__(self, other: typing.Any) -> bool:
        return self.equalTo(other)

    def __hash__(self):
        return self.hashCode()

    def __str__(self) -> str:
        return self.asString()
