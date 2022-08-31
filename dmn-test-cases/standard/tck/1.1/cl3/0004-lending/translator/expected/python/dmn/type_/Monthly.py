import typing
import decimal
import datetime
import time
import isodate

import jdmn.runtime.DMNType
import jdmn.runtime.Context
import jdmn.runtime.DMNRuntimeException


# Generated(value = {"itemDefinitionInterface.ftl", "Monthly"})
class Monthly(jdmn.runtime.DMNType.DMNType):
    def __init__(self):
        jdmn.runtime.DMNType.DMNType.__init__(self)

        self.income = None
        self.expenses = None
        self.repayments = None

    def toMonthly(self, other: typing.Any) -> typing.Optional['Monthly']:
        if other is None:
            return None
        elif issubclass(type(other), Monthly):
            return other
        elif isinstance(other, jdmn.runtime.Context.Context):
            result_ = Monthly()
            result_.income = other.get("Income")
            result_.expenses = other.get("Expenses")
            result_.repayments = other.get("Repayments")
            return result_
        elif isinstance(other, jdmn.runtime.DMNType.DMNType):
            return self.toMonthly(other.toContext())
        else:
            raise jdmn.runtime.DMNRuntimeException.DMNRuntimeException("Cannot convert '{0}' to '{1}'".format(type(other), type(Monthly)))

    def toContext(self) -> jdmn.runtime.Context.Context:
        context = jdmn.runtime.Context.Context()
        context.put("income", self.income)
        context.put("expenses", self.expenses)
        context.put("repayments", self.repayments)
        return context

    def equalTo(self, other: typing.Any) -> bool:
        if self is other:
            return True
        if type(self) is not type(other):
            return False

        if self.expenses != other.expenses:
            return False
        if self.income != other.income:
            return False
        if self.repayments != other.repayments:
            return False

        return True

    def hashCode(self):
        result = 0
        result = 31 * result + (0 if self.expenses is None else hash(self.expenses))
        result = 31 * result + (0 if self.income is None else hash(self.income))
        result = 31 * result + (0 if self.repayments is None else hash(self.repayments))
        return result

    def asString(self) -> str:
        result_ = "{"
        result_ += ("Expenses=" + self.expenses)
        result_ += (", Income=" + self.income)
        result_ += (", Repayments=" + self.repayments)
        result_ += "}"
        return result_
