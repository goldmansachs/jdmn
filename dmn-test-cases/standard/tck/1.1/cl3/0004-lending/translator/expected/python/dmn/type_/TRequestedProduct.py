import typing
import decimal
import datetime
import time
import isodate

import jdmn.runtime.DMNType
import jdmn.runtime.Context
import jdmn.runtime.DMNRuntimeException


# Generated(value = {"itemDefinitionInterface.ftl", "tRequestedProduct"})
class TRequestedProduct(jdmn.runtime.DMNType.DMNType):
    def __init__(self):
        jdmn.runtime.DMNType.DMNType.__init__(self)

        self.productType = None
        self.amount = None
        self.rate = None
        self.term = None

    def toTRequestedProduct(self, other: typing.Any) -> typing.Optional['TRequestedProduct']:
        if other is None:
            return None
        elif issubclass(type(other), TRequestedProduct):
            return other
        elif isinstance(other, jdmn.runtime.Context.Context):
            result_ = TRequestedProduct()
            result_.productType = other.get("ProductType")
            result_.amount = other.get("Amount")
            result_.rate = other.get("Rate")
            result_.term = other.get("Term")
            return result_
        elif isinstance(other, jdmn.runtime.DMNType.DMNType):
            return self.toTRequestedProduct(other.toContext())
        else:
            raise jdmn.runtime.DMNRuntimeException.DMNRuntimeException("Cannot convert '{0}' to '{1}'".format(type(other), type(TRequestedProduct)))

    def toContext(self) -> jdmn.runtime.Context.Context:
        context = jdmn.runtime.Context.Context()
        context.add("productType", self.productType)
        context.add("amount", self.amount)
        context.add("rate", self.rate)
        context.add("term", self.term)
        return context

    def equalTo(self, other: typing.Any) -> bool:
        if self is other:
            return True
        if type(self) is not type(other):
            return False

        if self.amount != other.amount:
            return False
        if self.productType != other.productType:
            return False
        if self.rate != other.rate:
            return False
        if self.term != other.term:
            return False

        return True

    def hashCode(self):
        result = 0
        result = 31 * result + (0 if self.amount is None else hash(self.amount))
        result = 31 * result + (0 if self.productType is None else hash(self.productType))
        result = 31 * result + (0 if self.rate is None else hash(self.rate))
        result = 31 * result + (0 if self.term is None else hash(self.term))
        return result

    def asString(self) -> str:
        result_ = "{"
        result_ += ("Amount=" + str(self.amount))
        result_ += (", ProductType=" + str(self.productType))
        result_ += (", Rate=" + str(self.rate))
        result_ += (", Term=" + str(self.term))
        result_ += "}"
        return result_
