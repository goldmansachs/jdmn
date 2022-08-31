import typing
import decimal
import datetime
import time
import isodate

import type_.Monthly


# Generated(value = {"itemDefinition.ftl", "Monthly"})
class MonthlyImpl(type_.Monthly.Monthly):
    def __init__(self, expenses: typing.Optional[decimal.Decimal] = None, income: typing.Optional[decimal.Decimal] = None, repayments: typing.Optional[decimal.Decimal] = None):
        type_.Monthly.Monthly.__init__(self)

        self.expenses = expenses
        self.income = income
        self.repayments = repayments

    def __eq__(self, other: typing.Any) -> bool:
        return self.equalTo(other)

    def __hash__(self):
        return self.hashCode()

    def __str__(self) -> str:
        return self.asString()
