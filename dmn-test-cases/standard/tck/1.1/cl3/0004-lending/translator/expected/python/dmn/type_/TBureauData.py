import typing
import decimal
import datetime
import time
import isodate

import jdmn.runtime.DMNType
import jdmn.runtime.Context
import jdmn.runtime.DMNRuntimeException


# Generated(value = {"itemDefinitionInterface.ftl", "tBureauData"})
class TBureauData(jdmn.runtime.DMNType.DMNType):
    def __init__(self):
        jdmn.runtime.DMNType.DMNType.__init__(self)

        self.creditScore = None
        self.bankrupt = None

    def toTBureauData(self, other: typing.Any) -> typing.Optional['TBureauData']:
        if other is None:
            return None
        elif issubclass(type(other), TBureauData):
            return other
        elif isinstance(other, jdmn.runtime.Context.Context):
            result_ = TBureauData()
            result_.creditScore = other.get("CreditScore")
            result_.bankrupt = other.get("Bankrupt")
            return result_
        elif isinstance(other, jdmn.runtime.DMNType.DMNType):
            return self.toTBureauData(other.toContext())
        else:
            raise jdmn.runtime.DMNRuntimeException.DMNRuntimeException("Cannot convert '{0}' to '{1}'".format(type(other), type(TBureauData)))

    def toContext(self) -> jdmn.runtime.Context.Context:
        context = jdmn.runtime.Context.Context()
        context.add("creditScore", self.creditScore)
        context.add("bankrupt", self.bankrupt)
        return context

    def equalTo(self, other: typing.Any) -> bool:
        if self is other:
            return True
        if type(self) is not type(other):
            return False

        if self.bankrupt != other.bankrupt:
            return False
        if self.creditScore != other.creditScore:
            return False

        return True

    def hashCode(self):
        result = 0
        result = 31 * result + (0 if self.bankrupt is None else hash(self.bankrupt))
        result = 31 * result + (0 if self.creditScore is None else hash(self.creditScore))
        return result

    def asString(self) -> str:
        result_ = "{"
        result_ += ("Bankrupt=" + str(self.bankrupt))
        result_ += (", CreditScore=" + str(self.creditScore))
        result_ += "}"
        return result_
